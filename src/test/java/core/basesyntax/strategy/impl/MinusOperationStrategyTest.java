package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.exception.FruitException;
import org.junit.BeforeClass;
import org.junit.Test;

public class MinusOperationStrategyTest {
    private static MinusOperationStrategy minusOperationStrategy;
    private static final String FRUIT_TEST = "apple";
    private static final int AMOUNT_TEST = 25;
    private static final int AMOUNT_EQUALS_OR_LESS_0 = -10;
    private static final int AMOUNT_EXIST_LESS_AMOUNT_OPERATION = 15;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void setUp() {
        fruitDao = new FruitDaoImpl();
        minusOperationStrategy = new MinusOperationStrategy(fruitDao);
    }

    @Test(expected = FruitException.class)
    public void executeOperation_NotOk_fruitNull() {
        minusOperationStrategy.executeOperation(null, AMOUNT_TEST);
    }

    @Test(expected = FruitException.class)
    public void executeOperation_NotOk_amountEqualsOrLessZero() {
        minusOperationStrategy.executeOperation(null, AMOUNT_EQUALS_OR_LESS_0);
    }

    @Test(expected = FruitException.class)
    public void executeOperation_NotOk_fruitDaoEmpty() {
        fruitDao.clear();
        minusOperationStrategy.executeOperation(FRUIT_TEST, AMOUNT_TEST);
    }

    @Test(expected = FruitException.class)
    public void executeOperation_NotOk_existLessAmountOperation() {
        fruitDao.clear();
        fruitDao.put(FRUIT_TEST, AMOUNT_EXIST_LESS_AMOUNT_OPERATION);
        minusOperationStrategy.executeOperation(FRUIT_TEST, AMOUNT_TEST);
    }

    @Test
    public void executeOperation_Ok() {
        fruitDao.clear();
        fruitDao.put(FRUIT_TEST, AMOUNT_TEST + 1);
        minusOperationStrategy.executeOperation(FRUIT_TEST, AMOUNT_TEST);
        assertEquals(1, fruitDao.get(FRUIT_TEST));
    }
}
