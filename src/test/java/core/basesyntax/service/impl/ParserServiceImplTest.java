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
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", new Fruit("banana"), 20));
        List<FruitTransaction> actual = parserService.parse(inputData);
        Assert.assertEquals(expected, actual);
    }
}
