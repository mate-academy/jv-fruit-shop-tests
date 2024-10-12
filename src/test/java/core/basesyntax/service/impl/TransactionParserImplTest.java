package core.basesyntax.service.impl;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> transactionParser.parse(invalidLines))
                .withMessage("Invalid transaction format. "
                        + "Each line should contain exactly 3 parts separated by commas:"
                        + " operation, fruit name, and quantity: b,apple");
    }

    @Test
    void parse_invalidOperationAbbreviation_notOk() {
        List<String> invalidOperation = List.of("x,banana,15");
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> transactionParser.parse(invalidOperation))
                .withMessage("Invalid operation in line: x,banana,15");
    }

    @Test
    void parse_invalidNumberFormat_notOk() {
        List<String> invalidQuantity = List.of("b,orange,abc");
        assertThatExceptionOfType(NumberFormatException.class)
                .isThrownBy(() -> transactionParser.parse(invalidQuantity))
                .withMessage("Unable to parse quantity: abc");

    }

    @Test
    void parse_missingQuantity_notOk() {
        List<String> missingQuantity = List.of("b,orange,");
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> transactionParser.parse(missingQuantity))
                .withMessage("Invalid transaction format. "
                        + "Each line should contain exactly 3 parts separated by commas:"
                        + " operation, fruit name, and quantity: b,orange,");
    }

    @Test
    void parse_missingOperationType_notOk() {
        List<String> missingFruitName = List.of(",orange,10");
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> transactionParser.parse(missingFruitName))
                .withMessage("Operation type cannot be empty. Line: ,orange,10");
    }

    @Test
    void parse_missingFruitName_notOk() {
        List<String> missingFruitName = List.of("b,,10");
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> transactionParser.parse(missingFruitName))
                        .withMessage("Fruit name cannot be empty. Line: b,,10");
    }
}
