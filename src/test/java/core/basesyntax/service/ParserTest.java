package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {
    private static Parser parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl();
    }

    @Test
    public void parse_parseData_Ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,"apple",25));
        List<String> actualData = new ArrayList<>();
        actualData.add("type,fruit,quantity");
        actualData.add("b,apple,25");
        List<FruitTransaction> actual = parser.parseData(actualData);
        assertEquals(expected,actual);
    }
}
