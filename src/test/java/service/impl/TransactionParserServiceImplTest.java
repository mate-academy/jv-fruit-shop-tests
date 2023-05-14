package service.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.TransactionParserService;

public class TransactionParserServiceImplTest {
    private static final String TITLE = "type,fruit,quantity";
    private static TransactionParserService transactionParserService
            = new TransactionParserServiceImpl();
    private static List<FruitTransaction> expected = new ArrayList<>();
    private static List<String> inputData = new ArrayList<>();

    @Before
    public void setUp() {
        inputData.add(TITLE);
    }

    @Test
    public void parseOneValidTransactionOk() {
        inputData.add("b,banana,17");
        expected.add(new FruitTransaction("b", "banana", 17));
        assertEquals(expected, transactionParserService.parseInputData(inputData));
    }

    @Test
    public void parseThreeValidTransactionsOk() {
        inputData.add("b,banana,17");
        inputData.add("s,apple,20");
        inputData.add("p,banana,11");
        expected.add(new FruitTransaction("b", "banana", 17));
        expected.add(new FruitTransaction("s", "apple", 20));
        expected.add(new FruitTransaction("p", "banana", 11));
        assertEquals(expected, transactionParserService.parseInputData(inputData));
    }

    @Test
    public void parseOnlyTitleOk() {
        assertEquals(expected, transactionParserService.parseInputData(inputData));
    }

    @Test (expected = RuntimeException.class)
    public void parseEmptyOk() {
        inputData.remove(0);
        transactionParserService.parseInputData(inputData);
    }

    @Test (expected = RuntimeException.class)
    public void parseNullOk() {
        inputData.remove(0);
        transactionParserService.parseInputData(null);
    }

    @After
    public void tearDown() {
        expected.clear();
        inputData.clear();
    }
}
