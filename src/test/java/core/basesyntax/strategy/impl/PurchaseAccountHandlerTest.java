package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Accountable;
import core.basesyntax.model.Fruit;
import org.junit.jupiter.api.Test;

class PurchaseAccountHandlerTest {
    @Test
    void account_purchase_decreasesAmount() {
        Accountable fruit = new Fruit("apple");
        fruit.setAmount(10);

        new PurchaseAccountHandler().account(fruit, 4);

        assertEquals(6, fruit.getAmount());
    }

    @Test
    void account_purchase_negative_throwsException() {
        Accountable fruit = new Fruit("apple");
        fruit.setAmount(3);

        assertThrows(RuntimeException.class,
                () -> new PurchaseAccountHandler().account(fruit, 5));
    }
}
