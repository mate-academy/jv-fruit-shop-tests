package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class FruitServiceTestBase<T extends FruitService> {
    private T instance;

    protected abstract T createInstance();

    List<String> transactions;

    @Before
    public void setUp() {
        instance = createInstance();
        transactions = List.of(
                "fruit,quantity",
                "b,banana,10",
                "b,apple,10",
                "s,banana,1",
                "s,apple,1",
                "p,banana,2",
                "p,apple,2",
                "r,banana,1",
                "r,apple,1"
        );
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void add_Correct_Data_To_Storage_ok() {
        instance.addToStorage(transactions);
        Map<Fruit, Integer> expected = Map.of(
                new Fruit("banana"), 10,
                new Fruit("apple"), 10
        );
        assertEquals(expected, Storage.storage);
    }

    @Test(expected = RuntimeException.class)
    public void add_Null_To_Storage_notOk() {
        transactions = new ArrayList<>();
        transactions.add("fruit,quantity");
        transactions.add(null);
        instance.addToStorage(transactions);
    }

    @Test
    public void generate_Report_Empty_Storage_ok() {
        assertEquals("fruit,quantity" + System.lineSeparator(), instance.generateReport());
    }

    @Test
    public void generate_Report_Full_Storage_ok() {
        instance.addToStorage(transactions);
        String expected = "fruit,quantity" +
                System.lineSeparator() +
                "banana,10" +
                System.lineSeparator() +
                "apple,10" +
                System.lineSeparator();
        assertEquals(expected, instance.generateReport());
    }
}
