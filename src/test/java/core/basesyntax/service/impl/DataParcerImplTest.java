package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.fruit.FruitTransaction;
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
    public void pareser_parseValidData_ok() {
        testValues.add("b,orange,23");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", "orange", 23));
        List<FruitTransaction> actual = parcer.getFruitsMoving(testValues);
        assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void pareser_parseWrongData_notOk() {
        testValues.add("w,orange,23");
        parcer.getFruitsMoving(testValues);
    }

    @After
    public void tearDown() throws Exception {
        testValues.clear();
    }
}
