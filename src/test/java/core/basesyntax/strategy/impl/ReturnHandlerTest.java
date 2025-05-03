package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private static OperationHandler returnOperation;

    @BeforeAll
    static void beforeAll() {
        returnOperation = new ReturnHandler();
    }

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
    }

    @Test
    void executeOperationReturnHandlerValidOperation_Ok() {
        FruitTransaction transactionReturn =
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        "banana", 20);
        Storage.storage.put(transactionReturn.getFruit(), 100);
        returnOperation.executeOperation(transactionReturn);
        int expectedQuantity = 120;
        int actualQuantity = Storage.storage.get(transactionReturn.getFruit());
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void executeOperationReturnHandlerInvalidOperation_NotOk() {
        FruitTransaction transactionReturn =
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        "banana", - 20);
        assertThrows(RuntimeException.class,
                () -> returnOperation.executeOperation(transactionReturn));
    }

    @AfterAll
    static void afterAll() {
        Storage.storage.clear();
    }
}
