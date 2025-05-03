package processor.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import exception.ReportException;
import model.OperationUnit;
import org.junit.Before;
import org.junit.Test;
import storage.DataStorage;

public class PurchaseHandlerTest {
    private static final String FRUIT = "banana";
    private static final Integer OPERATION_AMOUNT = 50;
    private OperationUnit operationUnit;

    @Before
    public void clear() {
        DataStorage.FRUIT_MAP.clear();
    }

    @Test
    public void handlePurchase_ok() {
        operationUnit = new OperationUnit(FRUIT, OPERATION_AMOUNT, 100);
        new PurchaseHandler().handleOperation(operationUnit);
        Integer expected = 50;
        Integer actual = DataStorage.FRUIT_MAP.get(operationUnit.getFruit());
        assertEquals("Must put correct data in storage", expected, actual);
    }

    @Test(expected = ReportException.class)
    public void handlePurchase_withNullStored_notOk() {
        operationUnit = new OperationUnit(FRUIT, OPERATION_AMOUNT, null);
        new PurchaseHandler().handleOperation(operationUnit);
        fail("Expected " + ReportException.class.getName()
                + " to be thrown for null storedAmount");
    }

    @Test(expected = ReportException.class)
    public void handlePurchase_withMinusResult_notOk() {
        operationUnit = new OperationUnit(FRUIT, OPERATION_AMOUNT, 0);
        new PurchaseHandler().handleOperation(operationUnit);
        fail("Expected " + ReportException.class.getName()
                + " to be thrown for insufficient amount for transaction");
    }
}
