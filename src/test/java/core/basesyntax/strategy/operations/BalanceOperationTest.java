package core.basesyntax.strategy.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.StorageFruits;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationTest {
    private BalanceOperation balanceOperation;

    @Before
    public void setUp() {
        balanceOperation = new BalanceOperation(new FruitDaoImpl());
    }

    @Test (expected = RuntimeException.class)
    public void apply_dailyTransactionNull_notOk() {
        balanceOperation.apply(null);
    }

    @Test
    public void apply_allOk() {
        Fruit expected = new Fruit("orange", 100);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                expected.getFruitName(), expected.getQuantity());
        balanceOperation.apply(fruitTransaction);
        Fruit actual = StorageFruits.fruits.get(0);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        StorageFruits.fruits.clear();
    }
}
