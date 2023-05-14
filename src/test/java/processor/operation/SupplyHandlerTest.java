package processor.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import exception.ReportException;
import model.OperationUnit;
import org.junit.Before;
import org.junit.Test;
import storage.DataStorage;

public class SupplyHandlerTest {
    private static final String FRUIT = "banana";
    private static final Integer OPERATION_AMOUNT = 50;
    private OperationUnit operationUnit;

    @Before
    public void clear() {
        DataStorage.FRUIT_MAP.clear();
    }

    @Test
    public void handleSupply_ok() {
        operationUnit = new OperationUnit(FRUIT, OPERATION_AMOUNT, 100);
        new SupplyHandler().handleOperation(operationUnit);
        Integer expected = 150;
        Integer actual = DataStorage.FRUIT_MAP.get(operationUnit.getFruit());
        assertEquals("Must put correct data in storage", expected, actual);
    }

    @Test(expected = ReportException.class)
    public void handleSupply_withNullStored_notOk() {
        operationUnit = new OperationUnit(FRUIT, OPERATION_AMOUNT, null);
        new SupplyHandler().handleOperation(operationUnit);
        fail("Expected " + ReportException.class.getName()
                + " to be thrown for null storedAmount");
    }
}
