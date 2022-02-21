package core.basesyntax.service.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FillStorage;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FillStorageImplTest {
    private static final int DEFAULT_QUANTITY_FOR_ONE_FRUIT = 0;
    private static final String CORRECT_DATA_FOR_BANANA = "b,banana,20";
    private static final String CORRECT_DATA_FOR_APPLE = "b,apple,30";
    private static final String INCORRECT_DATA_FOR_BANANA = "s,   banana1,20";
    private static FillStorage fillStorage;
    private static List<String> inputData;
    private static List<Fruit> expected;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fillStorage = new FillStorageImpl();
        inputData = new ArrayList<>();
        inputData.add(CORRECT_DATA_FOR_BANANA);
        inputData.add(CORRECT_DATA_FOR_APPLE);
        expected = new ArrayList<>();
        expected.add(new Fruit("banana", DEFAULT_QUANTITY_FOR_ONE_FRUIT));
        expected.add(new Fruit("apple", DEFAULT_QUANTITY_FOR_ONE_FRUIT));
    }

    @Test
    public void fill_ok() {
        fillStorage.fill(inputData);
        assertEquals(expected, FruitsStorage.getFruits());
    }

    @Test
    public void fillIncorrectData_ok() {
        inputData.add(INCORRECT_DATA_FOR_BANANA);
        fillStorage.fill(inputData);
        assertEquals(expected, FruitsStorage.getFruits());
    }

    @Test
    public void fillForInputDataNull_notOk() {
        assertThrows(RuntimeException.class, () -> fillStorage.fill(null));
    }

    @After
    public void tearDown() {
        FruitsStorage.getFruits().clear();
    }
}
