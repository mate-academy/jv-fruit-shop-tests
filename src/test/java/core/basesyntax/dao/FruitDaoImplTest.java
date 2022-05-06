package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FruitDaoImplTest {
    private final String firstName = "apple";
    private final int firstAmount = 5;
    private final String secondName = "banana";
    private final int secondAmount = 10;
    private final String thirdName = "pineapple";
    private final int thirdAmount = 15;
    private final FruitDao fruitDao = new FruitDaoImpl();

    @Test
    public void addFruit_Ok() {
        Storage.fruits.clear();
        Fruit fruitThird = new Fruit();
        fruitThird.setName(thirdName);
        fruitThird.setAmount(thirdAmount);
        fruitDao.addFruit(fruitThird);
        Assert.assertTrue(Storage.fruits.contains(fruitThird));
    }

    @Test
    public void getByName_Ok() {
        Storage.fruits.clear();
        Fruit fruitFirst = new Fruit();
        fruitFirst.setName(firstName);
        fruitFirst.setAmount(firstAmount);
        fruitDao.addFruit(fruitFirst);
        Fruit fruitSecond = new Fruit();
        fruitSecond.setName(secondName);
        fruitSecond.setAmount(secondAmount);
        fruitDao.addFruit(fruitSecond);
        Assert.assertSame(fruitDao.getByName(firstName), fruitFirst);
        Assert.assertSame(fruitDao.getByName(secondName), fruitSecond);
    }

    @Test
    public void getByName_NotOk() {
        Storage.fruits.clear();
        Assert.assertNull(fruitDao.getByName(thirdName));
    }

    @Test
    public void getAll_Ok() {
        Storage.fruits.clear();
        Fruit fruitFirst = new Fruit();
        fruitFirst.setName(firstName);
        fruitFirst.setAmount(firstAmount);
        fruitDao.addFruit(fruitFirst);
        Fruit fruitSecond = new Fruit();
        fruitSecond.setName(secondName);
        fruitSecond.setAmount(secondAmount);
        fruitDao.addFruit(fruitSecond);
        List<String> expectedList = List.of(
                "apple,5", "banana,10");
        List<String> getAllList = fruitDao.getAll();
        for (int i = 0; i < getAllList.size(); i++) {
            Assert.assertEquals(expectedList.get(i), getAllList.get(i));
        }
    }

    @Test
    public void addAmount_Ok() {
        Storage.fruits.clear();
        Fruit fruitFirst = new Fruit();
        fruitFirst.setName(firstName);
        fruitFirst.setAmount(firstAmount);
        fruitDao.addFruit(fruitFirst);
        Fruit fruitAdd = new Fruit();
        int addAmount = 100;
        fruitAdd.setName(firstName);
        fruitAdd.setAmount(addAmount);
        fruitDao.addAmount(fruitAdd);
        int amountExpected = addAmount + firstAmount;
        Assert.assertEquals(amountExpected, fruitDao.getByName(firstName).getAmount());
    }

    @Test
    public void subtractAmount_Ok() {
        Storage.fruits.clear();
        Fruit fruitSecond = new Fruit();
        fruitSecond.setName(secondName);
        fruitSecond.setAmount(secondAmount);
        fruitDao.addFruit(fruitSecond);
        Fruit fruitSubtract = new Fruit();
        int subtractAmount = 5;
        fruitSubtract.setName(secondName);
        fruitSubtract.setAmount(subtractAmount);
        fruitDao.subtractAmount(fruitSubtract);
        int amountExpected = secondAmount - subtractAmount;
        Assert.assertEquals(amountExpected, fruitDao.getByName(secondName).getAmount());
    }
}
