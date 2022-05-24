package core.basesyntax.servise.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.servise.LineParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class LineParserServiceImplTest {
    private static LineParserService lineParserService;
    private static List<FruitTransaction> expected;

    @BeforeClass
    public static void beforeClass() {
        lineParserService = new LineParserServiceImpl();
        expected = new ArrayList<>();
    }

    @After
    public void after() {
        expected.clear();
    }

    @Test
    public void parse_validStringList_Ok() {
        setTransaction(FruitTransaction.Operation.BALANCE, "banana", 20);
        setTransaction(FruitTransaction.Operation.BALANCE, "apple", 100);
        List<String> toParse = new ArrayList<>();
        toParse.add("type,fruit,quantity");
        toParse.add("b,banana,20");
        toParse.add("b,apple,100");
        List<FruitTransaction> actual = lineParserService.parse(toParse);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected.size(), actual.size());
    }

    @Test
    public void parse_nullList_notOk() {
        try {
            lineParserService.parse(new ArrayList<>(null));
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("Should be thrown RuntimeException");
    }

    @Test
    public void parse_emptyList_notOk() {
        try {
            lineParserService.parse(new ArrayList<>());
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("Should be thrown RuntimeException");
    }

    @Test
    public void parse_invalidStringList_notOk() {
        List<String> toParse = new ArrayList<>();
        toParse.add("tyh,sthtrsh,shtrt");
        toParse.add("bs,bhstm,anana20");
        toParse.add("bs,app,sths");
        try {
            lineParserService.parse(toParse);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("Should be thrown RuntimeException");
    }

    private void setTransaction(FruitTransaction.Operation operation, String fruit, int quantity) {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(operation);
        fruitTransaction.setQuantity(quantity);
        fruitTransaction.setFruit(fruit);
        expected.add(fruitTransaction);
    }
}
