package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationHandlerTest {
    private static FruitTransaction fruitTransaction;
    private static OperationHandler handler;

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction();
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void operate_null_notOk() {
        handler = new BalanceHandler();
        Assertions.assertThrows(RuntimeException.class, () -> handler.operate(null));
    }

    @Test
    void operate_validDataUsingBalanceOperation_ok() {
        handler = new BalanceHandler();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        handler.operate(fruitTransaction);
        int expected = 20;
        int actual = Storage.storage.get("banana");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void operate_validDataUsingPurchaseOperation_notOk() {
        Storage.storage.put("banana", 10);
        handler = new PurchaseHandler();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        Assertions.assertThrows(RuntimeException.class, () -> handler.operate(fruitTransaction));
    }

    @Test
    void operate_validDataUsingPurchaseOperation_Ok() {
        Storage.storage.put("banana", 45);
        handler = new PurchaseHandler();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(10);
        handler.operate(fruitTransaction);
        int expected = 35;
        int actual = Storage.storage.get("banana");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void operate_validDataUsingReturnOperation_ok() {
        handler = new ReturnHandler();
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        handler.operate(fruitTransaction);
        int expected = 20;
        int actual = Storage.storage.get("banana");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void operate_validDataUsingSupplyOperation_ok() {
        handler = new SupplyHandler();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        handler.operate(fruitTransaction);
        int expected = 20;
        int actual = Storage.storage.get("banana");
        Assertions.assertEquals(expected, actual);
    }
}
