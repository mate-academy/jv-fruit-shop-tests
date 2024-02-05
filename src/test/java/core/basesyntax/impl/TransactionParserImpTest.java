package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.fruittransaction.FruitTransaction;
import core.basesyntax.operations.Operation;
import core.basesyntax.service.TransactionParser;
import java.util.List;
import org.junit.jupiter.api.Test;

class TransactionParserImpTest {

    @Test
    void parse() {
        Operation operation = Operation.PURCHASE;

        TransactionParser transactionParser = new TransactionParserImp();

        String testData = "p,Apple,5" + System.lineSeparator()
                + "s,Banana,3"
                + System.lineSeparator();

        List<FruitTransaction> transactions = transactionParser.parse(testData);

        // Verify the result
        assertEquals(2, transactions.size());

        // Verify the content of the first transaction
        FruitTransaction firstTransaction = transactions.get(0);
        assertEquals(operation, firstTransaction.getOperation());
        assertEquals("Apple", firstTransaction.getName());
        assertEquals(5, firstTransaction.getAmount());

        // Verify the content of the second transaction
        FruitTransaction secondTransaction = transactions.get(1);
        assertEquals(Operation.SUPPLY, secondTransaction.getOperation());
        assertEquals("Banana", secondTransaction.getName());
        assertEquals(3, secondTransaction.getAmount());
    }

    @Test
    public void testParseLine() {
        // Mock or create a suitable implementation for the Operation enum
        Operation operation = Operation.PURCHASE; // replace with appropriate enum value

        // Mock or create a suitable implementation for the TransactionParser interface
        TransactionParser transactionParser = new TransactionParserImp();

        // Test data
        String testData = "p,Apple,5";

        // Call the parseLine method
        FruitTransaction transaction = new FruitTransaction(operation, "Apple", 5);

        // Verify the result
        assertEquals(transaction.getOperation().getCode() + ","
                + transaction.getName() + ","
                + transaction.getAmount(), testData);
    }
}
