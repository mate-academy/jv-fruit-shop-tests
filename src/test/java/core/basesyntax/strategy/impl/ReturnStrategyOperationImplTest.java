package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnStrategyOperationImplTest {
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        operationHandler = new ReturnStrategyOperationImpl();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(30);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void handle_Ok() {
        Storage.fruits.put("banana", 40);
        operationHandler.handle(fruitTransaction);
        Integer actual = Storage.fruits.get(fruitTransaction.getFruit());
        Integer expected = 70;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void handle_transactionNegative_notOk() {
        fruitTransaction.setQuantity(-10);
        Assertions.assertThrows(RuntimeException.class, ()
                -> operationHandler.handle(fruitTransaction));
    }

    @Test
    void handle_negativeResult_notOk() {
        Storage.fruits.put("banana", -1000);
        Assertions.assertThrows(RuntimeException.class, ()
                -> operationHandler.handle(fruitTransaction));
    }

    @Test
    void handle_transactionNull_notOk() {
        Assertions.assertThrows(RuntimeException.class, ()
                -> operationHandler.handle(null));
    }

    @Test
    void handle_transactionOperationNull_notOk() {
        fruitTransaction.setOperation(null);
        Assertions.assertThrows(RuntimeException.class, ()
                -> operationHandler.handle(fruitTransaction));
    }

    @Test
    void handle_transactionFruitNull_notOk() {
        fruitTransaction.setFruit(null);
        Assertions.assertThrows(RuntimeException.class, ()
                -> operationHandler.handle(fruitTransaction));
    }
}
