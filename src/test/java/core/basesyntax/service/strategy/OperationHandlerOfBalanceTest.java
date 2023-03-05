package core.basesyntax.service.strategy;

import static junit.framework.TestCase.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OperationHandlerOfBalanceTest {
    public static final String DEFAULT_FRUIT = "apple";
    public static final int DEFAULT_QUANTITY = 0;
    public static final int BALANCE_QUANTITY = 20;
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;
    private static Fruit defaultFruit;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        defaultFruit = new Fruit(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        operationHandler = new OperationHandlerOfBalance();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit(DEFAULT_FRUIT);
        fruitTransaction.setQuantity(BALANCE_QUANTITY);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
    }

    @Test
    public void update_ok() {
        Storage.getFruits().add(defaultFruit);
        operationHandler.update(fruitTransaction);
        Fruit actual = Storage.getFruits()
                .stream()
                .filter(fruit -> fruit.getName()
                        .equals(OperationHandlerOfBalanceTest.defaultFruit.getName()))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Can't get value of fruit"));
        assertEquals(fruitTransaction.getQuantity(), actual.getQuantity());
    }

    @Test
    public void update_nullTransaction_notOk() {
        thrown.expect(RuntimeException.class);
        operationHandler.update(null);
    }

    @Test
    public void update_negativeBalance_notOk() {
        fruitTransaction.setQuantity(-BALANCE_QUANTITY);
        thrown.expect(RuntimeException.class);
        operationHandler.update(fruitTransaction);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getFruits().clear();
    }
}
