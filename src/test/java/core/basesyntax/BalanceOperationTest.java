package core.basesyntax;

import core.basesyntax.service.operationhandler.BalanceOperationHandler;
import core.basesyntax.service.operationhandler.OperationHandler;
import core.basesyntax.service.reportdb.ReportDataStorage;
import core.basesyntax.service.reportdb.ReportDataStoragePerMapImpl;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationTest {
    private static OperationHandler balanceOperationHandler;
    private static ReportDataStorage reportDataStorage;

    @BeforeClass
    public static void beforeAll() {
        balanceOperationHandler = new BalanceOperationHandler();
        reportDataStorage = new ReportDataStoragePerMapImpl(new HashMap<String, Integer>());
    }

    @Test
    public void balanceOperationCalculation_OK() {
        Integer expected = 10;
        balanceOperationHandler.provideOperation(reportDataStorage, "banana", 10);
        Integer actual = reportDataStorage.getDataPerFruit("banana");
        Assert.assertEquals("balanceOperationHandler works incorrect ",
                expected, actual);
    }
}
