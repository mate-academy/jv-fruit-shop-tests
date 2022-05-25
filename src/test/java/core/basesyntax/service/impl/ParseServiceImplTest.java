package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseServiceImplTest {
    private static ParserService parseService;

    @BeforeClass
    public static void beforeClass() {
        parseService = new ParseServiceImpl();
    }

    @Test
    public void parse_nullList_notOK() {
        try {
            parseService.parse(null);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void parse_goodList_OK() {
        List<String> lines = new ArrayList<>();
        lines.add("operation,fruit,quantity");
        lines.add("b,apple,10");
        lines.add("s,banana,20");
        List<FruitTransaction> fruitTransactions = parseService.parse(lines);
        Assert.assertEquals(2, fruitTransactions.size());
        Assert.assertEquals("apple", fruitTransactions.get(0).getFruit());
        Assert.assertEquals(10, fruitTransactions.get(0).getQuantity());
        Assert.assertEquals(FruitTransaction.Operation.BALANCE,
                fruitTransactions.get(0).getOperation());
        Assert.assertEquals("banana", fruitTransactions.get(1).getFruit());
        Assert.assertEquals(20, fruitTransactions.get(1).getQuantity());
        Assert.assertEquals(FruitTransaction.Operation.SUPPLY,
                fruitTransactions.get(1).getOperation());
    }
}
