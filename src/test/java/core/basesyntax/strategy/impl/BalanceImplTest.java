package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceImplTest {
    private BalanceImpl balance;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
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

    @AfterEach
    public void afterEach() {
        Storage.fruitStorage.clear();
    }
}
