package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserData;
import java.util.List;
import org.junit.Test;

public class ParserDataImplTest {
    private static final List<String> LIST_FOR_PARSE
            = List.of("b,banana,20", "b,apple,100", "r,apple,10");
    private static final List<FruitTransaction> EXPECTED_LIST
            = List.of(new FruitTransaction("b", "banana", 20),
                    new FruitTransaction("b", "apple", 100),
                    new FruitTransaction("r", "apple", 10));
    private final ParserData parserData = new ParserDataImpl();

    @Test
    public void parse_emptyList_ok() {
        List<FruitTransaction> actual = parserData.parseData(List.of());
        assertEquals(0, actual.size());
    }

    @Test
    public void parse_someData_ok() {
        List<FruitTransaction> actual = parserData.parseData(LIST_FOR_PARSE);
        assertEquals(EXPECTED_LIST, actual);
    }
}
