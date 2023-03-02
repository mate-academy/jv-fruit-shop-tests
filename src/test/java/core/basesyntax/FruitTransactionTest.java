package core.basesyntax;

import core.basesyntax.service.strategy.OperationHandler;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class FruitTransactionTest {
    private static final String LINE_OK_1 = "b,apple,101";
    private static final String LINE_OK_2 = "s,apple,10";
    private static final String LINE_OK_3 = "s,apple,15";
    private static final String LINE_NOTOK_2WORDS = "apple,15";
    private static final String LINE_NOTOK_4WORDS = "s,apple,15,apple";
    private static final String LINE_NOTOK_NOFRUT = "s,,15";
    private static final String LINE_NOTOK_NOVALUE = "s,apple,";
    private static List<String> list;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        //operationHandler = new FruitTransaction();
        list = new ArrayList<>();

        /*fruitTransaction.getStrategy().put(FruitTransaction.Operation.BALANCE.getCode(),
                new BalanceService());
        fruitTransaction.getStrategy().put(FruitTransaction.Operation.PURCHASE.getCode(),
                new PurchaseService());
        fruitTransaction.getStrategy().put(FruitTransaction.Operation.RETURN.getCode(),
                new ReturnService());
        fruitTransaction.getStrategy().put(FruitTransaction.Operation.SUPPLY.getCode(),
                new SupplyService());*/
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

    /*
    @Test(expected = FruitShopExeption.class)
    public void chooseStrategy_null_NotOk() {
        fruitTransaction.chooseStrategy(null);
        fail("Expected " + FruitShopExeption.class.getName()
                    + " to be thrown for the null list, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void chooseStrategy_Less3WordsInLine_NotOk() {
        list.add(LINE_NOTOK_2WORDS);
        fruitTransaction.chooseStrategy(list);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for less then 3 words in line, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void chooseStrategy_More3WordsInLine_NotOk() {
        list.add(LINE_NOTOK_4WORDS);
        fruitTransaction.chooseStrategy(list);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for more then 3 words in line, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void chooseStrategy_NoFruit_NotOk() {
        list.add(LINE_NOTOK_NOFRUT);
        fruitTransaction.chooseStrategy(list);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown in line no fruit, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void chooseStrategy_NoValue_NotOk() {
        list.add(LINE_NOTOK_NOVALUE);
        fruitTransaction.chooseStrategy(list);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown in line no value, but it wasn't");
    }*/
}
