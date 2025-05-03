package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.DataFromOrderProcessor;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataFromOrderProcessorImplTest {
    private static final String FIRST_LINE = "b,apple,15";
    private static final String SECOND_LINE = "b,kiwi,2";
    private static final String FIRST_SYMBOL = "b";
    private static final String FRUIT = "apple";
    private static final String AMOUNT = "15";
    private static final int INDEX_OF_TYPE = 0;
    private static final int INDEX_OF_FRUIT = 1;
    private static final int INDEX_OF_AMOUNT = 2;
    private static List<String> actual;
    private static DataFromOrderProcessor dataFromOrderProcessor;

    @BeforeClass
    public static void beforeClass() {
        actual = new ArrayList<>();
        dataFromOrderProcessor = new DataFromOrderProcessorImpl();
    }

    @Before
    public void setUpBeforeTest() {
        actual.add(FIRST_LINE);
        actual.add(SECOND_LINE);
    }

    @After
    public void tearDownAfterTest() {
        actual.clear();
    }

    @Test
    public void split_Ok() {
        List<String[]> actualList = dataFromOrderProcessor.split(actual);
        String actualType = actualList.get(0)[INDEX_OF_TYPE];
        String actualFruit = actualList.get(0)[INDEX_OF_FRUIT];
        String actualAmount = actualList.get(0)[INDEX_OF_AMOUNT];
        assertEquals(actualType, FIRST_SYMBOL);
        assertEquals(actualFruit, FRUIT);
        assertEquals(actualAmount, AMOUNT);
    }

    @Test(expected = RuntimeException.class)
    public void split_with_null_string_notOk() {
        dataFromOrderProcessor.split(null);
        fail("Should throw an exception when the input parameter is null");
    }

    @Test(expected = RuntimeException.class)
    public void split_with_empty_string_notOk() {
        dataFromOrderProcessor.split(new ArrayList<>());
        fail("Should throw an exception when the input parameter is empty List");
    }
}
