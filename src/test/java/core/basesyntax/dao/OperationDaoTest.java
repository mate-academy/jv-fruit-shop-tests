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
        assertEquals(SIZE_OPERATIONS, operationDao.getListOperations().size());
    }

    @Test
    public void getOperation_Ok() {
        assertEquals(Operation.ArithmeticOperation.ADD, operation.getArithmeticOperation());
    }

    @Test
    public void getShortName_Ok() {
        assertEquals("b", operation.getShortName());
    }
}
