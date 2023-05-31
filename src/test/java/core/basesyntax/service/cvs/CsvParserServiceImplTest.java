package core.basesyntax.service.cvs;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CsvParserServiceImplTest {
    private static final int SIZE_LIST = 3;
    private CsvParserService csvParserService;

    @Before
    public void setUp() {
        csvParserService = new CsvParserServiceImpl();
    }

    @Test
    public void parse_isOk() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("orange", 25, Operation.BALANCE));
        expected.add(new FruitTransaction("orange", 15, Operation.SUPPLY));
        expected.add(new FruitTransaction("melon", 15, Operation.RETURN));
        List<String> list = List.of("type,fruit,quantity", "b,orange,25", "s,orange,15",
                "r,melon,15");
        List<FruitTransaction> actual = csvParserService.parse(list);
        assertEquals("Test failed! Size of list should be " + SIZE_LIST + " but it is "
                + actual.size(), SIZE_LIST, actual.size());
        assertEquals(expected, actual);
    }
}
