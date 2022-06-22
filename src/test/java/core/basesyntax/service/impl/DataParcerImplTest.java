package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.fruit.Fruit;
import core.basesyntax.service.DataParcer;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParcerImplTest {
    private static List<String> testValues;
    private static DataParcer parcer;

    @BeforeClass
    public static void beforeClass() {
        testValues = new ArrayList<>();
        parcer = new DataParcerImpl();
    }

    @Before
    public void setUp() {
        testValues.add("type,fruit,quantity");
    }

    @Test
    public void parcingData_Ok() {
        testValues.add("b,orange,23");
        List<Fruit> expected = new ArrayList<>();
        expected.add(new Fruit("b", "orange", 23));
        List<Fruit> actual = parcer.getFruitsMoving(testValues);
        assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void parseWrongData_NotOk() {
        testValues.add("w,orange,23");
        parcer.getFruitsMoving(testValues);
    }

    @After
    public void tearDown() throws Exception {
        testValues.clear();
        Storage.data.clear();
    }

}
