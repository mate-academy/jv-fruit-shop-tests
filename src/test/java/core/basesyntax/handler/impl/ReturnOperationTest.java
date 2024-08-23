package core.basesyntax.handler.impl;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private ReturnOperation returnOperation;
    private Map<String, Integer> fruitStorage;

    @BeforeEach
    void setUp() {
        fruitStorage = new HashMap<>();
        returnOperation = new ReturnOperation(fruitStorage);
    }

    @Test
    void apply_validTransaction_ok() {
        fruitStorage.put("banana", 100);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(30);
        returnOperation.apply(transaction);
        assertEquals(130, fruitStorage.get("banana"));
    }
}
