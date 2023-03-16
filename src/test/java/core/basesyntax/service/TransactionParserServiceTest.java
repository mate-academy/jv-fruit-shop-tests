package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.FruitException;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserServiceTest {
    private static TransactionParserService transactionParserService;
    private static final String ANNOTATION = "type,fruit,quantity";
    private static final String FRUIT_TEST = "apple";
    private static final int AMOUNT_TEST = 48;
    private static final String CODE_OPERATION_TEST = "s";
    private static final String LINE_NEVER_NUMBER_PARTS = "s,apple,banana,10";
    private static final String LINE_BLANK_FRUIT = "s, ,10";
    private static final String LINE_NEGATIVE_AMOUNT = "s,apple,-10";
    private static final String LINE_INVALID_FORMAT_NUMBER = "s,apple,1+0%";
    private static List<String> listOperation;

    @BeforeClass
    public static void setUp() {
        transactionParserService = new TransactionParserService();
        listOperation = new ArrayList<>();
    }

    @Test
    public void parse_Ok() {
        listOperation.add(ANNOTATION);
        listOperation.add(CODE_OPERATION_TEST + "," + FRUIT_TEST + "," + AMOUNT_TEST);
        List<FruitTransaction> actualList = transactionParserService.parse(listOperation);
        List<FruitTransaction> expectedList =
                List.of(new FruitTransaction(
                        FruitTransaction.Operation.SUPPLY, FRUIT_TEST, AMOUNT_TEST));
        assertEquals(expectedList, actualList);
    }

    @Test (expected = FruitException.class)
    public void parse_NotOk_nullListOperation() {
        transactionParserService.parse(null);
    }

    @Test (expected = FruitException.class)
    public void parse_NotOk_neverNumberPart() {
        listOperation.add(LINE_NEVER_NUMBER_PARTS);
        transactionParserService.parse(listOperation);
    }

    @Test (expected = FruitException.class)
    public void parse_NotOk_invalidFormatNumber() {
        listOperation.add(LINE_INVALID_FORMAT_NUMBER);
        transactionParserService.parse(listOperation);
    }

    @Test (expected = FruitException.class)
    public void parse_NotOk_negativeAmount() {
        listOperation.add(LINE_NEGATIVE_AMOUNT);
        transactionParserService.parse(listOperation);
    }

    @Test (expected = FruitException.class)
    public void parse_NotOk_blankFruit() {
        listOperation.add(LINE_BLANK_FRUIT);
        transactionParserService.parse(listOperation);
    }

    @After
    public void afterClass() {
        listOperation.clear();
    }
}
