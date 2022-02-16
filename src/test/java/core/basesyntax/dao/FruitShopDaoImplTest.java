package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FruitShopDaoImplTest {
    private static final Map<Fruit, Integer> TEST_MAP = new HashMap<>();
    private static final FruitShopDao DAO = new FruitShopDaoImpl();
    private static final int APPLE_QTY = 20;
    private static final int MANGO_QTY = 30;
    private static final int BANANA_QTY = 40;
    private static final int GRAPEFRUIT_QTY = 50;
    private static final int ORANGE_QTY = 60;
    private static final int PEAR_QTY = 70;
    private static final int PLUM_QTY = 80;
    private static final Fruit FRUIT_APPLE = new Fruit(Fruit.FruitType.APPLE);
    private static final Fruit FRUIT_MANGO = new Fruit(Fruit.FruitType.MANGO);
    private static final Fruit FRUIT_BANANA = new Fruit(Fruit.FruitType.BANANA);
    private static final Fruit FRUIT_GRAPEFRUIT = new Fruit(Fruit.FruitType.GRAPEFRUIT);
    private static final Fruit FRUIT_ORANGE = new Fruit(Fruit.FruitType.ORANGE);
    private static final Fruit FRUIT_PEAR = new Fruit(Fruit.FruitType.PEAR);
    private static final Fruit FRUIT_PLUM = new Fruit(Fruit.FruitType.PLUM);
    private static final int EXPECTED_MAP_SIZE = 7;

    @Before
    public void setUp() {
        Storage.storage.clear();
        TEST_MAP.put(FRUIT_APPLE, APPLE_QTY);
        TEST_MAP.put(FRUIT_MANGO, MANGO_QTY);
        TEST_MAP.put(FRUIT_BANANA, BANANA_QTY);
        TEST_MAP.put(FRUIT_GRAPEFRUIT, GRAPEFRUIT_QTY);
        TEST_MAP.put(FRUIT_ORANGE, ORANGE_QTY);
        TEST_MAP.put(FRUIT_PEAR, PEAR_QTY);
        TEST_MAP.put(FRUIT_PLUM, PLUM_QTY);
    }

    @Test
    public void daoMethods_Ok() {
        DAO.setRecords(TEST_MAP);
        assertEquals(EXPECTED_MAP_SIZE, DAO.getDbSize());
        Map<Fruit, Integer> assertionMap = new HashMap<>(DAO.getRecords());
        assertEquals(APPLE_QTY, assertionMap.get(FRUIT_APPLE).intValue());
        assertEquals(MANGO_QTY, assertionMap.get(FRUIT_MANGO).intValue());
        assertEquals(BANANA_QTY, assertionMap.get(FRUIT_BANANA).intValue());
        assertEquals(GRAPEFRUIT_QTY, assertionMap.get(FRUIT_GRAPEFRUIT).intValue());
        assertEquals(ORANGE_QTY, assertionMap.get(FRUIT_ORANGE).intValue());
        assertEquals(PEAR_QTY, assertionMap.get(FRUIT_PEAR).intValue());
        assertEquals(PLUM_QTY, assertionMap.get(FRUIT_PLUM).intValue());
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}