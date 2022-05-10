package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;
    private static Fruit firstFruit;
    private static Fruit secondFruit;

    private static Fruit createFruit(String name, int amount) {
        Fruit fruit = new Fruit();
        fruit.setName(name);
        fruit.setAmount(amount);
        return fruit;
    }

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        String firstName = "apple";
        int firstAmount = 5;
        String secondName = "banana";
        int secondAmount = 10;
        firstFruit = createFruit(firstName, firstAmount);
        secondFruit = createFruit(secondName, secondAmount);
    }

    @Test
    public void addFruit_Ok() {
        fruitDao.addFruit(firstFruit);
        fruitDao.addFruit(secondFruit);
        Assert.assertTrue("Test failed! Storage doesn't contain " + firstFruit,
                Storage.fruits.contains(firstFruit));
        Assert.assertTrue("Test failed! Storage doesn't contain " + secondFruit,
                Storage.fruits.contains(secondFruit));
    }

    @Test
    public void getByName_Ok() {
        fruitDao.addFruit(firstFruit);
        fruitDao.addFruit(secondFruit);
        String firstName = "apple";
        String secondName = "banana";
        Assert.assertSame("Test failed! First element should be " + firstFruit,
                firstFruit, fruitDao.getByName(firstName));
        Assert.assertSame("Test failed! First element should be " + secondFruit,
                secondFruit, fruitDao.getByName(secondName));
    }

    @Test
    public void getByName_NotOk() {
        String name = "apple";
        Assert.assertNull(fruitDao.getByName(name));
    }

    @Test
    public void getAll_Ok() {
        fruitDao.addFruit(firstFruit);
        fruitDao.addFruit(secondFruit);
        List<String> expectedList = List.of(
                "apple,5", "banana,10");
        List<String> actualList = fruitDao.getAll();
        Assert.assertEquals("Test failed! Expected size of List: " + expectedList.size(),
                expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            Assert.assertEquals("Test failed! The line №: " + i + " is not as expected",
                    expectedList.get(i), actualList.get(i));
        }
    }

    @Test
    public void addAmount_Ok() {
        fruitDao.addFruit(firstFruit);
        String name = "apple";
        int addAmount = 100;
        int amountExpected = addAmount + fruitDao.getByName(name).getAmount();
        Fruit fruit = createFruit(name, addAmount);
        fruitDao.addAmount(fruit);
        Assert.assertEquals("Test failed! Expected amount: " + amountExpected,
                amountExpected, fruitDao.getByName(name).getAmount());
    }

    @Test
    public void subtractAmount_Ok() {
        fruitDao.addFruit(secondFruit);
        String name = "banana";
        int amount = 5;
        int amountExpected = fruitDao.getByName(name).getAmount() - amount;
        Fruit subtractFruit = createFruit(name, amount);
        fruitDao.subtractAmount(subtractFruit);
        Assert.assertEquals("Test failed! Expected amount: " + amountExpected,
                amountExpected, fruitDao.getByName(name).getAmount());
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
