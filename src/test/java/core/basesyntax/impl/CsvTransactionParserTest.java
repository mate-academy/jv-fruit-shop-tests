package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvTransactionParserTest {
    private TransactionParser transactionParser;

    @BeforeEach
    void setUp() {
        transactionParser = new CsvTransactionParser();
    }

    @Test
    void parseTransactions_WhenGivenValidData_ReturnsListOfTransactions() {
        List<String> data = new ArrayList<>();
        data.add("SUPPLY,apple,10");
        data.add("PURCHASE,banana,5");

        List<FruitTransaction> expectedTransactions = new ArrayList<>();
        expectedTransactions.add(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 10)
        );
        expectedTransactions.add(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5)
        );

        List<FruitTransaction> parsedTransactions = transactionParser.parseTransactions(data);

        assertEquals(expectedTransactions.size(), parsedTransactions.size());

        for (int i = 0; i < expectedTransactions.size(); i++) {
            FruitTransaction expected = expectedTransactions.get(i);
            FruitTransaction actual = parsedTransactions.get(i);

            assertEquals(expected.getOperation(), actual.getOperation());
            assertEquals(expected.getFruit(), actual.getFruit());
            assertEquals(expected.getQuantity(), actual.getQuantity());
        }
    }

    @Test
    void parseTransactions_WhenGivenInvalidData_ReturnsEmptyList() {
        List<String> data = new ArrayList<>();
        data.add("ADD,apple");

        List<FruitTransaction> parsedTransactions = transactionParser.parseTransactions(data);

        assertEquals(0, parsedTransactions.size());
    }

    @Test
    void parseTransactions_WhenGivenEmptyData_ReturnsEmptyList() {
        List<String> data = new ArrayList<>();

        List<FruitTransaction> parsedTransactions = transactionParser.parseTransactions(data);

        assertEquals(0, parsedTransactions.size());
    }
}
