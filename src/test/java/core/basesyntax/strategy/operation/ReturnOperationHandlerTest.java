package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionException;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        operationHandler = new ReturnOperationHandler();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "apple", 20);
    }

    @Test
    void handler_validDataForReturnOperation_ok() {
        Storage.fruits.put("apple", 10);
        Integer expected = Storage.fruits.get(fruitTransaction.getFruit())
                + fruitTransaction.getQuantity();
        operationHandler.handle(fruitTransaction);
        Integer actual = Storage.fruits.get(fruitTransaction.getFruit());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void handler_invalidDataForReturnOperation_notOk() {
        Storage.fruits.put("apple", 60);
        Integer expected = Storage.fruits.get(fruitTransaction.getFruit())
                - fruitTransaction.getQuantity();
        operationHandler.handle(fruitTransaction);
        Integer actual = Storage.fruits.get(fruitTransaction.getFruit());
        Assertions.assertNotEquals(expected, actual);
    }

    @Test
    void handler_negativeQuantityOfFruit_notOk() {
        Storage.fruits.put("apple", -15);
        Assertions.assertThrows(TransactionException.class,
                () -> operationHandler.handle(fruitTransaction));
    }

    @Test
    void handler_keyInStorageIsNull_notOk() {
        Storage.fruits.put(null, 15);
        Assertions.assertThrows(TransactionException.class,
                () -> operationHandler.handle(fruitTransaction));
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
