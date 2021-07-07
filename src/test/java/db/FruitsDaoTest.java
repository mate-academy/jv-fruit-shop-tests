package db;

import exceptions.BalanceException;
import models.Fruit;
import org.junit.Assert;
import org.junit.Test;
import java.util.Map;

public class FruitsDaoTest {
private static final GenericDao FRUITS_DAO = new FruitsDao();
private final static Fruit VALID_FRUIT = new Fruit("banana");
private final static Fruit INVALID_FRUIT = new Fruit("orange");
private final static Fruit RANDOM_FRUIT = new Fruit("tangerine");
private final static Fruit SECOND_RANDOM_FRUIT = new Fruit("lemon");
private final static Integer BAD_QUANTITY = -20;
private final static Integer GOOD_QUANTITY = 20;

    @Test
    public void updateWithValidElement_Ok() {
        FRUITS_DAO.update(VALID_FRUIT, GOOD_QUANTITY);
        Assert.assertEquals(GOOD_QUANTITY, FRUITS_DAO.get(VALID_FRUIT));
    }

    @Test
    public void updateWithNewElement_Ok() {
        FRUITS_DAO.update(INVALID_FRUIT, GOOD_QUANTITY);
        Assert.assertEquals(GOOD_QUANTITY, FRUITS_DAO.get(INVALID_FRUIT));
    }

    @Test(expected = BalanceException.class)
    public void updateInvalidFruitBadQuantity_NotOk() {
        FRUITS_DAO.update(INVALID_FRUIT, BAD_QUANTITY * 2);
    }

    @Test(expected = BalanceException.class)
    public void updateValidFruitBadQuantity_NotOk() {
        FRUITS_DAO.update(VALID_FRUIT, BAD_QUANTITY * 2);
    }

    @Test
    public void getAll() {
        FRUITS_DAO.update(RANDOM_FRUIT, GOOD_QUANTITY);
        FRUITS_DAO.update(SECOND_RANDOM_FRUIT, GOOD_QUANTITY);
        Map<Fruit, Integer> map = FRUITS_DAO.getAll();
        Integer firstFruit = 0;
        Integer secondFruit = 0;
        for (Map.Entry<Fruit, Integer> entry : map.entrySet()) {
            if (entry.getKey().equals(VALID_FRUIT)) {
                firstFruit = entry.getValue();
            }
            if (entry.getKey().equals(INVALID_FRUIT)) {
                firstFruit = entry.getValue();
            }
        }
        Assert.assertEquals(0, firstFruit - secondFruit);
    }
}