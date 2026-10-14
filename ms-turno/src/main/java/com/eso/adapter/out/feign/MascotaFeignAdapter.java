package com.eso.adapter.out.feign;

import com.eso.domain.port.out.MascotaServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MascotaFeignAdapter implements MascotaServicePort {
    private final MascotaFeignClient client;
    @Override
    public boolean existsById(Long id) {
        try {
            client.findById(id);
            return true;
        } catch (feign.FeignException.NotFound e) {
            return false;
        }
    }
}
