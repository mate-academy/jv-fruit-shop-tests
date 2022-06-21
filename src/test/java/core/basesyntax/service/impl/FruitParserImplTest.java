package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionInfo;
import core.basesyntax.service.FruitParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitParserImplTest {
    private static List<String> listWithLines;
    private static List<TransactionInfo> excepted;
    private static FruitParser fruitParser;

    @BeforeClass
    public static void beforeClass() {
        fruitParser = new FruitParserImpl();
        listWithLines = new ArrayList<>();
        listWithLines.add("type,fruit,quantity");
        listWithLines.add("b,banana,20");
        listWithLines.add("b,apple,100");
        listWithLines.add("s,banana,100");
        excepted = new ArrayList<>();
        excepted.add(new TransactionInfo("b", new Fruit("banana"), 20));
        excepted.add(new TransactionInfo("b", new Fruit("apple"), 100));
        excepted.add(new TransactionInfo("s", new Fruit("banana"), 100));
    }

    @Test
    public void parseValidData_isOk() {
        List<TransactionInfo> actual = fruitParser.parse(listWithLines);
        Assert.assertEquals(excepted, actual);
    }
}
