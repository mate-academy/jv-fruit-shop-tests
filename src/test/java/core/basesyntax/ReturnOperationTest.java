package core.basesyntax;

import core.basesyntax.service.operationhandler.BalanceOperationHandler;
import core.basesyntax.service.operationhandler.OperationHandler;
import core.basesyntax.service.operationhandler.ReturnOperationHandler;
import core.basesyntax.service.reportdb.ReportDataStorage;
import core.basesyntax.service.reportdb.ReportDataStoragePerMapImpl;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationTest {
    private static OperationHandler balanceOperationHandler;
    private static OperationHandler returnOperationHandler;
    private static ReportDataStorage reportDataStorage;

    @BeforeClass
    public static void beforeAll() {
        returnOperationHandler = new ReturnOperationHandler();
        balanceOperationHandler = new BalanceOperationHandler();
        reportDataStorage = new ReportDataStoragePerMapImpl(new HashMap<String, Integer>());
        balanceOperationHandler.provideOperation(reportDataStorage, "orange", 0);
    }

    @Test
    public void returnOperationCalculation_OK() {
        Integer expected = 10;
        returnOperationHandler.provideOperation(reportDataStorage, "orange", 10);
        Integer actual = reportDataStorage.getDataPerFruit("orange");
        Assert.assertEquals("returnOperationHandler works incorrect ",
                expected, actual);
    }
}
