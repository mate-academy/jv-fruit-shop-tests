package service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import model.Fruit;
import model.FruitTransaction;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseServiceImplTest {
    private static ParseService parseService;

    @BeforeClass
    public static void beforeClass() {
        parseService = new ParseServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void parse_unknownOperation_notOk() {
        List<String> list = new ArrayList<>();
        list.add("b-apple-50");
        list.add("r-apple-20");
        list.add("u-apple-50");
        parseService.getInfo(list);
    }

    @Test
    public void parse_correctValue_Ok() {
        List<String> list = new ArrayList<>();
        list.add("b-apple-50");
        list.add("p-apple-20");
        list.add("s-apple-50");
        list.add("r-apple-52");
        Fruit apple = new Fruit("apple");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,apple,50));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,apple,20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,apple,50));
        expected.add(new FruitTransaction(FruitTransaction.Operation.RETURN,apple,52));
        List<FruitTransaction> actual = parseService.getInfo(list);
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(actual.get(i), expected.get(i));
            assertEquals(actual.get(i), expected.get(i));
            assertEquals(actual.get(i), expected.get(i));
            assertEquals(actual.get(i), expected.get(i));
        }
    }
}
