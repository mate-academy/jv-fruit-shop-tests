package core.basesyntax.java.core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.java.core.basesyntax.model.FruitTransaction;
import core.basesyntax.java.core.basesyntax.service.impl.DataValidatorImpl;
import core.basesyntax.java.core.basesyntax.service.impl.SplitServiceImpl;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SplitServiceTest {
    private static final String BALANCE = "b";
    private static SplitService splitService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        splitService = new SplitServiceImpl(new DataValidatorImpl());
    }

    @Test
    public void getTransactionFromRow_ok() {
        FruitTransaction expected = new FruitTransaction();
        expected.setOperation(BALANCE);
        expected.setFruit("apple");
        expected.setQuantity(50);
        String transaction = "b,apple,50";
        FruitTransaction actual = splitService.getTransactionFromRow(transaction);
        assertEquals(expected, actual);
    }

    @Test
    public void getTransactionFromRow_badDataInQuantity_notOk() {
        exception.expect(RuntimeException.class);
        splitService.getTransactionFromRow("b,banan,n");
    }

    @Test
    public void getTransactionFromRow_badDataInOperation_notOk() {
        exception.expect(RuntimeException.class);
        splitService.getTransactionFromRow("k,banan,10");
    }
}
