package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static Parser parserService;
    private static List<String> parserList;

    @BeforeClass
    public static void beforeClass() {
        parserService = new ParserImpl();
        parserList = List.of("type,fruit,quantity", "p,banana,10");
    }

    @Test
    public void parseMethod_isOk() {
        List<Transaction> expected = new ArrayList<>();
        expected.add(new Transaction("p", new Fruit("banana"), 10));
        List<Transaction> actual = parserService.parse(parserList);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_inputIsEmpty_isNotOk() {
        parserService.parse(null);
    }
}
