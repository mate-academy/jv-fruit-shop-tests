package basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import basesyntax.model.FruitTransaction;
import basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static Parser parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl();
    }

    @Test
    public void parseData_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 20));
        List<String> actualData = new ArrayList<>();
        actualData.add("type,fruit,quantity");
        actualData.add("b,banana,20");
        List<FruitTransaction> actual = parser.parseData(actualData);
        assertEquals(expected, actual);
    }
}
