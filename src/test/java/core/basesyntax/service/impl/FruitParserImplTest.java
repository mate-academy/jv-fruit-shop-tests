package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitParser;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FruitParserImplTest {
    private final String dataFromFile = "type,fruit,quantity" + System.lineSeparator()
            + "b,banana,20" + System.lineSeparator()
            + "b,apple,100" + System.lineSeparator()
            + "s,banana,10" + System.lineSeparator()
            + "p,banana,13" + System.lineSeparator()
            + "r,apple,10" + System.lineSeparator()
            + "p,apple,20" + System.lineSeparator()
            + "p,banana,5" + System.lineSeparator()
            + "s,banana,10";
    private final String dataFromFileOneLine = "type,fruit,quantity"
            + System.lineSeparator() + "b,banana,20";
    private FruitParser fruitParser;

    @Before
    public void setUp() {
        fruitParser = new FruitParserImpl();
    }

    @Test
    public void fruitTransaction_Ok() {
        List<FruitTransaction> actual = fruitParser.parseData(dataFromFile);
        assertEquals(8, actual.size());
        assertEquals(FruitTransaction.class, actual.get(0).getClass());
    }

    @Test
    public void fruitTransactionTypes_Ok() {
        FruitTransaction actual = fruitParser.parseData(dataFromFileOneLine).get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, actual.getOperation());
        assertEquals("banana", actual.getFruit());
        assertEquals(20, actual.getQuantity());
    }
}
