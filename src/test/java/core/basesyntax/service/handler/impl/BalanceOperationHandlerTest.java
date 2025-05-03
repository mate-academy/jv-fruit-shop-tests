package core.basesyntax.service.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final String FRUIT_NAME = "apple";
    private static final int FRUIT_AMOUNT = 50;
    private static final int FRUIT_NEGATIVE_AMOUNT = -50;
    private static OperationHandler operationHandler;
    private static FruitTransaction transaction;
    private static FruitTransaction transactionWithNegativeAmount;

    @BeforeAll
    static void beforeAll() {
        StorageDao storageDao = new StorageDaoImpl();
        operationHandler = new BalanceOperationHandler(storageDao);
        transaction = new FruitTransaction();
        transactionWithNegativeAmount = new FruitTransaction();
    }

    @BeforeEach
    void setUp() {
        transaction.setOperation(Operation.BALANCE);
        transaction.setFruitName(FRUIT_NAME);
        transaction.setAmount(FRUIT_AMOUNT);
        transactionWithNegativeAmount.setOperation(Operation.BALANCE);
        transactionWithNegativeAmount.setFruitName(FRUIT_NAME);
        transactionWithNegativeAmount.setAmount(FRUIT_NEGATIVE_AMOUNT);
    }

    @Test
    void updateStorage_isOk() {
        operationHandler.updateStorage(transaction);
        Integer expectedFruitAmount = FRUIT_AMOUNT;
        Integer actualFruitAmount = Storage.fruits.get(new Fruit(FRUIT_NAME));

        assertEquals(expectedFruitAmount, actualFruitAmount);
    }

    @Test
    void updateStorage_fruitInStorage_isOk() {
        operationHandler.updateStorage(transaction);
        operationHandler.updateStorage(transaction);
        Integer expectedFruitAmount = FRUIT_AMOUNT;
        Integer actualFruitAmount = Storage.fruits.get(new Fruit(FRUIT_NAME));

        assertEquals(expectedFruitAmount, actualFruitAmount);
    }

    @Test
    void updateStorage_fruitAmountIsNegative_isNotOk() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            operationHandler.updateStorage(transactionWithNegativeAmount);
        }, "If amount is negative it should throw RuntimeException!");

        String expectedMessage = "Amount is less then zero!!!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
