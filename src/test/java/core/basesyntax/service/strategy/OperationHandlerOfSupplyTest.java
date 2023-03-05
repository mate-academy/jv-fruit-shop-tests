package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OperationHandlerOfSupplyTest {
    private static final String DEFAULT_FRUIT = "banana";
    private static final int DEFAULT_QUANTITY = 40;
    private static final int SUPPLY_QUANTITY = 15;
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;
    private static Fruit defaultFruit;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        defaultFruit = new Fruit(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        Storage.getFruits().add(defaultFruit);
        operationHandler = new OperationHandlerOfSupply();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit(DEFAULT_FRUIT);
        fruitTransaction.setQuantity(SUPPLY_QUANTITY);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
    }

    @Test
    public void update_ok() {
        int quantityBeforeTransaction = defaultFruit.getQuantity();
        operationHandler.update(fruitTransaction);
        Fruit actual = Storage.getFruits().stream()
                .filter(fruit -> fruit.getName().equals(defaultFruit.getName()))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Can't get value of fruit"));
        assertEquals(quantityBeforeTransaction + fruitTransaction.getQuantity(),
                actual.getQuantity());
    }

    @Test
    public void update_nullTransaction_notOk() {
        thrown.expect(RuntimeException.class);
        operationHandler.update(null);
    }

    @Test
    public void update_negativeBalance_notOk() {
        fruitTransaction.setQuantity(-SUPPLY_QUANTITY);
        thrown.expect(RuntimeException.class);
        operationHandler.update(fruitTransaction);
    }

    @After
    public void tearDown() {
        fruitTransaction.setQuantity(SUPPLY_QUANTITY);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getFruits().clear();
    }
}
