package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.FruitShopException;
import core.basesyntax.service.DataProcessorService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataProcessorServiceImplTest {
    private static List<String[]> fruitTransactions;
    private static DataProcessorService dataProcessorService;

    @BeforeClass
    public static void beforeClass() {
        dataProcessorService = new DataProcessorServiceImpl();
        fruitTransactions = new ArrayList<>();
    }

    @Before
    public void setUp() {
        fruitTransactions.add(new String[]{"b", "apple", "20"});
        fruitTransactions.add((new String[] {"s", "apple", "30"}));
    }

    @After
    public void tearDown() {
        fruitTransactions.clear();
    }

    @Test
    public void processData_addValidData_ok() {
        dataProcessorService.processData(fruitTransactions);
        Integer actual = Storage.mapFruits.get("apple");
        Integer expected = 50;
        assertEquals(expected, actual);
    }

    @Test(expected = FruitShopException.class)
    public void processData_addInvalidOperation_notOk() {
        fruitTransactions.add(new String[]{"k", "apple", "20"});
        dataProcessorService.processData(fruitTransactions);
    }

    @Test(expected = FruitShopException.class)
    public void processorData_addNull_notOk() {
        dataProcessorService.processData(null);
    }
}
