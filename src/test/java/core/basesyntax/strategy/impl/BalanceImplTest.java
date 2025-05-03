package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceImplTest {
    private static BalanceImpl balance;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        balance = new BalanceImpl();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 5);
    }

    @Test
    public void calculate_negativeValue_notOk() {
        fruitTransaction.setQuantity(-2);
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                balance.calculateFruitOperation(fruitTransaction));
    }

    @Test
    public void calculate_validTransaction_ok() {
        balance.calculateFruitOperation(fruitTransaction);
        int expectedQuantity = 5;
        int actualQuantity = Storage.fruitStorage.get(fruitTransaction.getFruit());
        assertEquals(expectedQuantity, actualQuantity);
    }

    @AfterAll
    static void afterAll() {
        Storage.fruitStorage.clear();
    }
}
