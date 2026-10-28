(function () {
  "use strict";

  const configs = {
    duenios: {
      label: "dueño",
      endpoint: "/api/duenios",
      fields: [
        ["nombre", "Nombre", "text", true],
        ["apellido", "Apellido", "text", true],
        ["cedula", "Cédula", "text", true],
        ["telefono", "Teléfono", "number", true],
        ["email", "Email", "email", true],
      ],
      columns: (x) => [
        x.id,
        `${x.nombre} ${x.apellido}`,
        x.cedula,
        x.telefono,
        x.email,
      ],
    },
    mascotas: {
      label: "mascota",
      endpoint: "/api/mascotas",
      fields: [
        ["nombre", "Nombre", "text", true],
        ["especie", "Especie", "text", true],
        ["raza", "Raza", "text", false],
        ["fechaNacimiento", "Fecha de nacimiento", "date", false],
        ["duenioId", "Dueño", "number", true],
      ],
      columns: (x) => [
        x.id,
        x.nombre,
        x.especie,
        x.raza || "-",
        x.fechaNacimiento || "-",
        x.duenioId,
      ],
    },
    veterinarios: {
      label: "veterinario",
      endpoint: "/api/veterinarios",
      fields: [
        ["nombre", "Nombre", "text", true],
        ["apellido", "Apellido", "text", true],
        ["matricula", "Matrícula", "text", true],
        ["especialidad", "Especialidad", "text", true],
        ["email", "Email", "email", true],
      ],
      columns: (x) => [
        x.id,
        `${x.nombre} ${x.apellido}`,
        x.matricula,
        x.especialidad,
        x.email,
      ],
    },
    turnos: {
      label: "turno",
      endpoint: "/api/turnos",
      fields: [
        ["fechaHora", "Fecha y hora", "datetime-local", true],
        ["mascotaId", "ID de mascota", "number", true],
        ["veterinarioId", "ID de veterinario", "number", true],
        ["motivo", "Motivo", "text", true],
      ],
      columns: (x) => [
        x.id,
        formatDate(x.fechaHora),
        x.mascotaId,
        x.veterinarioId,
        x.motivo,
        statusBadge(x.estado),
      ],
    },
  };

  const escapeHtml = (value) =>
    String(value == null ? "" : value).replace(
      /[&<>"']/g,
      (char) =>
        ({
          "&": "&amp;",
          "<": "&lt;",
          ">": "&gt;",
          '"': "&quot;",
          "'": "&#039;",
        })[char],
    );
  const formatDate = (value) =>
    value ? new Date(value).toLocaleString("es-AR") : "-";
  const statusBadge = (value) =>
    `<span class="badge text-bg-${value === "CANCELADO" ? "secondary" : value === "CONFIRMADO" ? "success" : "warning"}">${escapeHtml(value || "-")}</span>`;

  async function request(url, options) {
    const response = await fetch(url, {
      headers: { "Content-Type": "application/json" },
      ...options,
    });
    if (!response.ok) {
      let message = `Error ${response.status}`;
      try {
        const body = await response.json();
        message = body.message || body.error || message;
      } catch (_) {
        /* respuesta sin JSON */
      }
      throw new Error(message);
    }
    return response.status === 204 ? null : response.json();
  }

  function showAlert(message, type) {
    const target = document.querySelector("#alert");
    if (target)
      target.innerHTML = `<div class="alert alert-${type} alert-dismissible fade show" role="alert">${escapeHtml(message)}<button type="button" class="btn-close" data-bs-dismiss="alert"></button></div>`;
  }

  function modal(config, item) {
    document.querySelector("#crudModal")?.remove();
    const isEdit = Boolean(item);
    const controls = config.fields
      .map(
        ([name, label, type, required]) =>
          `<div class="mb-3"><label class="form-label" for="field-${name}">${label}</label><input class="form-control" id="field-${name}" name="${name}" type="${type}" value="${escapeHtml(item?.[name] || "")}" ${required ? "required" : ""}></div>`,
      )
      .join("");
    const element = document.createElement("div");
    element.innerHTML = `<div class="modal fade" id="crudModal" tabindex="-1"><div class="modal-dialog"><div class="modal-content"><form id="crudForm"><div class="modal-header"><h5 class="modal-title">${isEdit ? "Editar" : "Nuevo"} ${config.label}</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div><div class="modal-body">${controls}</div><div class="modal-footer"><button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Cancelar</button><button class="btn btn-primary" type="submit">Guardar</button></div></form></div></div></div>`;
    document.body.appendChild(element.firstElementChild);
    const dialog = new bootstrap.Modal(document.querySelector("#crudModal"));
    dialog.show();
    document
      .querySelector("#crudForm")
      .addEventListener("submit", async (event) => {
        event.preventDefault();
        const payload = Object.fromEntries(new FormData(event.target));
        if (payload.telefono) payload.telefono = Number(payload.telefono);
        ["duenioId", "mascotaId", "veterinarioId"].forEach((key) => {
          if (payload[key]) payload[key] = Number(payload[key]);
        });
        try {
          await request(`${config.endpoint}${isEdit ? `/${item.id}` : ""}`, {
            method: isEdit ? "PUT" : "POST",
            body: JSON.stringify(payload),
          });
          dialog.hide();
          showAlert("Registro guardado correctamente.", "success");
          load(config);
        } catch (error) {
          showAlert(error.message, "danger");
        }
      });
  }

  async function load(config) {
    try {
      const data = await request(config.endpoint);
      const rows = document.querySelector("#rows");
      rows.innerHTML = data.length
        ? data
            .map((item) => {
              const values = config
                .columns(item)
                .map(
                  (value) =>
                    `<td>${typeof value === "string" && value.startsWith("<span") ? value : escapeHtml(value)}</td>`,
                )
                .join("");
              const actions =
                config.endpoint === "/api/turnos"
                  ? item.estado !== "CANCELADO"
                    ? `<button class="btn btn-sm btn-outline-danger" data-cancel="${item.id}">Cancelar</button>`
                    : ""
                  : `<button class="btn btn-sm btn-outline-primary me-1" data-edit="${item.id}">Editar</button><button class="btn btn-sm btn-outline-danger" data-delete="${item.id}">Eliminar</button>`;
              return `<tr>${values}<td class="text-end text-nowrap">${actions}</td></tr>`;
            })
            .join("")
        : `<tr><td colspan="10" class="text-center text-secondary">No hay registros.</td></tr>`;
      rows.querySelectorAll("[data-edit]").forEach((button) =>
        button.addEventListener("click", () =>
          modal(
            config,
            data.find((x) => String(x.id) === button.dataset.edit),
          ),
        ),
      );
      rows
        .querySelectorAll("[data-delete]")
        .forEach((button) =>
          button.addEventListener("click", () =>
            remove(config, button.dataset.delete),
          ),
        );
      rows
        .querySelectorAll("[data-cancel]")
        .forEach((button) =>
          button.addEventListener("click", () =>
            cancel(config, button.dataset.cancel),
          ),
        );
    } catch (error) {
      showAlert(`No se pudieron cargar los datos: ${error.message}`, "danger");
    }
  }

  async function remove(config, id) {
    if (!window.confirm("¿Eliminar este registro?")) return;
    try {
      await request(`${config.endpoint}/${id}`, { method: "DELETE" });
      showAlert("Registro eliminado.", "success");
      load(config);
    } catch (error) {
      showAlert(error.message, "danger");
    }
  }
  async function cancel(config, id) {
    if (!window.confirm("¿Cancelar este turno?")) return;
    try {
      await request(`${config.endpoint}/${id}/cancelar`, { method: "PATCH" });
      showAlert("Turno cancelado.", "success");
      load(config);
    } catch (error) {
      showAlert(error.message, "danger");
    }
  }

  async function init() {
    const navbar = document.querySelector("#navbar");
    if (navbar) {
      const navbarResponse = await fetch("/navbar.html");
      if (!navbarResponse.ok)
        throw new Error("No se pudo cargar la navegación.");
      navbar.innerHTML = await navbarResponse.text();
      const bootstrapScript = document.createElement("script");
      bootstrapScript.src =
        "https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js";
      await new Promise((resolve, reject) => {
        bootstrapScript.onload = resolve;
        bootstrapScript.onerror = () =>
          reject(new Error("No se pudo cargar Bootstrap."));
        document.body.appendChild(bootstrapScript);
      });
    }
    const page = document.querySelector("[data-page]");
    if (!page) return;
    const config = configs[page.dataset.page];
    document
      .querySelector("[data-action='new']")
      .addEventListener("click", () => modal(config));
    load(config);
  }
  init().catch((error) => showAlert(error.message, "danger"));
})();
