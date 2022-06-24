package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataService;
import core.basesyntax.service.impl.DataServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataServiceImplTest {
    private static DataService dataService;
    private static List<String> activities;

    @BeforeClass
    public static void beforeClass() {
        dataService = new DataServiceImpl();
        activities = new ArrayList<>();
    }

    @Test
    public void processData_goodData_ok() {
        activities.add("type,fruit,quantity");
        activities.add("b,banana,20");
        activities.add("s,banana,100");
        activities.add("r,apple,10");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10));
        List<FruitTransaction> actual = dataService.processData(activities);
        assertEquals(expected, actual);
    }

    @Test
    public void processData_emptyList_Ok() {
        activities.add("type,fruit,quantity");
        List<FruitTransaction> expected = new ArrayList<>();
        List<FruitTransaction> actual = dataService.processData(activities);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void processData_notExistingOperation_notOk() {
        activities.add("type,fruit,quantity");
        activities.add("k, kiwi, 36");
        dataService.processData(activities);
    }

    @After
    public void tearDown() {
        activities.clear();
    }
}
