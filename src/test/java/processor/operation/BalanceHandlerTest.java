package processor.operation;

import static org.junit.Assert.assertEquals;

import model.OperationUnit;
import org.junit.Before;
import org.junit.Test;
import storage.DataStorage;

public class BalanceHandlerTest {
    private static final String FRUIT = "banana";
    private static final Integer OPERATION_AMOUNT = 50;
    private OperationUnit operationUnit;

    @Before
    public void clear() {
        DataStorage.FRUIT_MAP.clear();
    }

    @Test
    public void handleBalance_ok() {
        operationUnit = new OperationUnit(FRUIT, OPERATION_AMOUNT, 100);
        new BalanceHandler().handleOperation(operationUnit);
        Integer expected = 50;
        Integer actual = DataStorage.FRUIT_MAP.get(operationUnit.getFruit());
        assertEquals("Must put correct data in storage", expected, actual);
    }

    @Test
    public void handleBalance_nullStored_ok() {
        operationUnit = new OperationUnit(FRUIT, OPERATION_AMOUNT, null);
        new BalanceHandler().handleOperation(operationUnit);
        Integer expected = 50;
        Integer actual = DataStorage.FRUIT_MAP.get(operationUnit.getFruit());
        assertEquals("Must put correct data in storage", expected, actual);
    }
}
