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
        lines.add("p,banana,5");
        List<FruitTransaction> transactions = readParser.parse(lines);
        assertNotNull(transactions);
        assertEquals(2, transactions.size());
        FruitTransaction transaction1 = transactions.get(0);
        assertEquals(Operation.BALANCE, transaction1.getOperation());
        assertEquals("apple", transaction1.getFruit());
        assertEquals(10, transaction1.getQuantity());
        FruitTransaction transaction2 = transactions.get(1);
        assertEquals(Operation.PURCHASE, transaction2.getOperation());
        assertEquals("banana", transaction2.getFruit());
        assertEquals(5, transaction2.getQuantity());
    }
}
