package core.basesyntax;

import core.basesyntax.service.operationhandler.BalanceOperationHandler;
import core.basesyntax.service.operationhandler.OperationHandler;
import core.basesyntax.service.operationhandler.PurchaseOperationHandler;
import core.basesyntax.service.operationhandler.ReturnOperationHandler;
import core.basesyntax.service.operationhandler.SupplyOperationHandler;
import core.basesyntax.service.reportdb.ReportDataStorage;
import core.basesyntax.service.reportdb.ReportDataStoragePerMapImpl;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerTest {
    private static OperationHandler operationHandler;
    private static OperationHandler balanceOperationHandler;
    private static OperationHandler purchaseOperationHandler;
    private static OperationHandler supplyOperationHandler;
    private static OperationHandler returnOperationHandler;
    private static ReportDataStorage reportDataStorage;

    @BeforeClass
    public static void beforeAll() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        returnOperationHandler = new ReturnOperationHandler();
        supplyOperationHandler = new SupplyOperationHandler();
        balanceOperationHandler = new BalanceOperationHandler();
        reportDataStorage = new ReportDataStoragePerMapImpl(new HashMap<String, Integer>());
        balanceOperationHandler.provideOperation(reportDataStorage, "apple", 0);
        balanceOperationHandler.provideOperation(reportDataStorage, "orange", 0);
        balanceOperationHandler.provideOperation(reportDataStorage, "strawberry", 10);

    }

    @Test
    public void balanceOperationCalculation_OK() {
        Integer expected = 10;
        balanceOperationHandler.provideOperation(reportDataStorage, "banana", 10);
        Integer actual = reportDataStorage.getDataPerFruit("banana");
        Assert.assertEquals("balanceOperationHandler works incorrect ",
                expected, actual);
    }

    @Test
    public void supplyOperationCalculation_OK() {
        Integer expected = 10;
        supplyOperationHandler.provideOperation(reportDataStorage, "apple", 10);
        Integer actual = reportDataStorage.getDataPerFruit("apple");
        Assert.assertEquals("supplyOperationHandler works incorrect ",
                expected, actual);
    }

    @Test
    public void returnOperationCalculation_OK() {
        Integer expected = 10;
        returnOperationHandler.provideOperation(reportDataStorage, "orange", 10);
        Integer actual = reportDataStorage.getDataPerFruit("orange");
        Assert.assertEquals("returnOperationHandler works incorrect ",
                expected, actual);
    }

    @Test
    public void purchaseOperationCalculation_OK() {
        Integer expected = 0;
        purchaseOperationHandler.provideOperation(reportDataStorage, "strawberry", 10);
        Integer actual = reportDataStorage.getDataPerFruit("orange");
        Assert.assertEquals("purchaseOperationHandler works incorrect ",
                expected, actual);
    }
}
