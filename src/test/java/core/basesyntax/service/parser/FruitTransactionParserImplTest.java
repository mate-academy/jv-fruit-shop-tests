package core.basesyntax.service.parser;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private static final List<String> EMPTY_LINES_LIST = new ArrayList<>();
    private FruitTransactionParser fruitTransactionParser = new FruitTransactionParserImpl();
    private List<FruitTransaction> expected = new ArrayList<>();

    @Test
    public void parseNullList_notOk() {
        try {
            List<FruitTransaction> actualParse = fruitTransactionParser.parse(null);
        } catch (RuntimeException e) {
            return;
        }
        fail("test should fail -> list is null ");
    }

    @Test
    public void parseEmptyList_Ok() {
        List<FruitTransaction> actualParse = fruitTransactionParser.parse(EMPTY_LINES_LIST);
        assertEquals(expected, actualParse);
    }

    @Test
    public void parseLinesToFruit_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        List<String> lines = new ArrayList<>();
        lines.add("b,banana,20");
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        expected.add(fruitTransaction);
        List<FruitTransaction> actualParse = fruitTransactionParser.parse(lines);
        assertEquals(expected, actualParse);
    }
}
