package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerImplTest {
    private static final String FRUIT_NAME = "apple";
    private static final int DEFAULT_FRUIT_QUANTITY = 10;
    private static final int NEW_FRUIT_QUANTITY = 20;
    private static FruitTransaction fruitTransaction;
    private static OperationHandler operationHandler;

    @BeforeAll
    static void setUpBeforeAll() {
        operationHandler = new BalanceHandlerImpl();
    }

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction.FruitBuilder()
                .setOperationType(Operation.BALANCE)
                .setFruitName(FRUIT_NAME)
                .setFruitQuantity(DEFAULT_FRUIT_QUANTITY)
                .build();
    }

    @Test
    void applyOperation_addNewFruits_AllValidData_ok() {
        FruitsStorage.FRUITS_STORAGE.clear();
        operationHandler.applyOperation(fruitTransaction);
        assertEquals(DEFAULT_FRUIT_QUANTITY, FruitsStorage.FRUITS_STORAGE.get(FRUIT_NAME));
    }

    @Test
    void applyOperation_updateFruitStorageBalance_AllValidData_ok() {
        FruitsStorage.FRUITS_STORAGE.put(FRUIT_NAME, DEFAULT_FRUIT_QUANTITY);
        operationHandler.applyOperation(fruitTransaction);
        assertEquals(NEW_FRUIT_QUANTITY, FruitsStorage.FRUITS_STORAGE.get(FRUIT_NAME));
    }

    @AfterEach
    public void afterEachTest() {
        FruitsStorage.FRUITS_STORAGE.clear();
    }
}
