package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionException;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        operationHandler = new BalanceOperationHandler();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 20);
    }

    @Test
    void handler_fruitIsNull_notOk() {
        fruitTransaction.setFruit(null);
        Assertions.assertThrows(TransactionException.class,
                () -> operationHandler.handle(fruitTransaction));
    }

    @Test
    void handler_negativeQuantity_notOk() {
        fruitTransaction.setQuantity(-15);
        Assertions.assertThrows(TransactionException.class,
                () -> operationHandler.handle(fruitTransaction));
    }

    @Test
    void handler_validDataForBalanceOperation_ok() {
        operationHandler.handle(fruitTransaction);
        Integer expected = fruitTransaction.getQuantity();
        Integer actual = Storage.fruits.get("apple");
        Assertions.assertEquals(expected, actual);
    }
}
