package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class BalanceTransactionHandlerImplTest {
    @Test
    void balance_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        Fruit fruit = new Fruit("apple", 5);
        transaction.setFruit(fruit);

        BalanceTransactionHandlerImpl handler = new BalanceTransactionHandlerImpl();
        handler.transaction(transaction);
        int expected = 5;
        int actual = Storage.fruits.get("apple");
        assertEquals(expected, actual);
    }
}
