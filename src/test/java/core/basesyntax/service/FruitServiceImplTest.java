package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static FruitService service;
    private List<String> transactions;

    @BeforeClass
    public static void beforeClass() {
        Map<Operation, OperationHandler> operations = Map.of(
                Operation.PURCHASE, new PurchaseOperationHandler(),
                Operation.BALANCE, new BalanceOperationHandler(),
                Operation.RETURN, new ReturnOperationHandler(),
                Operation.SUPPLY, new SupplyOperationHandler()
        );
        service = new FruitServiceImpl(operations);
    }

    @Before
    public void setUp() {
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
    public void addToStorage_correctData_ok() {
        service.addToStorage(transactions);
        Map<Fruit, Integer> expected = Map.of(
                new Fruit("banana"), 10,
                new Fruit("apple"), 10
        );
        assertEquals(expected, Storage.storage);
    }

    @Test(expected = RuntimeException.class)
    public void addToStorage_nullTransaction_notOk() {
        transactions = new ArrayList<>();
        transactions.add("fruit,quantity");
        transactions.add(null);
        service.addToStorage(transactions);
    }

    @Test
    public void generateReport_emptyStorage_ok() {
        assertEquals("fruit,quantity" + System.lineSeparator(), service.generateReport());
    }

    @Test
    public void generateReport_fullStorage_ok() {
        service.addToStorage(transactions);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,10"
                + System.lineSeparator()
                + "apple,10"
                + System.lineSeparator();
        assertEquals(expected, service.generateReport());
    }
}
