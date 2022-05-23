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

    @BeforeClass
    public static void beforeClass() {
        lineParserService = new LineParserServiceImpl();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void parse_validStringList_Ok() {
        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setFruit("banana");
        firstTransaction.setQuantity(20);
        firstTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        FruitTransaction secondTransaction = new FruitTransaction();
        secondTransaction.setFruit("apple");
        secondTransaction.setQuantity(100);
        secondTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(firstTransaction);
        expected.add(secondTransaction);
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
}
