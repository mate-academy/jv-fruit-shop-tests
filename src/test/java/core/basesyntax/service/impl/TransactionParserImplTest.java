package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Transaction;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private TransactionParserImpl transactionParser;

    @BeforeEach
    void setUp() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    void parse_ValidLines_ok() {
        List<String> lines = List.of("b,apple,50", "p,banana,30");
        List<Transaction> transactions = transactionParser.parse(lines);
        assertEquals(2, transactions.size());
        assertEquals(Transaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit().getName());
    }

    @Test
    void parse_InvalidLines_notOk() {
        List<String> invalidLines = List.of("b,apple");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> transactionParser.parse(invalidLines));
        assertEquals("Invalid transaction format. "
                + "Each line should contain exactly 3 parts separated by commas:"
                + " operation, fruit name, and quantity.b,apple", exception.getMessage());
    }
}
