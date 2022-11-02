package com.fruitshop.strategy;

public class ElementDoesNotExist extends RuntimeException {
    public ElementDoesNotExist(String message) {
        super(message);
    }
}
