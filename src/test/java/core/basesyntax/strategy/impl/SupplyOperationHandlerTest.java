package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static OperationHandler handler;

    @BeforeAll
    static void beforeAll() {
        handler = new SupplyOperationHandler();
    }

    @Test
    public void doCalculation_firstAddition_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "pear", 90);
        Map<String, Integer> expected = Map.of("pear", 90);
        handler.doCalculation(transaction);
        Map<String, Integer> actual = Storage.dataStorage;
        assertEquals(expected, actual);
    }

    @Test
    public void doCalculation_keyAlreadyExist_ok() {
        FruitTransaction firstTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 30);
        FruitTransaction secondTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 10);
        Storage.dataStorage.put(firstTransaction.getFruit(), firstTransaction.getQuantity());
        handler.doCalculation(secondTransaction);
        Map<String, Integer> expected = Map.of("banana", 40);
        Map<String, Integer> actual = Storage.dataStorage;
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.dataStorage.clear();
    }

}
