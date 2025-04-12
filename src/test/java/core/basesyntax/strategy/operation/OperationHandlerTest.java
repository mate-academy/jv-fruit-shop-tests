package core.basesyntax.strategy.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationHandlerTest {
    private static FruitTransaction fruitTransaction;
    private static OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction();
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void getOperation_null_notOk() {
        operationHandler = new BalanceHandler();
        assertThrows(NullPointerException.class,
                () -> operationHandler.getOperation(null));
    }

    @Test
    void getOperation_validDataUsingBalanceHandler_ok() {
        operationHandler = new BalanceHandler();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        operationHandler.getOperation(fruitTransaction);
        int expected = 20;
        int actual = Storage.storage.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    void getOperation_validDataUsingSupplyOperation_ok() {
        operationHandler = new SupplyHandler();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(100);
        operationHandler.getOperation(fruitTransaction);
        int expected = 100;
        int actual = Storage.storage.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    void getOperation_validDataUsingReturnOperation_ok() {
        operationHandler = new ReturnHandler();
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(11);
        operationHandler.getOperation(fruitTransaction);
        int expected = 11;
        int actual = Storage.storage.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    void getOperation_validDataUsingPurchaseOperation_ok() {
        Storage.storage.put("banana", 39);
        operationHandler = new PurchaseHandler();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(17);
        operationHandler.getOperation(fruitTransaction);
        int expected = 22;
        int actual = Storage.storage.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    void getOperation_validdataUsingPurchaseOperation_notOk() {
        Storage.storage.put("banana", 7);
        operationHandler = new PurchaseHandler();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(29);
        assertThrows(RuntimeException.class,
                () -> operationHandler.getOperation(fruitTransaction));
    }
}