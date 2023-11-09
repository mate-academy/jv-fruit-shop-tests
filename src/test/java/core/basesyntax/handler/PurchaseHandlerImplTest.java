package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.exception.DataValidationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerImplTest {
    private static FruitTransaction fruitTransaction;
    private static OperationHandler operationHandler;

    @BeforeAll
    static void setUpBeforeAll() {
        operationHandler = new PurchaseHandlerImpl();
    }

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction.FruitBuilder()
                .setOperationType(Operation.PURCHASE)
                .setFruitName("apple")
                .setFruitQuantity(100)
                .build();
    }

    @Test
    void applyOperation_purchaseFruitFromStorages_AllValidData_ok() {
        FruitsStorage.fruitsStorage.put("apple", 150);
        operationHandler.applyOperation(fruitTransaction);
        assertEquals(50, FruitsStorage.fruitsStorage.get("apple"));
    }

    @Test
    void applyOperation_purchaseMoreThanInStorage_AllValidData_ok() {
        FruitsStorage.fruitsStorage.put("apple", 50);
        DataValidationException exception = assertThrows(DataValidationException.class,
                () -> operationHandler.applyOperation(fruitTransaction));
        assertEquals("The quantity of the product for purchase exceeds "
                + "the quantity available in the storage", exception.getMessage());
    }

    @AfterEach
    public void afterEachTest() {
        FruitsStorage.fruitsStorage.clear();
    }
}
