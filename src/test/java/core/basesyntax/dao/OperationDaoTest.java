package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Operation;
import org.junit.Before;
import org.junit.Test;

public class OperationDaoTest {
    private static Operation operation;
    private static OperationDaoImpl operationDao;
    private static final int SIZE_OPERATIONS = 4;

    @Before
    public void setUp() {
        operation = new Operation("BALANCE", "b", Operation.ArithmeticOperation.ADD);
        operationDao = new OperationDaoImpl();
    }

    @Test
    public void amountOperations_Ok() {
        assertEquals(operationDao.getListOperations().size(), SIZE_OPERATIONS);
    }

    @Test
    public void getOperation_Ok() {
        assertEquals(operation.getArithmeticOperation(), Operation.ArithmeticOperation.ADD);
    }

    @Test
    public void getShortName_Ok() {
        assertEquals(operation.getShortName(), "b");
    }
}
