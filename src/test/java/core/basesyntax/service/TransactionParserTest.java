package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserTest {

    private TransactionParser transactionParser;

    @Before
    public void setUp() {
        transactionParser = new TransactionParser();
    }

    @Test
    public void parseTransactions_ValidInput_ReturnsCorrectTransactions() {
        List<String> inputLines = Arrays.asList(
                "p,Apple,10",
                "r,Banana,5",
                "s,Orange,20",
                "b,Lemon,15"
        );
        List<FruitTransaction> expectedTransactions = Arrays.asList(
                new FruitTransaction(OperationType.PURCHASE, "Apple", 10),
                new FruitTransaction(OperationType.RETURN, "Banana", 5),
                new FruitTransaction(OperationType.SUPPLY, "Orange", 20),
                new FruitTransaction(OperationType.BALANCE, "Lemon", 15)
        );
        List<FruitTransaction> actualTransactions
                = transactionParser.parseTransactions(inputLines);
        assertEquals("The number of transactions should match",
                expectedTransactions.size(), actualTransactions.size());
        for (int i = 0; i < expectedTransactions.size(); i++) {
            assertEquals("Transaction " + i + " is incorrect",
                    expectedTransactions.get(i), actualTransactions.get(i));
        }
    }

    @Test
    public void parseTransactions_negativeQuantity_throwsIllegalArgumentException() {
        List<String> inputLines = Arrays.asList("p,Apple,-10");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                transactionParser.parseTransactions(inputLines));
        assertTrue(exception.getMessage().contains("Quantity cannot be negative"),
                "Error message should contain 'Quantity cannot be negative'");
    }

    @Test
    public void parseTransactions_invalidOperationCode_throwsIllegalArgumentException() {
        List<String> inputLines = Arrays.asList("INVALID,Apple,10");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                transactionParser.parseTransactions(inputLines));
        assertTrue(exception.getMessage().contains("Invalid operation code"),
                "Error message should contain 'Invalid operation code'");
    }

    @Test
    public void parseTransactions_invalidQuantity_throwsIllegalArgumentException() {
        List<String> inputLines = Arrays.asList("p,Apple,abc");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                transactionParser.parseTransactions(inputLines));
        assertTrue(exception.getMessage().contains("Quantity is not a valid number"),
                "Error message should contain 'Quantity is not a valid number'");
    }

    @Test
    public void parseTransactions_EmptyInput_ReturnsEmptyList() {
        List<String> inputLines = Arrays.asList();
        List<FruitTransaction> actualTransactions
                = transactionParser.parseTransactions(inputLines);
        assertTrue(actualTransactions.isEmpty(), "The transaction list should be empty");
    }
}
