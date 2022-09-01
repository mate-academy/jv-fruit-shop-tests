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

public class BalanceOperationTest {
    private BalanceOperation balanceOperation;
    private FruitDao fruitDao;

    @Before
    public void setUp() throws Exception {
        fruitDao = new FruitDaoImpl();
        balanceOperation = new BalanceOperation(fruitDao);
    }

    @Test (expected = WrongDataException.class)
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
    public void tearDown() throws Exception {
        StorageFruits.fruits.clear();
    }
}
