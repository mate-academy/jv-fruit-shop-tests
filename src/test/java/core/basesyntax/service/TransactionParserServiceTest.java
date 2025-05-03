package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.FruitStoreException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.TransactionParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserServiceTest {
    private static final String LINE_OK_1 = "b,apple,101";
    private static final String LINE_OK_2 = "s,apple,10";
    private static final String LINE_OK_3 = "s,apple,15";
    private static final String APPLE = "apple";
    private static final String LINE_NO_TOK_2WORDS = "apple,15";
    private static final String LINE_NOT_OK_4WORDS = "s,apple,15,apple";
    private static final String LINE_NOT_OK_NO_FRUIT = "s,,15";
    private static final String LINE_NOT_OK_NO_VALUE = "s,apple,";
    private static final String LINE_NOT_OK_LETTER_IN_VALUE = "b,apple,f101";
    private static final int EXPECTED_AMOUNT = 101;
    private static List<String> list;
    private static TransactionParserService transactionParserService;

    @BeforeClass
    public static void beforeClass() {
        transactionParserService = new TransactionParserServiceImpl();
        list = new ArrayList<>();
    }

    @After
    public void tearDown() {
        list.clear();
    }

    @Test(expected = FruitStoreException.class)
    public void parseFruitTransaction_null_NotOk() {
        transactionParserService.parse(null);
    }

    @Test(expected = FruitStoreException.class)
    public void parseFruitTransaction_Line_With2Words_NotOk() {
        list.add(LINE_NO_TOK_2WORDS);
        transactionParserService.parse(list);
    }

    @Test(expected = FruitStoreException.class)
    public void parseFruitTransaction_Line_With4Words_NotOk() {
        list.add(LINE_NOT_OK_4WORDS);
        transactionParserService.parse(list);
    }

    @Test(expected = FruitStoreException.class)
    public void parseFruitTransaction_Line_NoFruit_NotOk() {
        list.add(LINE_NOT_OK_NO_FRUIT);
        transactionParserService.parse(list);
    }

    @Test(expected = FruitStoreException.class)
    public void parseFruitTransaction_Line_NoValue_NotOk() {
        list.add(LINE_NOT_OK_NO_VALUE);
        transactionParserService.parse(list);
    }

    @Test(expected = FruitStoreException.class)
    public void parseFruitTransaction_Line_NegativeValue_NotOk() {
        list.add(LINE_NOT_OK_LETTER_IN_VALUE);
        transactionParserService.parse(list);
    }

    @Test
    public void parseFruitTransaction_Line_Ok() {
        List<String> list2 = List.of(LINE_OK_1, LINE_OK_2, LINE_OK_3);
        List<FruitTransaction> listTest = transactionParserService.parse(list2);
        FruitTransaction fruitTransaction = listTest.get(0);
        String expectedFruit = APPLE;
        String actualFruit = listTest.get(0).getFruit();
        assertEquals(expectedFruit, actualFruit);
        FruitTransaction.Operation expectedOperation = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actualOperation = fruitTransaction.getOperation();
        assertEquals(expectedOperation, actualOperation);
        int expectedAmount = EXPECTED_AMOUNT;
        int actualAmount = listTest.get(0).getQuantity();
        assertEquals(expectedAmount, actualAmount);
    }
}
