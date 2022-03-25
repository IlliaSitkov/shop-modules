package com.databases.shop.models;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
public class ProductInOrderId implements Serializable {


    private Long orderId;
    private Long productArticul;

    public ProductInOrderId(Long orderId, Long productArticul) {
        this.orderId = orderId;
        this.productArticul = productArticul;
    }

    public ProductInOrderId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductInOrderId)) return false;
        ProductInOrderId that = (ProductInOrderId) o;
        return getOrderId().equals(that.getOrderId()) &&
                getProductArticul().equals(that.getProductArticul());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId(), getProductArticul());
    }
}
