package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.exception.FruitsHolderException;
import core.basesyntax.service.FruitsHolderService;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class FruitsHolderServiceImplTest {
    private static final int AMOUNT_SAMPLE = 2020;
    private static final String NAME_SAMPLE = "banana";
    private static final String NON_EXISTING_KEY = "pineapple";
    private static final int VALUE_FOR_NON_EXISTED_KEY = 0;
    private FruitsHolderService fruitsHolderService;

    @Before
    public void setUp() {
        fruitsHolderService = new FruitsHolderServiceImpl();
    }

    @Test
    public void putFruit_argumentsAreNull_notOk() {
        try {
            fruitsHolderService.putFruit(null, null);
        } catch (FruitsHolderException e) {
            return;
        }
        fail("putFruit should throw an error if one of the arguments is null");
    }

    @Test
    public void putFruit_firstArgumentIsNull_notOk() {
        try {
            fruitsHolderService.putFruit(null, AMOUNT_SAMPLE);
        } catch (FruitsHolderException e) {
            return;
        }
        fail("putFruit should throw an error if one of the arguments is null");
    }

    @Test
    public void putFruit_secondArgumentIsNull_notOk() {
        try {
            fruitsHolderService.putFruit(NAME_SAMPLE, null);
        } catch (FruitsHolderException e) {
            return;
        }
        fail("putFruit should throw an error if one of the arguments is null");
    }

    @Test
    public void putFruit_defaultData_ok() {
        for (int i = 0; i < 100; i++) {
            fruitsHolderService.putFruit(i + " item", i);
        }
        for (int i = 0; i < 100; i++) {
            assertEquals("putFruit should not modify the data it stores",
                    fruitsHolderService.getFruitAmount(i + " item"), i);
        }
    }

    @Test
    public void getFruitAmount_nullArgument_notOk() {
        try {
            fruitsHolderService.getFruitAmount(null);
        } catch (FruitsHolderException e) {
            return;
        }
        fail("getFruitAmount should throw an error if it argument equals null");
    }

    @Test
    public void getFruitAmount_defaultValue_ok() {
        assertEquals("getFruitAmount should return 0 if"
                + " accessed by non-existent key", VALUE_FOR_NON_EXISTED_KEY,
                fruitsHolderService.getFruitAmount(NON_EXISTING_KEY));
    }

    @Test
    public void getAllFruits_getEmptyMap_ok() {
        assertEquals("getFruitAmount should return an"
                + "empty map if there is nothing in it",
                new HashMap<>(), fruitsHolderService.getAllFruits());
    }

    @Test
    public void getAllFruits_getMapWithElements_ok() {
        Map<String, Integer> sampleData = new HashMap<>();
        sampleData.put("banana", 100);
        sampleData.put("apple", 120);
        sampleData.put("pineapple", 60);
        sampleData.put("orange", 190);
        for (Map.Entry<String, Integer> e: sampleData.entrySet()) {
            fruitsHolderService.putFruit(e.getKey(), e.getValue());
        }
        assertEquals("getFruitAmount should return a map",
                sampleData, fruitsHolderService.getAllFruits());
    }
}
