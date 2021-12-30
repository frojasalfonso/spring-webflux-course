package com.feliper.orderservice.util;

import com.feliper.orderservice.dto.OrderStatus;
import com.feliper.orderservice.dto.ProductDto;
import com.feliper.orderservice.dto.PurchaseOrderRequestDto;
import com.feliper.orderservice.dto.PurchaseOrderResponseDto;
import com.feliper.orderservice.dto.RequestContext;
import com.feliper.orderservice.dto.TransactionRequestDto;
import com.feliper.orderservice.dto.TransactionStatus;
import com.feliper.orderservice.entity.PurchaseOrder;

public class EntityDtoUtil {

    public static void setTransactionRequestDto(RequestContext rc) {
        rc.setTransactionRequestDto(TransactionRequestDto.builder()
                .userId(rc.getPurchaseOrderRequestDto().getUserId())
                .amount(rc.getProductDto().getPrice())
                .build());
    }

    public static PurchaseOrder getPurchaseOrder(RequestContext rc) {
        return PurchaseOrder.builder()
                .userId(rc.getPurchaseOrderRequestDto().getUserId())
                .productId(rc.getPurchaseOrderRequestDto().getProductId())
                .amount(rc.getProductDto().getPrice())
                .status(rc.getTransactionResponseDto().getStatus() == TransactionStatus.APPROVED
                        ? OrderStatus.COMPLETED
                        : OrderStatus.FAILED
                )
                .build();
    }

    public static PurchaseOrderResponseDto toDto(PurchaseOrder entity) {
        return PurchaseOrderResponseDto.builder()
                .orderId(entity.getId())
                .userId(entity.getUserId())
                .productId(entity.getProductId())
                .amount(entity.getAmount())
                .status(entity.getStatus())
                .build();
    }

}
