package core.basesyntax.service;

import static org.junit.Assert.assertTrue;
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
    private static final String LINE_NOTOK_2WORDS = "apple,15";
    private static final String LINE_NOTOK_4WORDS = "s,apple,15,apple";
    private static final String LINE_NOTOK_NOFRUT = "s,,15";
    private static final String LINE_NOTOK_NOVALUE = "s,apple,";
    private static final String LINE_NOTOK_LETER_IN_VALUE = "b,apple,f101";
    private static List<String> list;
    private static TransactionParserService transactionParserService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        transactionParserService = new TransactionParserService();
        list = new ArrayList<>();
        Storage.fruits.clear();
    }

    @Before
    public void setUp() throws Exception {
        list.add(LINE_OK_1);
        list.add(LINE_OK_2);
        list.add(LINE_OK_3);
    }

    @After
    public void tearDown() throws Exception {
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
        list.add(LINE_NOTOK_2WORDS);
        transactionParserService.parseFruitTransaction(list);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for not existing file, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void parseFruitTransaction_Line_With4Words_NotOk() {
        list.add(LINE_NOTOK_4WORDS);
        transactionParserService.parseFruitTransaction(list);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for this case, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void parseFruitTransaction_Line_NoFruit_NotOk() {
        list.add(LINE_NOTOK_NOFRUT);
        transactionParserService.parseFruitTransaction(list);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for this case, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void parseFruitTransaction_Line_NoValue_NotOk() {
        list.add(LINE_NOTOK_NOVALUE);
        transactionParserService.parseFruitTransaction(list);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for this case, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void parseFruitTransaction_Line_NegativeValue_NotOk() {
        list.add(LINE_NOTOK_LETER_IN_VALUE);
        transactionParserService.parseFruitTransaction(list);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for this case, but it wasn't");
    }

    @Test
    public void parseFruitTransaction_Line_Ok() {
        List<FruitTransaction> listTest = transactionParserService.parseFruitTransaction(list);
        FruitTransaction fruitTransaction = listTest.get(0);
        String expectedFruit = APPLE;
        FruitTransaction.Operation expectedOperation = FruitTransaction.Operation.BALANCE;
        int exeptedAmount = 101;
        assertTrue("Expected fruits = " + expectedFruit + ", but was "
                        + listTest.get(0).getFruit(),
                fruitTransaction.getFruit().equals(expectedFruit));

        assertTrue("Expected operation = " + expectedOperation + ", but was "
                        + listTest.get(0).getOperation(),
                fruitTransaction.getOperation() == expectedOperation);

        assertTrue("Expected amount = " + exeptedAmount + ", but was "
                        + listTest.get(0).getQuantity(),
                fruitTransaction.getQuantity() == exeptedAmount);
    }
}
