package com.sirmaacademy.professionalprogram.solidprinciplesexercises.singleresponsibilityprinciple.documentoperations;

import java.math.BigDecimal;

public class Product {

    private String name;
    private BigDecimal value;

    public Product(String name, BigDecimal value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }
}
