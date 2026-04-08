package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Accountable;
import core.basesyntax.model.Fruit;
import org.junit.jupiter.api.Test;

class SupplyAccountHandlerTest {
    @Test
    void account_supply_increasesAmount() {
        Accountable fruit = new Fruit("apple");
        fruit.setAmount(10);

        new SupplyAccountHandler().account(fruit, 5);

        assertEquals(15, fruit.getAmount());
    }
}
