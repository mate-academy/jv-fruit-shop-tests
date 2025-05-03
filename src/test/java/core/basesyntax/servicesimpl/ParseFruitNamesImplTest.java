package core.basesyntax.servicesimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.db.DataBase;
import core.basesyntax.model.Fruit;
import core.basesyntax.services.ParseFruitNames;
import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ParseFruitNamesImplTest {
    private final ParseFruitNames parser = new ParseFruitNamesImpl();
    private final List<String> listWithFruits = List.of("type,fruit,quantity", "b,banana,20",
            "b,apple,100", "b,kivi,100", "s,banana,100",
            "p,kivi,100", "p,banana,13", "r,apple,10", "p,apple,20",
            "p,banana,5", "s,kivi,5", "s,kivi,20", "s,banana,50");
    private HashMap<String, Fruit> rightMap;
    private HashMap<String, Fruit> wrongMap;

    @Before
    public void setUp() {
        rightMap = new HashMap<>();
        rightMap.put("banana", new Fruit());
        rightMap.put("apple", new Fruit());
        rightMap.put("kivi", new Fruit());
        wrongMap = new HashMap<>();
        wrongMap.put("ban", new Fruit());
        wrongMap.put("ale", new Fruit());
        wrongMap.put("kivi", new Fruit());
    }

    @Test
    public void getFruitNamesMap_ok() {
        parser.getFruitNamesMap(listWithFruits);
        assertEquals(rightMap, DataBase.fruitsInShop);
    }

    @Test
    public void getFruitNamesMap_notEquals_ok() {
        parser.getFruitNamesMap(listWithFruits);
        assertNotEquals(wrongMap, DataBase.fruitsInShop);
    }

    @After
    public void tearDown() {
        DataBase.fruitsInShop.clear();
    }
}
