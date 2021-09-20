package core.basesyntax;

import core.basesyntax.service.operationhandler.BalanceOperationHandler;
import core.basesyntax.service.operationhandler.OperationHandler;
import core.basesyntax.service.operationhandler.SupplyOperationHandler;
import core.basesyntax.service.reportdb.ReportDataStorage;
import core.basesyntax.service.reportdb.ReportDataStoragePerMapImpl;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationTest {
    private static OperationHandler balanceOperationHandler;
    private static OperationHandler supplyOperationHandler;
    private static ReportDataStorage reportDataStorage;

    @BeforeClass
    public static void beforeAll() {
        supplyOperationHandler = new SupplyOperationHandler();
        balanceOperationHandler = new BalanceOperationHandler();
        reportDataStorage = new ReportDataStoragePerMapImpl(new HashMap<String, Integer>());
        balanceOperationHandler.provideOperation(reportDataStorage, "apple", 0);
    }

    @Test
    public void supplyOperationCalculation_OK() {
        Integer expected = 10;
        supplyOperationHandler.provideOperation(reportDataStorage, "apple", 10);
        Integer actual = reportDataStorage.getDataPerFruit("apple");
        Assert.assertEquals("supplyOperationHandler works incorrect ",
                expected, actual);
    }
}
