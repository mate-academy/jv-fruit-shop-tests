package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FormatParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FormatParserServiceImplTest {
    private static FormatParserService formatParserService;

    @BeforeClass
    public static void beforeClass() {
        formatParserService = new FormatParserServiceImpl();
    }

    @Test(expected = NullPointerException.class)
    public void parse_nullInputData_NotOk() {
        formatParserService.parseData(null);
    }

    @Test
    public void parse_validInputData_Ok() {
        String inputData = "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "r,apple,10\n";

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction
                .Operation.BALANCE, new Fruit("banana"), 20));
        expected.add(new FruitTransaction(FruitTransaction
                .Operation.RETURN, new Fruit("apple"), 10));

        List<FruitTransaction> actual = formatParserService.parseData(inputData);
        Assert.assertEquals(expected, actual);
    }
}
