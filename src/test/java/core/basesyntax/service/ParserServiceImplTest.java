package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParserServiceImplTest {
    private List<String> expected;
    private ParserServiceImpl fileParser;

    @BeforeEach
    public void setUp() {
        fileParser = new ParserServiceImpl();
        expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
    }

    @Test
    public void parseData_deleteTitleFromList_Ok() {
        int expectSize = 8;
        List<FruitTransaction> actual = fileParser.parseData(expected);
        assertEquals(expectSize, actual.size());
    }

    @Test
    public void parseData_correctLineParsing_Ok() {
        List<FruitTransaction> actual = fileParser.parseData(expected);
        assertEquals(FruitTransaction.Operation.BALANCE, actual.get(0).getOperation());
        assertEquals("banana", actual.get(0).getFruit());
        assertEquals(20, actual.get(0).getQuantity());
        assertEquals(FruitTransaction.Operation.BALANCE, actual.get(1).getOperation());
        assertEquals("apple", actual.get(1).getFruit());
        assertEquals(100, actual.get(1).getQuantity());
        assertEquals(FruitTransaction.Operation.SUPPLY, actual.get(7).getOperation());
        assertEquals("banana", actual.get(7).getFruit());
        assertEquals(50, actual.get(7).getQuantity());
    }
}
