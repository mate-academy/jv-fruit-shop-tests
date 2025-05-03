package core.basesyntax.service.implementation;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ParseServiceImplTest {
    private final ParseService parseService = new ParseServiceImpl();

    @Test(expected = RuntimeException.class)
    public void parse_NullList_NotOk() {
        parseService.parse(null);
    }

    @Test
    public void parse_List_Ok() {
        List<String> lines = new ArrayList<>();
        lines.add("transaction,fruitType,quantity");
        lines.add("b,banana,20");
        lines.add("b,banana,100");
        lines.add("s,apple,30");
        List<FruitTransaction> fruitTransactiions
                = parseService.parse(lines);
        Assert.assertEquals(3, fruitTransactiions.size());
        Assert.assertEquals("banana", fruitTransactiions.get(0).getFruit());
        Assert.assertEquals(20, fruitTransactiions.get(0).getQuantity());
        Assert.assertEquals(FruitTransaction.Operation.BALANCE,
                fruitTransactiions.get(0).getOperation());
        Assert.assertEquals("banana", fruitTransactiions.get(1).getFruit());
        Assert.assertEquals(100, fruitTransactiions.get(1).getQuantity());
        Assert.assertEquals(FruitTransaction.Operation.BALANCE,
                fruitTransactiions.get(1).getOperation());
        Assert.assertEquals("apple", fruitTransactiions.get(2).getFruit());
        Assert.assertEquals(30, fruitTransactiions.get(2).getQuantity());
        Assert.assertEquals(FruitTransaction.Operation.SUPPLY,
                fruitTransactiions.get(2).getOperation());
    }
}
