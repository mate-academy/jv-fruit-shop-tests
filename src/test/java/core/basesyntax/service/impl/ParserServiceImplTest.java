package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static ParserService parserService;

    @BeforeClass
    public static void beforeClass() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parser_validParseTest_Ok() {
        String data = "type,fruit,quantity" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator()
                + "s,banana,100" + System.lineSeparator()
                + "p,banana,13";
        List<Transaction> expected = new ArrayList<>();
        expected.add(new Transaction(new Fruit("apple"), 100, "b"));
        expected.add(new Transaction(new Fruit("banana"), 100, "s"));
        expected.add(new Transaction(new Fruit("banana"), 13, "p"));
        List<Transaction> actual = parserService.parse(data);
        assertEquals(expected, actual);
    }
}
