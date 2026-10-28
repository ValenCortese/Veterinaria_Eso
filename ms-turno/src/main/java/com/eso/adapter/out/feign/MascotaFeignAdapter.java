package com.eso.adapter.out.feign;

import com.eso.domain.exception.ServicioNoDisponibleException;
import com.eso.domain.port.out.MascotaServicePort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MascotaFeignAdapter implements MascotaServicePort {

    private final MascotaFeignClient client;

    @CircuitBreaker(name = "ms-mascota", fallbackMethod = "fallbackMascota")
    @Retry(name = "ms-mascota", fallbackMethod = "fallbackMascota")
    @Override
    public boolean existsById(Long id) {
        try {
            client.findById(id);
            return true;
        } catch (feign.FeignException.NotFound e) {
            return false;
        }
    }

    private MascotaResponse fallbackMascota(Long mascotaId, ServicioNoDisponibleException ex) {
        throw new ServicioNoDisponibleException("El servicio de mascotas no esta disponible: " + ex.getMessage());
    }
}
