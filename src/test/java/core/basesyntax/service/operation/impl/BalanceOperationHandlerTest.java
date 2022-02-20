package core.basesyntax.service.operation.impl;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    public static final String FRUIT = "apple";
    public static final int BALANCE_QUANTITY = 20;
    public static final int DEFAULT_FRUIT_QUANTITY = 0;
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;
    private Fruit fruit;

    @Before
    public void setUp() throws Exception {
        operationHandler = new BalanceOperationHandler();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit(FRUIT);
        fruitTransaction.setQuantity(BALANCE_QUANTITY);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruit = new Fruit(FRUIT, DEFAULT_FRUIT_QUANTITY);
    }

    @Test
    public void updateBalanceForNullTransaction_notOk() {
        assertThrows(RuntimeException.class, () -> operationHandler.updateBalance(null));
    }

    @Test
    public void updateBalanceForNegativeBalance_notOk() {
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

    @AfterClass
    public static void afterClass() throws Exception {
        FruitsStorage.getFruits().clear();
    }
}
