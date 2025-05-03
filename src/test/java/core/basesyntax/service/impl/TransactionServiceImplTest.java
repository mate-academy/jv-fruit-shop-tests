package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;

import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionServiceImplTest {
    private TransactionService transaction;

    @Before
    public void setUp() {
        transaction = new TransactionServiceImpl();
    }

    @Test
    public void transactionService_emptyInput_notOk() {
        List<String> transactionStrings = new ArrayList<>();
        assertThrows(RuntimeException.class,
                () -> transaction.parseTransactions(transactionStrings));
    }

    @Test
    public void transactionService_invalidInput_notOk() {
        List<String> transactionStrings = new ArrayList<>();
        transactionStrings.add("b,apple");
        assertThrows(RuntimeException.class,
                () -> transaction.parseTransactions(transactionStrings));
    }

    @Test
    public void transactionService_successfulTransaction_ok() {
        List<String> transactionStrings = new ArrayList<>();
        transactionStrings.add("b,apple,3");
        transactionStrings.add("s,banana,2");
        transactionStrings.add("p,apple,5");
        transactionStrings.add("r,banana,5");
        transaction.parseTransactions(transactionStrings);
    }
}
