package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnImplTest {
    private ReturnImpl returnImpl;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        returnImpl = new ReturnImpl();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 15);
    }

    @Test
    public void calculate_validTransaction_ok() {
        Storage storage = new Storage();
        storage.put(fruitTransaction.getFruit(), 50);
        returnImpl.calculateFruitOperation(fruitTransaction);
        int actualQuantity = Storage.fruitStorage.get(fruitTransaction.getFruit());
        int expectedQuantity = 65;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void calculate_negativeValue_notOk() {
        fruitTransaction.setQuantity(-10);
        assertThrows(RuntimeException.class, () ->
                returnImpl.calculateFruitOperation(fruitTransaction));
    }

    @AfterEach
    public void afterEach() {
        Storage.fruitStorage.clear();
    }
}
