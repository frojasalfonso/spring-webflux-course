package com.feliper.orderservice.service;

import com.feliper.orderservice.dto.PurchaseOrderResponseDto;
import com.feliper.orderservice.repository.PurchaseOrderRepository;
import com.feliper.orderservice.util.EntityDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderQueryService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public OrderQueryService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    public Flux<PurchaseOrderResponseDto> getByUserId(Integer userId) {
        return Flux.fromStream(() -> this.purchaseOrderRepository.findByUserId(userId).stream())
                .map(EntityDtoUtil::toDto)
                .subscribeOn(Schedulers.boundedElastic());
    }
}
