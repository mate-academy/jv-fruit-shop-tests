package core.basesyntax.strategy.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.StorageFruits;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationTest {
    private SupplyOperation supplyOperation;

    @Before
    public void setUp() {
        supplyOperation = new SupplyOperation(new FruitDaoImpl());
    }

    @Test (expected = RuntimeException.class)
    public void apply_dailyTransactionNull_notOk() {
        supplyOperation.apply(null);
    }

    @Test
    public void apply_allOk() {
        StorageFruits.fruits.add(new Fruit("orange", 200));
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "orange", 1000);
        supplyOperation.apply(fruitTransaction);
        Fruit actual = StorageFruits.fruits.get(0);
        Fruit expected = new Fruit("orange", 1200);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        StorageFruits.fruits.clear();
    }
}
