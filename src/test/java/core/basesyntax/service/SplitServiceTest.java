package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataValidatorImpl;
import core.basesyntax.service.impl.SplitServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class SplitServiceTest {
    private static final FruitTransaction.Operation BALANCE = FruitTransaction.Operation.BALANCE;
    private static SplitService splitService;

    @BeforeClass
    public static void beforeClass() {
        splitService = new SplitServiceImpl(new DataValidatorImpl());
    }

    @Test
    public void getTransactionFromRow_validData_ok() {
        FruitTransaction expected = new FruitTransaction(BALANCE, "apple", 50);
        FruitTransaction actual = splitService.getTransactionFromRow("b,apple,50");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getTransactionFromRow_invalidDataInQuantity_notOk() {
        splitService.getTransactionFromRow("b,banana,n");
    }

    @Test(expected = RuntimeException.class)
    public void getTransactionFromRow_invalidDataInOperation_notOk() {
        splitService.getTransactionFromRow("b,banana,n");
    }

    @Test(expected = RuntimeException.class)
    public void getTransactionFromRow_invalidTransaction_notOk() {
        splitService.getTransactionFromRow("b,apple,50,b,apple,50");
    }
}
