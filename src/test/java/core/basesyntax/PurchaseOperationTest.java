package core.basesyntax;

import core.basesyntax.service.operationhandler.BalanceOperationHandler;
import core.basesyntax.service.operationhandler.OperationHandler;
import core.basesyntax.service.operationhandler.PurchaseOperationHandler;
import core.basesyntax.service.reportdb.ReportDataStorage;
import core.basesyntax.service.reportdb.ReportDataStoragePerMapImpl;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationTest {
    private static OperationHandler balanceOperationHandler;
    private static OperationHandler purchaseOperationHandler;
    private static ReportDataStorage reportDataStorage;

    @BeforeClass
    public static void beforeAll() {
        balanceOperationHandler = new BalanceOperationHandler();
        purchaseOperationHandler = new PurchaseOperationHandler();
        reportDataStorage = new ReportDataStoragePerMapImpl(new HashMap<String, Integer>());
        balanceOperationHandler.provideOperation(reportDataStorage, "strawberry", 10);
    }

    @Test
    public void purchaseOperationCalculation_OK() {
        Integer expected = 0;
        purchaseOperationHandler.provideOperation(reportDataStorage, "strawberry", 10);
        Integer actual = reportDataStorage.getDataPerFruit("strawberry");
        Assert.assertEquals("purchaseOperationHandler works incorrect ",
                expected, actual);
    }
}
