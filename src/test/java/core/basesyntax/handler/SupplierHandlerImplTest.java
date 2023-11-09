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

class SupplierHandlerImplTest {
    private static FruitTransaction fruitTransaction;
    private static OperationHandler operationHandler;

    @BeforeAll
    static void setUpBeforeAll() {
        operationHandler = new SupplierHandlerImpl();
    }

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction.FruitBuilder()
                .setOperationType(Operation.SUPPLY)
                .setFruitName("apple")
                .setFruitQuantity(10)
                .build();
    }

    @Test
    void applyOperation_supplyFruitsFirstTime_AllValidData_ok() {
        operationHandler.applyOperation(fruitTransaction);
        assertEquals(10, FruitsStorage.fruitsStorage.get("apple"));
    }

    @Test
    void applyOperation_updateFruitStorage_AllValidData_ok() {
        FruitsStorage.fruitsStorage.put("apple", 50);
        operationHandler.applyOperation(fruitTransaction);
        assertEquals(60, FruitsStorage.fruitsStorage.get("apple"));
    }

    @AfterEach
    public void afterEachTest() {
        FruitsStorage.fruitsStorage.clear();
    }
}
