package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionException;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        operationHandler = new PurchaseOperationHandler();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 20);
    }

    @Test
    void handler_keyInStorageIsNull_notOk() {
        Storage.fruits.put(null, 15);
        Assertions.assertThrows(TransactionException.class,
                () -> operationHandler.handle(fruitTransaction));
    }

    @Test
    void handler_inputFruitIsNull_notOk() {
        fruitTransaction.setFruit(null);
        Assertions.assertThrows(TransactionException.class,
                () -> operationHandler.handle(fruitTransaction));
    }

    @Test
    void handler_negativeQuantityOfFruit_notOk() {
        Storage.fruits.put("apple", -15);
        Assertions.assertThrows(TransactionException.class,
                () -> operationHandler.handle(fruitTransaction));
    }

    @Test
    void handler_invalidDataForPurchaseOperation_notOk() {
        Storage.fruits.put("apple", 5);
        Assertions.assertThrows(TransactionException.class,
                () -> operationHandler.handle(fruitTransaction));
    }

    @Test
    void handler_validDataForPurchaseOperation_ok() {
        Storage.fruits.put("apple", 100);
        operationHandler.handle(fruitTransaction);
        Integer currentQuantity = fruitTransaction.getQuantity();
        Integer oldSum = Storage.fruits.get("apple");
        Assertions.assertTrue(oldSum >= currentQuantity);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
