package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Accountable;
import core.basesyntax.model.Fruit;
import org.junit.jupiter.api.Test;

class BalanceAccountHandlerTest {
    @Test
    void account_balance_setsAmount() {
        Accountable fruit = new Fruit("apple");
        fruit.setAmount(10);

        new BalanceAccountHandler().account(fruit, 50);

        assertEquals(50, fruit.getAmount());
    }
}
