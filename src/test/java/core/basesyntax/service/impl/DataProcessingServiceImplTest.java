package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.service.DataProcessingService;
import core.basesyntax.strategy.impl.BalanceStrategy;
import core.basesyntax.strategy.impl.PurchaseStrategy;
import core.basesyntax.strategy.impl.ReturnStrategy;
import core.basesyntax.strategy.impl.SupplyStrategy;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataProcessingServiceImplTest {
    private static DataProcessingService dataProcessingService;
    private static List<Transaction> transactions;

    @BeforeClass
    public static void beforeClass() {
        dataProcessingService = new DataProcessingServiceImpl();
    }

    @Before
    public void setUp() {
        transactions = new ArrayList<>();
    }

    @Test
    public void processData_balanceStrategy_ok() {
        Integer expected = 20;
        transactions.add(new Transaction(new BalanceStrategy(), "fruit", expected));
        dataProcessingService.processData(transactions);
        Integer actual = Storage.fruits.get("fruit");
        assertEquals("Balance must be " + expected + " but is " + actual, expected, actual);
    }

    @Test
    public void processData_purchaseStrategy_ok() {
        Storage.fruits.put("fruit", 100);
        Integer purchased = 20;
        Integer expected = 80;
        transactions.add(new Transaction(new PurchaseStrategy(), "fruit", purchased));
        dataProcessingService.processData(transactions);
        Integer actual = Storage.fruits.get("fruit");
        assertEquals("Balance must be 100-" + purchased + "=" + expected
                + " but is " + actual, expected, actual);
    }

    @Test
    public void processData_returnStrategy_ok() {
        Storage.fruits.put("fruit", 80);
        Integer returned = 20;
        Integer expected = 100;
        transactions.add(new Transaction(new ReturnStrategy(), "fruit", returned));
        dataProcessingService.processData(transactions);
        Integer actual = Storage.fruits.get("fruit");
        assertEquals("Balance must be 80+" + returned + "=" + expected
                + " but is " + actual, expected, actual);
    }

    @Test
    public void processData_supplyStrategy_ok() {
        Storage.fruits.put("fruit", 80);
        Integer deliveredBySupplier = 20;
        Integer expected = 100;
        transactions.add(new Transaction(new SupplyStrategy(), "fruit", deliveredBySupplier));
        dataProcessingService.processData(transactions);
        Integer actual = Storage.fruits.get("fruit");
        assertEquals("Balance must be 80+" + deliveredBySupplier + "=" + expected
                + " but is " + actual, expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}
