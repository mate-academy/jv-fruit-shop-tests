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

class PurchaseOperationHandlerTest {
    private static final String FRUIT_NAME = "banana";
    private static final int FRUIT_AMOUNT = 120;
    private static final int FRUIT_NEGATIVE_AMOUNT = -50;
    private static final int FRUIT_GREATER_AMOUNT = 140;
    private static final int FRUIT_LESS_AMOUNT = 100;
    private static OperationHandler operationHandler;
    private static FruitTransaction transaction;
    private static FruitTransaction transactionWithNegativeAmount;
    private static FruitTransaction transactionWithGreaterAmount;
    private static FruitTransaction transactionWithLessAmount;

    @BeforeAll
    static void beforeAll() {
        StorageDao storageDao = new StorageDaoImpl();
        operationHandler = new PurchaseOperationHandler(storageDao);
        transaction = new FruitTransaction();
        transactionWithNegativeAmount = new FruitTransaction();
        transactionWithGreaterAmount = new FruitTransaction();
        transactionWithLessAmount = new FruitTransaction();
    }

    @BeforeEach
    void setUp() {
        transaction.setOperation(Operation.PURCHASE);
        transaction.setFruitName(FRUIT_NAME);
        transaction.setAmount(FRUIT_AMOUNT);
        transactionWithNegativeAmount.setOperation(Operation.PURCHASE);
        transactionWithNegativeAmount.setFruitName(FRUIT_NAME);
        transactionWithNegativeAmount.setAmount(FRUIT_NEGATIVE_AMOUNT);
        transactionWithGreaterAmount.setOperation(Operation.PURCHASE);
        transactionWithGreaterAmount.setFruitName(FRUIT_NAME);
        transactionWithGreaterAmount.setAmount(FRUIT_GREATER_AMOUNT);
        transactionWithLessAmount.setOperation(Operation.PURCHASE);
        transactionWithLessAmount.setFruitName(FRUIT_NAME);
        transactionWithLessAmount.setAmount(FRUIT_LESS_AMOUNT);
    }

    @Test
    void updateStorage_emptyStorage_isNotOk() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            operationHandler.updateStorage(transaction);
        }, "If storage does not have such fruit it should throw Runtime Exception");

        String expectedMessage = "There is no such fruit!!!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void updateStorage_fruitInStorage_isOk() {
        Storage.fruits.put(new Fruit(FRUIT_NAME), FRUIT_AMOUNT);
        operationHandler.updateStorage(transaction);
        Integer expectedFruitAmount = FRUIT_AMOUNT - transaction.getAmount();
        Integer actualFruitAmount = Storage.fruits.get(new Fruit(FRUIT_NAME));
        assertEquals(expectedFruitAmount, actualFruitAmount);
        Storage.fruits.put(new Fruit(FRUIT_NAME), FRUIT_AMOUNT);
        operationHandler.updateStorage(transactionWithLessAmount);
        Integer expectedFruitAmount2 = FRUIT_AMOUNT - transactionWithLessAmount.getAmount();
        Integer actualFruitAmount2 = Storage.fruits.get(new Fruit(FRUIT_NAME));
        assertEquals(expectedFruitAmount2, actualFruitAmount2);
    }

    @Test
    void updateStorage_fruitAmountIsGreaterThanInStorage_isNotOk() {
        Storage.fruits.put(new Fruit(FRUIT_NAME), FRUIT_AMOUNT);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            operationHandler.updateStorage(transactionWithGreaterAmount);
        }, "If amount is greater than in Storage it should throw RuntimeException!");

        String expectedMessage = " isn't enough!!!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateStorage_fruitAmountIsNegative_isNotOk() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            operationHandler.updateStorage(transactionWithNegativeAmount);
        }, "If amount is negative it should throw RuntimeException!");

        String expectedMessage = "Amount is less then zero!!!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));;
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
