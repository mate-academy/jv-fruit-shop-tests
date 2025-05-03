package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserData;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserDataImplTest {
    private static ParserData parserData;

    @BeforeClass
    public static void setUp() {
        parserData = new ParserDataImpl();
    }

    @Test
    public void parse_emptyList_ok() {
        List<FruitTransaction> actual = parserData.parseData(List.of());
        assertEquals(0, actual.size());
    }

    @Test
    public void parse_someData_ok() {
        List<String> listForParse
                = List.of("b,banana,20", "b,apple,100", "r,apple,10");
        List<FruitTransaction> expectedList
                = List.of(new FruitTransaction("b", "banana", 20),
                new FruitTransaction("b", "apple", 100),
                new FruitTransaction("r", "apple", 10));
        List<FruitTransaction> actual = parserData.parseData(listForParse);
        assertEquals(expectedList, actual);
    }
}
