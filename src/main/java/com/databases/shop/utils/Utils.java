package com.databases.shop.utils;

import com.databases.shop.exceptions.InvalidNameException;
import com.databases.shop.exceptions.product.ProductIllegalArgumentException;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    public String processName(String name) {
        return name.replaceAll("\\s+", " ").trim();
    }

    public boolean isInvalidName(String name) {
        return name.isEmpty();
    }

    public void checkName(String name) {
        if (isInvalidName(name)) {
            throw new InvalidNameException(Values.INVALID_NAME);
        }
    }

    public void checkQuantOfProduct(int quantOfProduct) {
        if (quantOfProduct < 0) {
            throw new ProductIllegalArgumentException("Quantity of product should not be negative!");
        }
    }

    public void checkPriceOfProduct(double priceOfProduct) {
        if (priceOfProduct < 0) {
            throw new ProductIllegalArgumentException("Price of product should not be negative!");
        }
    }
}
