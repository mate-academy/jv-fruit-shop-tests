package core.basesyntax;

import core.basesyntax.service.Operation;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.operations.ExpenseOperationHandler;
import core.basesyntax.service.operations.IncomeOperationHandler;
import core.basesyntax.service.operations.OperationHandler;
import org.junit.Assert;
import org.junit.Test;

public class OperationStrategyTest {
    private static final Operation[] EXPECTED_VALUES = Operation.values();
    private static final String[] OPERATION_ABBREVIATION = new String[]{"b", "s", "p", "r"};
    private static final OperationHandler income = new IncomeOperationHandler();
    private static final OperationHandler expense = new ExpenseOperationHandler();

    @Test
    public void getOperationValue_validData_ok() {
        for (int i = 0; i < OPERATION_ABBREVIATION.length; i++) {
            Operation actual = Operation.getOperationValue(OPERATION_ABBREVIATION[i]);
            Assert.assertEquals(EXPECTED_VALUES[i].toString(), actual.toString());
        }
    }

    @Test(expected = RuntimeException.class)
    public void getOperationValue_invalidData_notOk() {
        String abbreviation = "c";
        Operation.getOperationValue(abbreviation);
    }

    @Test
    public void getOperationType_incomeData_Ok() {
        Operation balance = Operation.BALANCE;
        Operation ret = Operation.RETURN;
        Operation supply = Operation.SUPPLY;
        Assert.assertEquals(income.getClass(), OperationStrategy
                .getOperationType(balance).getClass());
        Assert.assertEquals(income.getClass(), OperationStrategy
                .getOperationType(ret).getClass());
        Assert.assertEquals(income.getClass(), OperationStrategy
                .getOperationType(supply).getClass());
    }

    @Test
    public void getOperationType_expenseData_Ok() {
        Operation purchase = Operation.PURCHASE;
        Assert.assertEquals(expense.getClass(), OperationStrategy
                .getOperationType(purchase).getClass());
    }
}
