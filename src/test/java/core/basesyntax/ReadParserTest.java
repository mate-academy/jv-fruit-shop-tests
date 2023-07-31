package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ReadParser;
import core.basesyntax.service.impl.ReadParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ReadParserTest {
    private ReadParser readParser;

    @Before
    public void setUp() {
        readParser = new ReadParserImpl();
    }

    @Test
    public void readParse_ValidData_Ok() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,apple,10");
        List<FruitTransaction> transactions = readParser.parse(lines);
        FruitTransaction transaction = transactions.get(0);
        assertEquals(Operation.BALANCE, transaction.getOperation());
        assertEquals("apple", transaction.getFruit());
        assertEquals(10, transaction.getQuantity());
    }

    @Test
    public void readParse_Empty_Ok() {
        List<String> lines = new ArrayList<>();
        List<FruitTransaction> transactions = readParser.parse(lines);
        assertNotNull(transactions);
        assertEquals(0, transactions.size());
    }

    @Test
    public void readParse_OnlyHeader_Ok() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        List<FruitTransaction> transactions = readParser.parse(lines);
        assertNotNull(transactions);
        assertEquals(0, transactions.size());
    }
}
