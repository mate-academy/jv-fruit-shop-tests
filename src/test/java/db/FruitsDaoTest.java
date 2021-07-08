package db;

import exceptions.BalanceException;
import java.util.Map;
import models.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class FruitsDaoTest {
    private static final Fruit FIRST_FRUIT = new Fruit("banana");
    private static final Fruit SECOND_FRUIT = new Fruit("orange");
    private static final Integer BAD_QUANTITY = -20;
    private static final Integer GOOD_QUANTITY = 20;
    private static GenericDao<Fruit, Integer> dao = new FruitsDao();

    @After
    public void setUpStorageData() {
        Storage.fruits.clear();
    }

    @Test
    public void updateWithPresentElement_Ok() {
        dao.update(FIRST_FRUIT, GOOD_QUANTITY);
        Assert.assertEquals(GOOD_QUANTITY, dao.get(FIRST_FRUIT));
    }

    @Test
    public void updateWithNewElement_Ok() {
        dao.update(SECOND_FRUIT, GOOD_QUANTITY);
        Assert.assertEquals(GOOD_QUANTITY, dao.get(SECOND_FRUIT));
    }

    @Test(expected = BalanceException.class)
    public void updateInvalidFruitBadQuantity_NotOk() {
        dao.update(SECOND_FRUIT, BAD_QUANTITY * 2);
    }

    @Test(expected = BalanceException.class)
    public void updateValidFruitBadQuantity_NotOk() {
        dao.update(FIRST_FRUIT, BAD_QUANTITY * 2);
    }

    @Test
    public void getAll() {
        dao.update(FIRST_FRUIT, GOOD_QUANTITY);
        dao.update(SECOND_FRUIT, GOOD_QUANTITY);
        Map<Fruit, Integer> map = dao.getAll();
        Integer firstFruit = 0;
        Integer secondFruit = 0;
        for (Map.Entry<Fruit, Integer> entry : map.entrySet()) {
            if (entry.getKey().equals(FIRST_FRUIT)) {
                firstFruit = entry.getValue();
            }
            if (entry.getKey().equals(SECOND_FRUIT)) {
                secondFruit = entry.getValue();
            }
        }
        Assert.assertEquals(0, firstFruit - secondFruit);
    }
}
