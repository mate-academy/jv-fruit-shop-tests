package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.LineParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LineParserServiceImplTest {
    private static LineParserService lineParser;

    @Before
    public void setUp() {
        lineParser = new LineParserServiceImpl();
    }

    @Test
    public void parseAll_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(fruitTransaction);
        List<String> actualData = new ArrayList<>();
        actualData.add("type,fruit,quantity");
        actualData.add("b,banana,20");
        List<FruitTransaction> actual = lineParser.parse(actualData);
        Assert.assertEquals(expected, actual);
    }
}
