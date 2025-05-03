package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.dao.Storage;
import core.basesyntax.exeption.FruitShopExeption;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
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
    private static List<String> list;
    private static TransactionParserService transactionParserService;

    @BeforeClass
    public static void beforeClass() {
        transactionParserService = new TransactionParserService();
        list = new ArrayList<>();
        Storage.fruits.clear();
    }

    @Before
    public void setUp() {
        list.add(LINE_OK_1);
        list.add(LINE_OK_2);
        list.add(LINE_OK_3);
    }

    @After
    public void tearDown() {
        list.clear();
    }

    @Test(expected = FruitShopExeption.class)
    public void parseFruitTransaction_null_NotOk() {
        transactionParserService.parseFruitTransaction(null);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for not existing file, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void parseFruitTransaction_Line_With2Words_NotOk() {
        list.add(LINE_NO_TOK_2WORDS);
        transactionParserService.parseFruitTransaction(list);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for not existing file, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void parseFruitTransaction_Line_With4Words_NotOk() {
        list.add(LINE_NOT_OK_4WORDS);
        transactionParserService.parseFruitTransaction(list);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for this case, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void parseFruitTransaction_Line_NoFruit_NotOk() {
        list.add(LINE_NOT_OK_NO_FRUIT);
        transactionParserService.parseFruitTransaction(list);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for this case, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void parseFruitTransaction_Line_NoValue_NotOk() {
        list.add(LINE_NOT_OK_NO_VALUE);
        transactionParserService.parseFruitTransaction(list);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for this case, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void parseFruitTransaction_Line_NegativeValue_NotOk() {
        list.add(LINE_NOT_OK_LETTER_IN_VALUE);
        transactionParserService.parseFruitTransaction(list);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for this case, but it wasn't");
    }

    @Test
    public void parseFruitTransaction_Line_Ok() {
        List<FruitTransaction> listTest = transactionParserService.parseFruitTransaction(list);
        FruitTransaction fruitTransaction = listTest.get(0);

        String expectedFruit = APPLE;
        String actualFruit = listTest.get(0).getFruit();
        assertEquals("Expected fruits = " + expectedFruit + ", but was: " + actualFruit,
                expectedFruit, actualFruit);

        FruitTransaction.Operation expectedOperation = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actualOperation = fruitTransaction.getOperation();
        assertEquals("Expected operation = " + expectedOperation + ", but was: " + actualOperation,
                expectedOperation, actualOperation);

        int expectedAmount = 101;
        int actualAmount = listTest.get(0).getQuantity();
        assertEquals("Expected amount = " + expectedAmount + ", but was: " + actualAmount,
                expectedAmount, actualAmount);
    }
}
