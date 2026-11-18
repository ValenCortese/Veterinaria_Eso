package com.eso.adapter.out.feign;

import com.eso.domain.exception.ServicioNoDisponibleException;
import com.eso.domain.port.out.MascotaServicePort;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MascotaFeignAdapter implements MascotaServicePort {

    private final MascotaFeignClient client;

    @Retry(name = "ms-mascota", fallbackMethod = "fallbackMascota")
    @CircuitBreaker(name = "ms-mascota")
    @Override
    public boolean existsById(Long id) {
        try {
            client.findById(id);
            return true;
        } catch (FeignException.NotFound e) {
            return false;
        }
    }

    private boolean fallbackMascota(Long mascotaId, Throwable ex) {
        throw new ServicioNoDisponibleException("El servicio de mascotas no está disponible: " + ex.getMessage());
    }
}