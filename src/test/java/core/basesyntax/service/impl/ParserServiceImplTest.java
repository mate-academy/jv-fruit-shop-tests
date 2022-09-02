package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParserServiceImplTest {
    private static ParserService parserService;

    @Before
    public void setUp() throws Exception {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parserService_checkDate_ok() {
        List<String> inputData = new ArrayList<>();
        inputData.add("type,fruit,quantity");
        inputData.add("b,banana,20");
        inputData.add("b,apple,100");
        inputData.add("s,banana,100");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", new Fruit("banana"), 20));
        expected.add(new FruitTransaction("b", new Fruit("apple"), 100));
        expected.add(new FruitTransaction("s", new Fruit("banana"), 100));
        List<FruitTransaction> actual = parserService.parse(inputData);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parserService_checkNull_NotOk() {
        List<String> inputData = new ArrayList<>();
        inputData.add("type,fruit,quantity");
        inputData.add("b,banana,null");
        inputData.add("b,apple,100");
        inputData.add("s,banana,100");
        List<FruitTransaction> actual = parserService.parse(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void parserService_checkQuantityIsString_NotOk() {
        List<String> inputData = new ArrayList<>();
        inputData.add("type,fruit,quantity");
        inputData.add("b,banana,dfdff");
        inputData.add("b,apple,100");
        inputData.add("s,banana,100");
        List<FruitTransaction> actual = parserService.parse(inputData);
    }
}
