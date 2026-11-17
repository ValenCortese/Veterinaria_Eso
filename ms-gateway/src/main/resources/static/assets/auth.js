(function () {
  "use strict";

  const TOKEN_KEY = "veterinaria_token";
  const form = document.querySelector("#auth-form");
  const alertTarget = document.querySelector("#auth-alert");
  const submit = document.querySelector("#auth-submit");
  const roleGroup = document.querySelector("#role-group");
  let mode = "login";

  function showAlert(message, type) {
    alertTarget.innerHTML =
      `<div class="alert alert-${type}" role="alert">${message}</div>`;
  }

  function setMode(nextMode) {
    mode = nextMode;
    const registering = mode === "register";
    document.querySelectorAll("[data-auth-mode]").forEach((button) => {
      button.classList.toggle("active", button.dataset.authMode === mode);
    });
    roleGroup.classList.toggle("d-none", !registering);
    submit.textContent = registering ? "Crear usuario" : "Iniciar sesión";
    document.querySelector("#password").autocomplete = registering
      ? "new-password"
      : "current-password";
    alertTarget.innerHTML = "";
  }

  document.querySelectorAll("[data-auth-mode]").forEach((button) => {
    button.addEventListener("click", () => setMode(button.dataset.authMode));
  });

  form.addEventListener("submit", async (event) => {
    event.preventDefault();
    submit.disabled = true;
    showAlert("Procesando...", "info");
    const values = Object.fromEntries(new FormData(form));
    const payload = {
      username: values.username.trim(),
      password: values.password,
    };
    if (mode === "register") payload.rol = values.rol;

    try {
      const response = await fetch(`/auth/${mode}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });
      if (!response.ok) {
        let message = `No se pudo completar la operación (${response.status}).`;
        try {
          const body = await response.json();
          message = body.message || body.error || message;
        } catch (_) {
          // La API puede responder sin cuerpo JSON.
        }
        throw new Error(message);
      }
      const body = await response.json();
      if (!body.token) throw new Error("La respuesta no contiene un token.");
      localStorage.setItem(TOKEN_KEY, body.token);
      window.location.replace("/index.html");
    } catch (error) {
      showAlert(error.message, "danger");
      submit.disabled = false;
    }
  });
})();
