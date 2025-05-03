package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.MapStrategy;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionImplTest {
    private static MapStrategy mapStrategy;
    private static FruitDao fruitDao;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() throws Exception {
        mapStrategy = new MapStrategy();
        fruitDao = new FruitDaoImpl();
        fruitTransaction = new FruitTransactionImpl();
    }

    @Test
    public void processTransaction_Ok() {
        Storage.fruits.clear();
        List<String> lines = List.of(
                "type,name,quantity",
                "b,banana,100",
                "s,banana,100",
                "p,banana,1",
                "r,cucumber,200",
                "r,banana,100",
                "s,pineapple,1000");
        Fruit expectedFruit = new Fruit();
        expectedFruit.setName("banana");
        expectedFruit.setAmount(299);
        fruitTransaction.processTransaction(lines, mapStrategy.mapStrategy(fruitDao));
        String banana = "banana";
        int bananaAmount = 299;
        Fruit fruit = Storage.fruits.stream()
                .filter(f -> f.getName().equals(banana))
                .findFirst().get();
        Assert.assertEquals("Test failed! Expected Fruit: " + expectedFruit,
                expectedFruit, fruit);
        Assert.assertEquals("Test failed! Expected banana amount: " + bananaAmount,
                bananaAmount, fruitDao.getByName(banana).getAmount());
        String pineapple = "pineapple";
        int pineappleAmount = 1000;
        Assert.assertEquals("Test failed! Expected pineapple amount: " + pineappleAmount,
                pineappleAmount, fruitDao.getByName(pineapple).getAmount());
        String cucumber = "cucumber";
        int cucumberAmount = 200;
        Assert.assertEquals("Test failed! Expected cucumber amount: " + cucumberAmount,
                cucumberAmount, fruitDao.getByName(cucumber).getAmount());
    }

    @Test(expected = RuntimeException.class)
    public void processTransaction_NotOk() {
        Storage.fruits.clear();
        List<String> lines = List.of(
                "type,name,quantity",
                "p,cucumber,200");
        fruitTransaction.processTransaction(lines, mapStrategy.mapStrategy(fruitDao));
    }
}
