package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler supplyOperationHandler;
    private static FruitTransaction bananaSupplyOperation;

    @BeforeAll
    static void beforeAll() {
        supplyOperationHandler = new SupplyOperationHandler();
        bananaSupplyOperation = new FruitTransaction();
    }

    @BeforeEach
    void setUp() {
        bananaSupplyOperation.setOperation(FruitTransaction.Operation.getCode("s"));
        bananaSupplyOperation.setFruit("banana");
        bananaSupplyOperation.setAmount(50);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitData.clear();
    }

    @Test
    void execute_firstOperationOfFruitTransaction_NotOk() {
        supplyOperationHandler.process(bananaSupplyOperation);
        assertTrue(Storage.fruitData
                .containsKey(bananaSupplyOperation.getFruit()));
        assertEquals(50,Storage.fruitData
                .getOrDefault(bananaSupplyOperation.getFruit(),
                        bananaSupplyOperation.getAmount()));
    }

    @Test
    void execute_correctSupplyOperation_Ok() {
        Storage.fruitData.put("banana",70);
        supplyOperationHandler.process(bananaSupplyOperation);
        assertTrue(Storage.fruitData
                .containsKey(bananaSupplyOperation.getFruit()));
        assertEquals(120, Storage
                .fruitData.getOrDefault(bananaSupplyOperation.getFruit(),
                bananaSupplyOperation.getAmount()));
    }
}
