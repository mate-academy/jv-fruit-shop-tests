package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitTransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private static final String TITLE_LINE = "type,fruit,quantity";
    private FruitTransactionParser fruitTransactionParser;
    private List<String> data;

    @Before
    public void setUp() {
        fruitTransactionParser = new FruitTransactionParserImpl();
        data = new ArrayList<>();
        data.add(TITLE_LINE);
    }

    @Test
    public void parseList_validData_ok() {
        data.add("b,apple,100");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(Operation.BALANCE, "apple", 100));
        List<FruitTransaction> actual = fruitTransactionParser.parseList(data);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseList_invalidOperationData_notOk() {
        data.add("##,banana,20");
        fruitTransactionParser.parseList(data);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void parseList_invalidFruitData_notOk() {
        data.add("b,20");
        fruitTransactionParser.parseList(data);
    }

    @Test(expected = NumberFormatException.class)
    public void parseList_invalidQuantityData_notOk() {
        data.add("b,banana,2$");
        fruitTransactionParser.parseList(data);
    }

    @Test(expected = RuntimeException.class)
    public void parseList_negativeQuantityData_notOk() {
        data.add("b,banana,-20");
        fruitTransactionParser.parseList(data);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void parseList_nullData_notOk() {
        fruitTransactionParser.parseList(new ArrayList<>());
    }
}
