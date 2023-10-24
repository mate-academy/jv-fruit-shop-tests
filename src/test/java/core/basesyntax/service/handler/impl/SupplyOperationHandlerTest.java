package core.basesyntax.service.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

class SupplyOperationHandlerTest {
    private static final String FRUIT_NAME = "banana";
    private static final int FRUIT_AMOUNT = 120;
    private static final int FRUIT_NEGATIVE_AMOUNT = -50;
    private static final int NUMBER_OF_OPERATION = 2;
    private static OperationHandler operationHandler;
    private static FruitTransaction transaction;
    private static FruitTransaction transactionWithNegativeAmount;

    @BeforeAll
    static void beforeAll() {
        StorageDao storageDao = new StorageDaoImpl();
        operationHandler = new SupplyOperationHandler(storageDao);
        transaction = new FruitTransaction();
        transactionWithNegativeAmount = new FruitTransaction();
    }

    @BeforeEach
    void setUp() {
        transaction.setOperation(Operation.SUPPLY);
        transaction.setFruitName(FRUIT_NAME);
        transaction.setAmount(FRUIT_AMOUNT);
        transactionWithNegativeAmount.setOperation(Operation.SUPPLY);
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
        for (int count = 0; count < NUMBER_OF_OPERATION; count++) {
            operationHandler.updateStorage(transaction);
        }
        Integer expectedFruitAmount = FRUIT_AMOUNT * NUMBER_OF_OPERATION;
        Integer actualFruitAmount = Storage.fruits.get(new Fruit(FRUIT_NAME));
        assertEquals(expectedFruitAmount, actualFruitAmount);

    }

    @Test
    void updateStorage_fruitAmountIsNegative_isNotOk() {
        assertThrows(RuntimeException.class, () -> {
            operationHandler.updateStorage(transactionWithNegativeAmount);
        }, "If amount is negative it should throw RuntimeException!");
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
