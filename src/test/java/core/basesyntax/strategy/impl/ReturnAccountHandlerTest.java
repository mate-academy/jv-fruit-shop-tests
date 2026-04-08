package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Accountable;
import core.basesyntax.model.Fruit;
import org.junit.jupiter.api.Test;

class ReturnAccountHandlerTest {
    @Test
    void account_return_increasesAmount() {
        Accountable fruit = new Fruit("apple");
        fruit.setAmount(10);

        new ReturnAccountHandler().account(fruit, 5);

        assertEquals(15, fruit.getAmount());
    }
}
