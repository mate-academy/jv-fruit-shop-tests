package core.basesyntax.service.impl;

import core.basesyntax.dto.ProductTransaction;
import core.basesyntax.enums.Operation;
import core.basesyntax.service.TransactionParser;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CsvParserTest {
    private static final List<String> VALID_TEST_DATA = Arrays.asList(
            "operation,fruit,quantity",
            "b,apple,10",
            "s,banana,20",
            "p,orange,5"
    );
    private static final List<String> EMPTY_TEST_DATA = Collections.emptyList();
    private static final List<ProductTransaction> EXPECTED_TRANSACTIONS = Arrays.asList(
            new ProductTransaction(Operation.BALANCE, "apple", 10),
            new ProductTransaction(Operation.SUPPLY, "banana", 20),
            new ProductTransaction(Operation.PURCHASE, "orange", 5)
    );
    private static TransactionParser csvParser;

    @BeforeAll
    static void beforeAll() {
        csvParser = new CsvParser();
    }

    @Test
    public void parseTransactions_ValidData_ReturnsListOfProductTransactions() {
        List<ProductTransaction> actualTransactions = csvParser.parseTransactions(VALID_TEST_DATA);

        Assertions.assertEquals(EXPECTED_TRANSACTIONS, actualTransactions);
    }

    @Test
    public void parseTransactions_EmptyData_ReturnsEmptyList() {
        List<ProductTransaction> actualTransactions = csvParser.parseTransactions(EMPTY_TEST_DATA);

        Assertions.assertTrue(actualTransactions.isEmpty());
    }
}
