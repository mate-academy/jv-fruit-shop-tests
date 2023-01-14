package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.FruitShopException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataProcessorServiceImplTest {
    private static List<String[]> list;
    private DataProcessorServiceImpl dataProcessorService = new DataProcessorServiceImpl();

    @BeforeClass
    public static void beforeClass() {
        list = new ArrayList<>();
    }

    @Before
    public void setUp() {
        list.add(new String[]{"b", "apple", "20"});
        list.add((new String[] {"s", "apple", "30"}));
    }

    @After
    public void tearDown() {
        list.clear();
    }

    @Test
    public void processData_addValidData_Ok() {
        dataProcessorService.processData(list);
        Integer actual = Storage.mapFruits.get("apple");
        Integer expected = 50;
        assertEquals(expected, actual);
    }

    @Test(expected = FruitShopException.class)
    public void processData_addInvalidOperation_notOk() {
        list.add(new String[]{"k", "apple", "20"});
        dataProcessorService.processData(list);
    }

    @Test(expected = FruitShopException.class)
    public void processorData_addNull_notOk() {
        dataProcessorService.processData(null);
    }
}
