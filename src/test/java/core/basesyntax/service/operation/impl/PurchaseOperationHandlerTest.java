package core.basesyntax.service.operation.impl;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final String FRUIT_TYPE = "apple";
    private static final int DEFAULT_FRUIT_QUANTITY = 20;
    private static final Fruit FRUIT = new Fruit(FRUIT_TYPE, DEFAULT_FRUIT_QUANTITY);
    private static final int PURCHASE_QUANTITY = 7;
    private static final int PURCHASE_GREATER_THAN_BALANCE = 30;
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() throws Exception {
        FruitsStorage.getFruits().add(FRUIT);
        operationHandler = new PurchaseOperationHandler();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit(FRUIT_TYPE);
        fruitTransaction.setQuantity(PURCHASE_QUANTITY);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
    }

    @Test
    public void updateBalanceForNullTransaction_notOk() {
        assertThrows(RuntimeException.class,
                () -> operationHandler.updateBalance(null));
    }

    @Test
    public void updateBalanceForNegativeBalance_notOk() {
        fruitTransaction.setQuantity(-PURCHASE_QUANTITY);
        assertThrows(RuntimeException.class,
                () -> operationHandler.updateBalance(fruitTransaction));
    }

    @Test
    public void updateBalance_ok() {
        int fruitQuantityBeforeTransaction = FRUIT.getQuantity();
        operationHandler.updateBalance(fruitTransaction);
        Fruit actual = FruitsStorage.getFruits().stream()
                .filter(f -> f.getFruitType().equals(FRUIT.getFruitType()))
                .findFirst()
                .get();
        assertEquals(fruitQuantityBeforeTransaction
                - fruitTransaction.getQuantity(), actual.getQuantity());
    }

    @Test
    public void purchaseGreaterThanPreviousBalance_notOk() {
        fruitTransaction.setQuantity(PURCHASE_GREATER_THAN_BALANCE);
        assertThrows(RuntimeException.class,
                () -> operationHandler.updateBalance(fruitTransaction));
    }

    @After
    public void tearDown() {
        fruitTransaction.setQuantity(PURCHASE_QUANTITY);
    }

    @AfterClass
    public static void afterClass() {
        FruitsStorage.getFruits().clear();
    }
}
