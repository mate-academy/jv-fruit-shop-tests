package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataValidatorImpl;
import core.basesyntax.service.impl.SplitServiceImpl;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SplitServiceTest {
    private static final FruitTransaction.Operation BALANCE = FruitTransaction.Operation.BALANCE;
    private static SplitService splitService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        splitService = new SplitServiceImpl(new DataValidatorImpl());
    }

    @Test
    public void getTransactionFromRow_ok() {
        FruitTransaction expected = new FruitTransaction(BALANCE, "apple", 50);
        FruitTransaction actual = splitService.getTransactionFromRow("b,apple,50");
        assertEquals(expected, actual);
    }

    @Test
    public void getTransactionFromRow_badDataInQuantity_notOk() {
        exception.expect(RuntimeException.class);
        splitService.getTransactionFromRow("b,banana,n");
    }

    @Test
    public void getTransactionFromRow_badDataInOperation_notOk() {
        exception.expect(RuntimeException.class);
        splitService.getTransactionFromRow("b,banana,n");
    }

    @Test
    public void getTransactionFromRow_badTransaction_notOk() {
        exception.expect(RuntimeException.class);
        splitService.getTransactionFromRow("b,apple,50,b,apple,50");
    }
}
