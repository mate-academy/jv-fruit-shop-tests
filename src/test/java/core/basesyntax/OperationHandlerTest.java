package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.service.operations.ExpenseOperationHandler;
import core.basesyntax.service.operations.IncomeOperationHandler;
import core.basesyntax.service.operations.OperationHandler;
import org.junit.Assert;
import org.junit.Test;

public class OperationHandlerTest {
    private static final OperationHandler INCOME = new IncomeOperationHandler();
    private static final OperationHandler EXPENSE = new ExpenseOperationHandler();
    private static final String WATERMELON = "watermelon";
    private static final String MELON = "melon";

    @Test
    public void operate_putToStorage_validData_ok() {
        INCOME.operate(WATERMELON, 10);
        int expectedWeight = 10;
        int actualWeight = Storage.fruits.get(WATERMELON);
        Assert.assertEquals(expectedWeight, actualWeight);
    }

    @Test(expected = RuntimeException.class)
    public void operate_putToStorage_nullValue_notOk() {
        INCOME.operate(null, 10);
    }

    @Test
    public void operate_getPresentDataFromStorage_ok() {
        Storage.fruits.put(MELON, 20);
        EXPENSE.operate(MELON, 10);
        int expectedWeight = 10;
        int actualWeight = Storage.fruits.get(MELON);
        Assert.assertEquals(expectedWeight, actualWeight);
    }

    @Test(expected = RuntimeException.class)
    public void operate_getAbsentDataFromStorage_notOk() {
        Assert.assertFalse(Storage.fruits.containsKey("orange"));
        EXPENSE.operate("orange", 20);
    }

    @Test(expected = RuntimeException.class)
    public void operate_getDataFromStorageMoreThenAvailable_notOk() {
        int actualQuantity = Storage.fruits.get(MELON);
        EXPENSE.operate(MELON, actualQuantity + 10);
    }
}
