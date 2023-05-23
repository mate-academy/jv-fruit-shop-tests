package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static OperationHandler handler;

    @BeforeAll
    static void beforeAll() {
        handler = new ReturnOperationHandler();
    }

    @Test
    public void doCalculation_firstAddition_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 50);
        Map<String, Integer> expected = Map.of("banana", 50);
        handler.doCalculation(transaction);
        Map<String, Integer> actual = Storage.dataStorage;
        assertEquals(expected, actual);
    }

    @Test
    public void doCalculation_keyAlreadyExist_ok() {
        FruitTransaction firstTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 50);
        FruitTransaction secondTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 15);
        Storage.dataStorage.put(firstTransaction.getFruit(), firstTransaction.getQuantity());
        handler.doCalculation(secondTransaction);
        Map<String, Integer> expected = Map.of("banana", 65);
        Map<String, Integer> actual = Storage.dataStorage;
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.dataStorage.clear();
    }

}
