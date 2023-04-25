package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnOperationHandler;
    private static FruitTransaction bananaReturnOperation;

    @BeforeAll
    static void beforeAll() {
        returnOperationHandler = new ReturnOperationHandler();
        bananaReturnOperation = new FruitTransaction();
    }

    @BeforeEach
    void setUp() {
        bananaReturnOperation.setFruit("banana");
        bananaReturnOperation.setOperation(FruitTransaction.Operation.RETURN);
        bananaReturnOperation.setAmount(20);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitData.clear();
    }

    @Test
    void execute_firstOperationOfFruitTransaction_NotOk() {
        returnOperationHandler.process(bananaReturnOperation);
        assertTrue(Storage.fruitData
                .containsKey(bananaReturnOperation.getFruit()));
        assertEquals(20, Storage.fruitData
                .get(bananaReturnOperation.getFruit()));
    }

    @Test
    void execute_correctReturnOperation_Ok() {
        Storage.fruitData.put(bananaReturnOperation.getFruit(),100);
        returnOperationHandler.process(bananaReturnOperation);
        assertTrue(Storage.fruitData
                .containsKey(bananaReturnOperation.getFruit()));
        assertEquals(120,Storage.fruitData
                .get(bananaReturnOperation.getFruit()));
    }
}
