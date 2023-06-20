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
    private static final String EXCEPTION_MESSAGE
            = TransactionException.class.toString();
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

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
                () -> operationHandler.handle(fruitTransaction),
                String.format("%s should throw for null value in storage", EXCEPTION_MESSAGE));
    }

    @Test
    void handler_inputFruitIsNull_notOk() {
        fruitTransaction.setFruit(null);
        Assertions.assertThrows(TransactionException.class,
                () -> operationHandler.handle(fruitTransaction),
                String.format("%s should throw for null input value", EXCEPTION_MESSAGE));
    }

    @Test
    void handler_negativeQuantityOfFruit_notOk() {
        Storage.fruits.put("apple", -15);
        Assertions.assertThrows(TransactionException.class,
                () -> operationHandler.handle(fruitTransaction),
                String.format("%s should throw for negative quantity", EXCEPTION_MESSAGE));
    }

    @Test
    void handler_validSubtraction_notOk() {
        Storage.fruits.put("apple", 5);
        Assertions.assertThrows(TransactionException.class,
                () -> operationHandler.handle(fruitTransaction),
                String.format("%s should throw for incorrect subtraction", EXCEPTION_MESSAGE));
    }

    @Test
    void handler_correctSupplying_Ok() {
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
