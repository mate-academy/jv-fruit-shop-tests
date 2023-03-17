package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionParserServiceImplTest {
    private static final String TITLE = "type,fruit,quantity";
    private static TransactionParserService transactionParserService;
    private static List<FruitTransaction> expected;
    private static List<String> inputData;

    @BeforeAll
    static void beforeAll() {
        transactionParserService = new TransactionParserServiceImpl();
        expected = new ArrayList<>();
        inputData = new ArrayList<>();
    }

    @BeforeEach
    void setUp() {
        inputData.add(TITLE);
    }

    @Test
    void parseOneValidTransactionOk() {
        inputData.add("b,banana,17");
        expected.add(new FruitTransaction("b", "banana", 17));
        transactionParserService.parseInputData(inputData);
    }

    @Test
    void parseThreeValidTransactionsOk() {
        inputData.add("b,banana,17");
        inputData.add("s,apple,20");
        inputData.add("p,banana,11");
        expected.add(new FruitTransaction("b", "banana", 17));
        expected.add(new FruitTransaction("s", "apple", 20));
        expected.add(new FruitTransaction("p", "banana", 11));
        assertEquals(expected, transactionParserService.parseInputData(inputData));
    }

    @Test
    void parseOnlyTitleOk() {
        assertEquals(expected, transactionParserService.parseInputData(inputData));
    }

    @Test
    void parseEmptyOk() {
        inputData.remove(0);
        assertThrows(RuntimeException.class, () -> {
            transactionParserService.parseInputData(inputData);
        });
    }

    @AfterEach
    void tearDown() {
        expected.clear();
        inputData.clear();
    }
}
