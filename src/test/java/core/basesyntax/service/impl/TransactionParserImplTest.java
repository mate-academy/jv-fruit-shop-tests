package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;
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
    void parse_invalidPartsCount_notOk() {
        List<String> invalidLines = List.of("b,apple");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> transactionParser.parse(invalidLines));
        assertEquals("Invalid transaction format. "
                + "Each line should contain exactly 3 parts separated by commas:"
                + " operation, fruit name, and quantity.b,apple", exception.getMessage());
    }

    @Test
    void parse_invalidOperationAbbreviation_notOk() {
        List<String> invalidOperation = List.of("x,banana,15");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> transactionParser.parse(invalidOperation));
        assertTrue(thrown.getMessage().contains("Invalid operation"));
    }

    @Test
    void parse_invalidNumberFormat_notOk() {
        List<String> invalidQuantity = List.of("b,orange,abc");
        NumberFormatException thrown = assertThrows(NumberFormatException.class, () ->
                transactionParser.parse(invalidQuantity));
        assertTrue(thrown.getMessage().contains("Unable to parse quantity: "));

    }

    @Test
    void parse_missingQuantity_notOk() {
        List<String> missingQuantity = List.of("b,orange,");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                transactionParser.parse(missingQuantity));
        assertTrue(thrown.getMessage().contains("Invalid transaction format. "));
    }

    @Test
    void parse_missingOperationType_notOk() {
        List<String> missingFruitName = List.of(",orange,10");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                transactionParser.parse(missingFruitName));
        assertTrue(thrown.getMessage().contains("Operation type cannot be empty. "));
    }

    @Test
    void parse_missingFruitName_notOk() {
        List<String> missingFruitName = List.of("b,,10");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                transactionParser.parse(missingFruitName));
        assertTrue(thrown.getMessage().contains("Fruit name cannot be empty. "));
    }
}
