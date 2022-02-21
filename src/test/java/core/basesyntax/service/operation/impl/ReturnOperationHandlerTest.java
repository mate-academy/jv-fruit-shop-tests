package core.basesyntax.service.operation.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static final String FRUIT_TYPE = "banana";
    private static final int DEFAULT_FRUIT_QUANTITY = 20;
    private static final Fruit FRUIT = new Fruit(FRUIT_TYPE, DEFAULT_FRUIT_QUANTITY);
    private static final int RETURN_QUANTITY = 15;
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        FruitsStorage.getFruits().add(FRUIT);
        operationHandler = new ReturnOperationHandler();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit(FRUIT_TYPE);
        fruitTransaction.setQuantity(RETURN_QUANTITY);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
    }

    @Test
    public void updateBalance_ok() {
        int fruitQuantityBeforeTransaction = FRUIT.getQuantity();
        operationHandler.updateBalance(fruitTransaction);
        Fruit actual = FruitsStorage.getFruits().stream()
                .filter(f -> f.getFruitType().equals(FRUIT.getFruitType()))
                .findFirst()
                .get();
        assertEquals(fruitQuantityBeforeTransaction + fruitTransaction.getQuantity(),
                actual.getQuantity());
    }

    @Test
    public void updateBalanceForNullTransaction_notOk() {
        assertThrows(RuntimeException.class, () -> operationHandler.updateBalance(null));
    }

    @Test
    public void updateBalanceForNegativeBalance_notOk() {
        fruitTransaction.setQuantity(-RETURN_QUANTITY);
        assertThrows(RuntimeException.class,
                () -> operationHandler.updateBalance(fruitTransaction));
    }

    @After
    public void tearDown() {
        fruitTransaction.setQuantity(RETURN_QUANTITY);
    }

    @AfterClass
    public static void afterClass() {
        FruitsStorage.getFruits().clear();
    }
}
