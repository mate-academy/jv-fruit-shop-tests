package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;

import core.basesyntax.service.Transaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionImplTest {
    private Transaction transaction;

    @Before
    public void setUp() {
        transaction = new TransactionImpl();
    }

    @Test
    public void testParseTransaction_EmptyInput_ThrowsException() {
        List<String> transactionStrings = new ArrayList<>();
        assertThrows(RuntimeException.class,
                () -> transaction.parseTransaction(transactionStrings));
    }

    @Test
    public void testParseTransaction_InvalidInputFormat_ThrowsException() {
        List<String> transactionStrings = new ArrayList<>();
        transactionStrings.add("b,apple");
        assertThrows(RuntimeException.class,
                () -> transaction.parseTransaction(transactionStrings));
    }
}
