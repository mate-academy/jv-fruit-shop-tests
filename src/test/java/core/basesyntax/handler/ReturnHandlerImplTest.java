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

class ReturnHandlerImplTest {
    private static final String FRUIT_NAME = "apple";
    private static final int DEFAULT_QUANTITY = 100;
    private static final int FRUIT_QUANTITY1 = 0;
    private static final int FRUIT_QUANTITY1_RESULT = 100;
    private static final int FRUIT_QUANTITY2 = 50;
    private static final int FRUIT_QUANTITY2_RESULT = 150;
    private static FruitTransaction fruitTransaction;
    private static OperationHandler operationHandler;

    @BeforeAll
    static void setUpBeforeAll() {
        operationHandler = new ReturnOperationHandlerImpl();
    }

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction.FruitBuilder()
                .setOperationType(Operation.RETURN)
                .setFruitName(FRUIT_NAME)
                .setFruitQuantity(DEFAULT_QUANTITY)
                .build();
    }

    @Test
    void applyOperation_returnFruitsToEmptyStorage_AllValidData_ok() {
        FruitsStorage.FRUITS_STORAGE.put(FRUIT_NAME, FRUIT_QUANTITY1);
        operationHandler.applyOperation(fruitTransaction);
        assertEquals(FRUIT_QUANTITY1_RESULT, FruitsStorage.FRUITS_STORAGE.get(FRUIT_NAME));
    }

    @Test
    void applyOperation_returnFruitsToFruitStorage_AllValidData_ok() {
        FruitsStorage.FRUITS_STORAGE.put(FRUIT_NAME, FRUIT_QUANTITY2);
        operationHandler.applyOperation(fruitTransaction);
        assertEquals(FRUIT_QUANTITY2_RESULT, FruitsStorage.FRUITS_STORAGE.get(FRUIT_NAME));
    }

    @AfterEach
    public void afterEachTest() {
        FruitsStorage.FRUITS_STORAGE.clear();
    }
}
