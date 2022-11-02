package com.fruitshop.servicesimpl;

import com.fruitshop.db.DataBase;
import com.fruitshop.model.Fruit;
import com.fruitshop.services.FruitShopSupplier;
import com.fruitshop.strategy.ElementDoesNotExist;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopSupplierImplTest {

    private final FruitShopSupplier supplier = new FruitShopSupplierImpl();
    private final List<String> listWithFruits = List.of("type,fruit,quantity",
            "b,banana,20", "b,apple,100", "b,kivi,100", "s,banana,100",
            "p,kivi,100", "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5",
            "s,kivi,5", "s,kivi,20", "s,banana,50");
    private List<String> listWithFruitsNull;
    private Map<String, Fruit> rightMap;
    private Fruit apple = new Fruit();
    private Fruit kivi = new Fruit();
    private Fruit banana = new Fruit();

    @BeforeEach
    void setUp() {
        rightMap = new HashMap<>();
        apple = new Fruit();
        apple.setBalance(100);
        apple.setPurchase(20);
        apple.setReturned(10);
        banana = new Fruit();
        banana.setBalance(20);
        banana.setSupply(150);
        banana.setPurchase(18);
        kivi = new Fruit();
        kivi.setBalance(100);
        kivi.setSupply(25);
        kivi.setPurchase(100);
        rightMap.put("apple", apple);
        rightMap.put("banana", banana);
        rightMap.put("kivi", kivi);
        DataBase.fruitsInShop.put("apple", new Fruit());
        DataBase.fruitsInShop.put("banana", new Fruit());
        DataBase.fruitsInShop.put("kivi", new Fruit());
        listWithFruitsNull = new ArrayList<>();
        listWithFruitsNull.add(null);
        listWithFruitsNull.add(null);
    }

    @AfterEach
    void tearDown() {
        DataBase.fruitsInShop.clear();
    }

    @Test
    void fillTheMap_Ok() {
        DataBase.fruitsInShop.put("apple", new Fruit());
        DataBase.fruitsInShop.put("banana", new Fruit());
        DataBase.fruitsInShop.put("kivi", new Fruit());
        supplier.fillTheMap(listWithFruits);
        Assertions.assertEquals(rightMap, DataBase.fruitsInShop);
    }

    @Test
    void fillTheMap_SkipFIrstIndex() {
        supplier.fillTheMap(listWithFruits);
        String example = "type,fruit,quantity";
        String[] array = example.split(",");
        Assertions.assertFalse(DataBase.fruitsInShop.containsKey(array[1]));
    }

    @Test
    void fillTheMap_indexIsNull() {
        Assertions.assertThrows(ElementDoesNotExist.class,
                () -> supplier.fillTheMap(listWithFruitsNull));
    }
}
