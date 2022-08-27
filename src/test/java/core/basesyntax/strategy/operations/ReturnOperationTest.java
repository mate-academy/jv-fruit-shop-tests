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

public class ReturnOperationTest {
    private ReturnOperation returnOperation;
    private FruitDao fruitDao;

    @Before
    public void setUp() throws Exception {
        fruitDao = new FruitDaoImpl();
        returnOperation = new ReturnOperation(fruitDao);
    }

    @Test (expected = WrongDataException.class)
    public void apply_dailyTransactionNull_notOk() {
        returnOperation.apply(null);
    }

    @Test
    public void apply_allOk() {
        StorageFruits.fruits.add(new Fruit("orange", 200));
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "orange", 100);
        returnOperation.apply(fruitTransaction);
        Fruit actual = new Fruit();
        actual = StorageFruits.fruits.get(0);
        Fruit expected = new Fruit("orange", 300);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        StorageFruits.fruits.clear();
    }
}
