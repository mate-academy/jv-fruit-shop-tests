package core.basesyntax.strategy.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.StorageFruits;
import core.basesyntax.exceptions.WrongDataException;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationTest {
    private PurchaseOperation purchaseOperation;
    private FruitDao fruitDao;
    private Fruit actual;

    @Before
    public void setUp() throws Exception {
        fruitDao = new FruitDaoImpl();
        purchaseOperation = new PurchaseOperation(fruitDao);
        actual = new Fruit();
        StorageFruits.fruits.add(new Fruit("orange", 200));
    }

    @Test (expected = WrongDataException.class)
    public void apply_dailyTransactionNull_notOk() {
        purchaseOperation.apply(null);
    }

    @Test
    public void apply_allOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "orange", 100);
        purchaseOperation.apply(fruitTransaction);
        actual = StorageFruits.fruits.get(0);
        Fruit expected = new Fruit("orange", 100);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void apply_notEnoughFruitInStock_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "orange", 300);
        purchaseOperation.apply(fruitTransaction);
        actual = StorageFruits.fruits.get(0);
        Fruit expected = new Fruit("orange", 100);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        StorageFruits.fruits.clear();
    }
}
