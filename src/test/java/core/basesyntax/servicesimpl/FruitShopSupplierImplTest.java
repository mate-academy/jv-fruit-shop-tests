package core.basesyntax.servicesimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import core.basesyntax.db.DataBase;
import core.basesyntax.model.Fruit;
import core.basesyntax.services.FruitShopSupplier;
import core.basesyntax.strategy.ElementDoesNotExist;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopSupplierImplTest {
    private static FruitShopSupplier supplier;
    private static List<String> listWithFruits;
    private List<String> listWithFruitsNull;
    private Map<String, Fruit> mapWithFruits;

    @BeforeClass
    public static void setUpBeforeClass() {
        supplier = new FruitShopSupplierImpl();
        listWithFruits = List.of("type,fruit,quantity",
                "b,banana,20", "b,apple,100", "b,kivi,100", "s,banana,100",
                "p,kivi,100", "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5",
                "s,kivi,5", "s,kivi,20", "s,banana,50");
    }

    @Before
    public void setUp() {
        mapWithFruits = new HashMap<>();
        Fruit apple;
        apple = new Fruit();
        apple.setBalance(100);
        apple.setPurchase(20);
        apple.setReturned(10);
        Fruit banana;
        banana = new Fruit();
        banana.setBalance(20);
        banana.setSupply(150);
        banana.setPurchase(18);
        Fruit kivi;
        kivi = new Fruit();
        kivi.setBalance(100);
        kivi.setSupply(25);
        kivi.setPurchase(100);
        mapWithFruits.put("apple", apple);
        mapWithFruits.put("banana", banana);
        mapWithFruits.put("kivi", kivi);
        DataBase.fruitsInShop.put("apple", new Fruit());
        DataBase.fruitsInShop.put("banana", new Fruit());
        DataBase.fruitsInShop.put("kivi", new Fruit());
        listWithFruitsNull = new ArrayList<>();
        listWithFruitsNull.add(null);
        listWithFruitsNull.add(null);
    }

    @Test
    public void fillTheMap_ok() {
        DataBase.fruitsInShop.put("apple", new Fruit());
        DataBase.fruitsInShop.put("banana", new Fruit());
        DataBase.fruitsInShop.put("kivi", new Fruit());
        supplier.fillTheMap(listWithFruits);
        assertEquals(mapWithFruits, DataBase.fruitsInShop);
    }

    @Test
    public void fillTheMap_skipFirstIndex_ok() {
        supplier.fillTheMap(listWithFruits);
        String example = "type,fruit,quantity";
        String[] array = example.split(",");
        assertFalse(DataBase.fruitsInShop.containsKey(array[1]));
    }

    @Test(expected = ElementDoesNotExist.class)
    public void fillTheMap_indexIsNull_notOk() {
        supplier.fillTheMap(listWithFruitsNull);
    }

    @After
    public void tearDown() {
        DataBase.fruitsInShop.clear();
    }
}
