package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.exception.InvalidOperatioExeption;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static OperationProcessor returnOperation;

    @BeforeAll
    static void setUp() {
        returnOperation = new ReturnOperation(new StorageImpl());
    }

    @Test
    void process_returnOperation_ok() {
        Storage.storage.put("banana", 105);
        FruitTransaction fruitTransaction = new FruitTransaction(
                RETURN, "banana", 85);
        returnOperation.process(fruitTransaction);
        Integer expected = 190;
        Integer actual = Storage.storage.get(fruitTransaction.getFruit());
        assertEquals(expected, actual);
    }

    @Test
    void process_negativeQuantity_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                RETURN, "banana", -20);
        assertThrows(InvalidOperatioExeption.class,
                () -> returnOperation.process(fruitTransaction));
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}
