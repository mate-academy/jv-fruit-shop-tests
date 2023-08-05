package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerImplTest {
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
                .setFruitName("apple")
                .setFruitQuantity(100)
                .build();
    }

    @Test
    void applyOperation_ReturnFruitsToEmptyStorage_AllValidData_ok() {
        FruitsStorage.fruitsStorage.put("apple", 0);
        operationHandler.applyOperation(fruitTransaction);
        assertEquals(100, FruitsStorage.fruitsStorage.get("apple"));
    }

    @Test
    void applyOperation_ReturnFruitsToFruitStorage_AllValidData_ok() {
        FruitsStorage.fruitsStorage.put("apple", 50);
        operationHandler.applyOperation(fruitTransaction);
        assertEquals(150, FruitsStorage.fruitsStorage.get("apple"));
    }

    @AfterEach
    public void afterEachTest() {
        FruitsStorage.fruitsStorage.clear();
    }
}
