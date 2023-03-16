package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.exception.FruitException;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlusOperationStrategyTest {
    private static PlusOperationStrategy plusOperationStrategy;
    private static final String FRUIT_TEST = "apple";
    private static final int AMOUNT_TEST = 25;
    private static final int AMOUNT_EQUALS_OR_LESS_0 = -10;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void setUp() {
        fruitDao = new FruitDaoImpl();
        plusOperationStrategy = new PlusOperationStrategy(fruitDao);
    }

    @Test
    public void executeOperation_Ok() {
        fruitDao.clear();
        plusOperationStrategy.executeOperation(FRUIT_TEST, AMOUNT_TEST);
        assertEquals(AMOUNT_TEST, fruitDao.get(FRUIT_TEST));
    }

    @Test(expected = FruitException.class)
    public void executeOperation_NotOk_fruitNull() {
        plusOperationStrategy.executeOperation(null, AMOUNT_TEST);
    }

    @Test(expected = FruitException.class)
    public void executeOperation_NotOk_amountEqualsOrLessZero() {
        plusOperationStrategy.executeOperation(null, AMOUNT_EQUALS_OR_LESS_0);
    }
}
