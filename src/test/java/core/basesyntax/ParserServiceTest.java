package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.ParserService;
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
        FruitTransaction fruitTransaction1 = new FruitTransaction(OperationType.BALANCE,
                "banana", 40);
        FruitTransaction fruitTransaction2 = new FruitTransaction(OperationType.BALANCE,
                "apple", 80);
        FruitTransaction fruitTransaction3 = new FruitTransaction(OperationType.BALANCE,
                "orange", 30);

        FruitTransaction fruitTransaction4 = new FruitTransaction(OperationType.SUPPLY,
                "banana", 10);
        FruitTransaction fruitTransaction5 = new FruitTransaction(OperationType.SUPPLY,
                "apple", 15);
        FruitTransaction fruitTransaction6 = new FruitTransaction(OperationType.SUPPLY,
                "orange", 20);

        FruitTransaction fruitTransaction7 = new FruitTransaction(OperationType.PURCHASE,
                "banana", 30);
        FruitTransaction fruitTransaction8 = new FruitTransaction(OperationType.PURCHASE,
                "apple", 45);
        FruitTransaction fruitTransaction9 = new FruitTransaction(OperationType.PURCHASE,
                "orange", 50);

        FruitTransaction fruitTransaction10 = new FruitTransaction(OperationType.RETURN,
                "banana", 5);
        FruitTransaction fruitTransaction11 = new FruitTransaction(OperationType.RETURN,
                "apple", 10);
        FruitTransaction fruitTransaction12 = new FruitTransaction(OperationType.RETURN,
                "orange", 15);

        expectedFruitTransactionList = List.of(fruitTransaction1, fruitTransaction2,
                fruitTransaction3, fruitTransaction4, fruitTransaction5, fruitTransaction6,
                fruitTransaction7, fruitTransaction8, fruitTransaction9, fruitTransaction10,
                fruitTransaction11, fruitTransaction12);

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
        assertThrows(ValidationException.class,
                () -> {
                    parser.getFruitTransaction(null);
                });
    }

    @Test
    public void add_emptyDataToGetFruitTransactionList_NotOk() {
        ParserService parser = new ParserServiceImpl();
        assertThrows(ValidationException.class,
                () -> {
                    parser.getFruitTransaction(new ArrayList<>());
                });
    }
}
