package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class SupplyTransactionHandlerImplTest {
    @Test
    void supply_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        Fruit fruit = new Fruit("apple", 5);
        transaction.setFruit(fruit);
        Storage.fruits.put("apple", 10);
        SupplyTransactionHandlerImpl handler = new SupplyTransactionHandlerImpl();
        handler.transaction(transaction);
        int expected = 15;
        int actual = Storage.fruits.get("apple");
        assertEquals(expected, actual);
    }
}
