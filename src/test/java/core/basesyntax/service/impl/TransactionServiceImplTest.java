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
    public void testParseTransaction_EmptyInput_ThrowsException() {
        List<String> transactionStrings = new ArrayList<>();
        assertThrows(RuntimeException.class,
                () -> transaction.parseTransactions(transactionStrings));
    }

    @Test
    public void testParseTransaction_InvalidInputFormat_ThrowsException() {
        List<String> transactionStrings = new ArrayList<>();
        transactionStrings.add("b,apple");
        assertThrows(RuntimeException.class,
                () -> transaction.parseTransactions(transactionStrings));
    }
}
