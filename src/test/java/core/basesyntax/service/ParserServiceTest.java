package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceTest {
    private static List<FruitTransaction> expectedFruitTransactionList;
    private static final List<String> SENDING_DATA = new ArrayList<>();

    @BeforeClass
    public static void beforeClass() {
        FruitTransaction bananaForBalance = new FruitTransaction(OperationType.BALANCE,
                "banana", 40);
        FruitTransaction appleForBalance = new FruitTransaction(OperationType.BALANCE,
                "apple", 80);
        FruitTransaction orangeForBalance = new FruitTransaction(OperationType.BALANCE,
                "orange", 30);

        FruitTransaction fruitTransaction4 = new FruitTransaction(OperationType.SUPPLY,
                "banana", 10);
        FruitTransaction appleForSupply = new FruitTransaction(OperationType.SUPPLY,
                "apple", 15);
        FruitTransaction orangeForSupply = new FruitTransaction(OperationType.SUPPLY,
                "orange", 20);

        FruitTransaction bananaForPurchase = new FruitTransaction(OperationType.PURCHASE,
                "banana", 30);
        FruitTransaction appleForPurchase = new FruitTransaction(OperationType.PURCHASE,
                "apple", 45);
        FruitTransaction orangeForPurchase = new FruitTransaction(OperationType.PURCHASE,
                "orange", 50);

        FruitTransaction bananaForReturn = new FruitTransaction(OperationType.RETURN,
                "banana", 5);
        FruitTransaction appleForReturn = new FruitTransaction(OperationType.RETURN,
                "apple", 10);
        FruitTransaction orangeForReturn = new FruitTransaction(OperationType.RETURN,
                "orange", 15);

        expectedFruitTransactionList = List.of(bananaForBalance, appleForBalance,
                orangeForBalance, fruitTransaction4, appleForSupply, orangeForSupply,
                bananaForPurchase, appleForPurchase, orangeForPurchase, bananaForReturn,
                appleForReturn, orangeForReturn);

        SENDING_DATA.add("type,fruit,quantity");
        SENDING_DATA.add("b,banana,40");
        SENDING_DATA.add("b,apple,80");
        SENDING_DATA.add("b,orange,30");
        SENDING_DATA.add("s,banana,10");
        SENDING_DATA.add("s,apple,15");
        SENDING_DATA.add("s,orange,20");
        SENDING_DATA.add("p,banana,30");
        SENDING_DATA.add("p,apple,45");
        SENDING_DATA.add("p,orange,50");
        SENDING_DATA.add("r,banana,5");
        SENDING_DATA.add("r,apple,10");
        SENDING_DATA.add("r,orange,15");
    }

    @Test
    public void create_fruitTransactionList_Ok() {
        ParserService parser = new ParserServiceImpl();
        List<FruitTransaction> actualFruitTransaction = parser.getFruitTransaction(SENDING_DATA);

        Assert.assertEquals("Wrong Fruit Transactions List",
                expectedFruitTransactionList, actualFruitTransaction);
    }

    @Test
    public void add_nullToGetFruitTransactionList_NotOk() {
        ParserService parser = new ParserServiceImpl();
        assertThrows(ValidationException.class, () ->
                    parser.getFruitTransaction(null));
    }

    @Test
    public void add_emptyDataToGetFruitTransactionList_NotOk() {
        ParserService parser = new ParserServiceImpl();
        assertThrows(ValidationException.class, () ->
                    parser.getFruitTransaction(new ArrayList<>()));
    }
}
