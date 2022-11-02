package com.fruitshop.strategy;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HandlerConditionFactoryTest {
    private final HandlerConditionFactory handler = new HandlerConditionFactory();

    @Test
    void getHandler_Ok() {
        String existing = "b";
        OperationHandler expected = new OperationHandlerBalance();
        Assertions.assertEquals(expected.getClass(), handler.getHandler(existing).getClass());
    }

    @Test
    void getHandler_NotExist() {
        String wrong = "A";
        Assertions.assertThrows(NoSuchElementException.class, () -> handler.getHandler(wrong));
    }
}
