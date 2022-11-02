package com.fruitshop.servicesimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.fruitshop.db.DataBase;
import com.fruitshop.model.Fruit;
import com.fruitshop.services.ParseFruitNames;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParseFruitNamesImplTest {

    private final ParseFruitNames parser = new ParseFruitNamesImpl();

    private final List<String> listWithFruits = List.of("type,fruit,quantity", "b,banana,20",
            "b,apple,100", "b,kivi,100", "s,banana,100",
            "p,kivi,100", "p,banana,13", "r,apple,10", "p,apple,20",
            "p,banana,5", "s,kivi,5", "s,kivi,20", "s,banana,50");
    private HashMap<String, Fruit> rightMap;
    private HashMap<String, Fruit> wrongMap;

    @BeforeEach
    void setUp() {
        rightMap = new HashMap<>();
        rightMap.put("banana", new Fruit());
        rightMap.put("apple", new Fruit());
        rightMap.put("kivi", new Fruit());
        wrongMap = new HashMap<>();
        wrongMap.put("ban", new Fruit());
        wrongMap.put("ale", new Fruit());
        wrongMap.put("kivi", new Fruit());
    }

    @AfterEach
    void tearDown() {
        DataBase.fruitsInShop.clear();
    }

    @Test
    void getFruitNamesMap_Ok() {
        parser.getFruitNamesMap(listWithFruits);
        assertEquals(rightMap, DataBase.fruitsInShop);
    }

    @Test
    void getFruitNamesMap_NotEquals() {
        parser.getFruitNamesMap(listWithFruits);
        assertNotEquals(wrongMap, DataBase.fruitsInShop);
    }
}
