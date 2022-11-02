package com.fruitshop.strategy;

import com.fruitshop.db.DataBase;
import com.fruitshop.model.Fruit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationHandlerSupplyTest {
    private final HandlerConditionFactory handlerCondition = new HandlerConditionFactory();
    private Fruit lemon;

    @BeforeEach
    void setUp() {
        lemon = new Fruit();
        lemon.setSupply(50);
        DataBase.fruitsInShop.put("lemon", lemon);
    }

    @AfterEach
    void tearDown() {
        DataBase.fruitsInShop.clear();
    }

    @Test
    void handle_ok() {
        handlerCondition.getHandler("s").handle("lemon", "50");
        int expected = 100;
        Assertions.assertEquals(expected, lemon.getSupply());
    }
}
