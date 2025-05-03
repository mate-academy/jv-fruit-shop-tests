package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyImplTest {
    private static SupplyImpl supply;
    private static FruitTransaction fruitTransaction;

    @BeforeEach
    public void beforeEach() {
        supply = new SupplyImpl();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 15);
    }

    @Test
    public void supplyOperation_OperateTransaction_ok() {
        Storage storage = new Storage();
        storage.put(fruitTransaction.getFruit(), 50);
        supply.calculateFruitOperation(fruitTransaction);
        int expectedQuantity = 65;
        int actualQuantity = storage.get(fruitTransaction.getFruit());
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void supplyOperation_NegativeQuantity_notOk() {
        fruitTransaction.setQuantity(-5);
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                supply.calculateFruitOperation(fruitTransaction));
    }

    @AfterAll
    static void afterAll() {
        Storage.fruitStorage.clear();
    }
}
