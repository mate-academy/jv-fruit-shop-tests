package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserTest {
    private static FruitTransactionParser fruitTransactionParser;
    private List<String> lines;

    @BeforeClass
    public static void beforeClass() {
        fruitTransactionParser = new FruitTransactionParserImpl();
    }

    @Before
    public void setUp() {
        lines = new ArrayList<>();
    }

    @Test
    public void parse_ValidData_Ok() {
        lines.add("type,fruit,quantity");
        lines.add("s,apple,100");
        List<FruitTransaction> fruitTransactionListActual = fruitTransactionParser.parse(lines);
        String fruitActual = fruitTransactionListActual.get(0).getFruit();
        Assert.assertEquals("apple", fruitActual);
        FruitTransaction.Operation operationActual =
                fruitTransactionListActual.get(0).getOperation();
        Assert.assertEquals(FruitTransaction.Operation.SUPPLY, operationActual);
        int quantityActual = fruitTransactionListActual.get(0).getQuantity();
        Assert.assertEquals(100, quantityActual);
        int sizeActual = fruitTransactionListActual.size();
        Assert.assertEquals(1, sizeActual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_emptyList_NotOk() {
        fruitTransactionParser.parse(lines);
    }

    @Test(expected = RuntimeException.class)
    public void parse_invalidHeader_NotOk() {
        lines.add("");
        lines.add("b,banana,20");
        fruitTransactionParser.parse(lines);
    }

    @Test(expected = RuntimeException.class)
    public void parse_NullList_NotOk() {
        lines = null;
        fruitTransactionParser.parse(lines);
    }

    @Test(expected = RuntimeException.class)
    public void parse_invalidDataInList_NotOk() {
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("b,20");
        fruitTransactionParser.parse(lines);
    }
}
