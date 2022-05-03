package core.basesyntax.service.parser;

import static junit.framework.TestCase.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private FruitTransactionParser fruitTransactionParser;
    private List<FruitTransaction> expectedValid;

    @Before
    public void setUp() throws Exception {
        fruitTransactionParser = new FruitTransactionParserImpl();
        expectedValid = new ArrayList<>();
    }

    @Test(expected = RuntimeException.class)
    public void parse_nullList_notOk() {
        List<FruitTransaction> fruitTransactions = fruitTransactionParser.parse(null);
    }

    @Test
    public void parse_emptyList_Ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        List<String> emptyList = new ArrayList<>();
        List<FruitTransaction> actual = fruitTransactionParser.parse(emptyList);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_validList_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        List<String> lines = new ArrayList<>();
        lines.add("b,banana,20");
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        expectedValid.add(fruitTransaction);
        List<FruitTransaction> actualParse = fruitTransactionParser.parse(lines);
        assertEquals(expectedValid, actualParse);
    }
}
