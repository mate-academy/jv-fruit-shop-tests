package core.basesyntax.datavalidation;

import core.basesyntax.operationswithfile.Transaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class OperationTypeValidatorImplTest {
    private static final List<Transaction> transactionList = new ArrayList<>();
    private static final OperationTypeValidator operationTypeValidator
            = new OperationTypeValidatorImpl();
    private static final Transaction transaction = new Transaction();

    @Test(expected = RuntimeException.class)
    public void testValidationOperationTypeWithIllegalData_assertException() {
        transaction.setOperationType("y");
        transaction.setName("banana");
        transaction.setCount(50);
        transactionList.add(transaction);
        operationTypeValidator.validation(transactionList);
    }

    @Test
    public void testValidationOperationTypeWithLegalData_ok() {
        transaction.setOperationType("b");
        transaction.setName("banana");
        transaction.setCount(50);
        transactionList.add(transaction);
        operationTypeValidator.validation(transactionList);
    }
}
