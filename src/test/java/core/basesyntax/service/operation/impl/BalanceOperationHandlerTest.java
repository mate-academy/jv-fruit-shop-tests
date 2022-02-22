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

public class BalanceOperationHandlerTest {
    public static final String FRUIT = "apple";
    public static final int BALANCE_QUANTITY = 20;
    public static final int DEFAULT_FRUIT_QUANTITY = 0;
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandler = new BalanceOperationHandler();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit(FRUIT);
        fruitTransaction.setQuantity(BALANCE_QUANTITY);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruit = new Fruit(FRUIT, DEFAULT_FRUIT_QUANTITY);
    }

    @Test
    public void updateBalance_nullTransaction_notOk() {
        assertThrows(RuntimeException.class, () -> operationHandler.updateBalance(null));
    }

    @Test
    public void updateBalance_negativeBalance_notOk() {
        fruitTransaction.setQuantity(-BALANCE_QUANTITY);
        assertThrows(RuntimeException.class,
                () -> operationHandler.updateBalance(fruitTransaction));
    }

    @Test
    public void updateBalance_ok() {
        FruitsStorage.getFruits().add(fruit);
        operationHandler.updateBalance(fruitTransaction);
        Fruit actual = FruitsStorage.getFruits().stream()
                .filter(f -> f.getFruitType().equals(fruit.getFruitType()))
                .findFirst()
                .get();
        assertEquals(fruitTransaction.getQuantity(), actual.getQuantity());
    }

    @After
    public void tearDown() {
        fruitTransaction.setQuantity(BALANCE_QUANTITY);
    }

    @AfterClass
    public static void afterClass() {
        FruitsStorage.getFruits().clear();
    }
}
