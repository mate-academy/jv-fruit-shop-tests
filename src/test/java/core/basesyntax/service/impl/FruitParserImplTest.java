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
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        fruitParser = new FruitParserImpl();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(20);
    }

    @Test
    public void parseData_validOutput_ok() {
        List<FruitTransaction> actual = fruitParser.parseData(dataFromFile);
        assertEquals("Invalid count of parsed lines",8, actual.size());
        assertEquals("Invalid class type!", FruitTransaction.class,
                actual.get(0).getClass());
    }

    @Test
    public void createFruitTransaction_validOutput_ok() {
        FruitTransaction actual = fruitParser.parseData(dataFromFileOneLine).get(0);
        assertEquals(fruitTransaction, actual);
    }
}
