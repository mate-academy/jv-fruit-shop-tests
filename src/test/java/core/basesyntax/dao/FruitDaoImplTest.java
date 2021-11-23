package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitDaoImplTest {
    private FruitDao fruitDao;
    private Fruit invalidFruit;
    private boolean thrown = false;

    @Before
    public void setUp() {
        Storage.getFruits().clear();
        fruitDao = new FruitDaoImpl();
        invalidFruit = new Fruit.FruitBuilder().setName("orange").build();
    }

    @Test
    public void checkEmptyList_Ok() {
        Assert.assertEquals(0,Storage.getFruits().size());
    }

    @Test
    public void validFruit_Ok() {
        try {
            Storage.getFruits().add((new Fruit.FruitBuilder()
                    .setName("apple")
                    .setQuantity(0)
                    .build()));
            Storage.getFruits().add((new Fruit.FruitBuilder()
                    .setName("banana")
                    .setQuantity(0)
                    .build()));
            fruitDao.get(invalidFruit.getName());
        } catch (RuntimeException e) {
            thrown = true;
        }
        Assert.assertTrue("Apples and bananas can be in the store",thrown);
    }

    @Test
    public void getFruitFromStore() {
        Fruit apple = new Fruit.FruitBuilder().setName("apple").setQuantity(30).build();
        Storage.getFruits().add(apple);
        Assert.assertTrue("We can get apple or bananas from storage",
                apple.equals(fruitDao.get("apple")));
        try {
            fruitDao.get(invalidFruit.getName());
        } catch (RuntimeException e) {
            thrown = true;
        }
        Assert.assertTrue("We have not fruit" + invalidFruit.getName(),thrown);
    }

    @Test
    public void addFruitToStore() {
        Fruit apple = new Fruit.FruitBuilder()
                .setName("apple")
                .setQuantity(30)
                .build();
        fruitDao.add(apple.getName(),apple.getQuantity());
        Assert.assertEquals(true, Storage.getFruits().contains(apple));
        Assert.assertEquals(30,fruitDao.get("apple").getQuantity());
    }

    @Test
    public void updateFruit() {
        Storage.getFruits().add((new Fruit.FruitBuilder()
                .setName("apple")
                .setQuantity(0)
                .build()));
        Storage.getFruits().add((new Fruit.FruitBuilder()
                .setName("banana")
                .setQuantity(0)
                .build()));
        String fruitName = "apple";
        fruitDao.update(fruitName,20);
        Assert.assertEquals(20,fruitDao.get(fruitName).getQuantity());
        Storage.getFruits().clear();
    }

    @AfterClass
    public static void afterClass() {
        Storage.getFruits().clear();
    }
}
