package core.basesyntax;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.ParseService;
import core.basesyntax.service.impl.ParseServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParseServiceTest {
    private static final String WRONG_ROW_EMPTY_ROW = "";
    private static final String WRONG_ROW_EXTRA_PART = "b,apple,123,banana";
    private static final String WRONG_ROW_NEGATIVE_AMOUNT = "b,apple,-123";
    private static final String WRONG_ROW_WRONG_AMOUNT_TYPE = "b,apple,wer";
    private static final String WRONG_ROW_EMPTY_NAME_TYPE = "b,,wer";
    private static final String WRONG_ROW_WRONG_OPERATION = "m,banana,12";
    private static final String CORRECT_ROW_BALANCE = "b,apple,100";
    private static final String CORRECT_ROW_RETURN = "r,apple,100";
    private static final String CORRECT_ROW_SUPPLY = "s,apple,100";
    private static final String CORRECT_ROW_PURCHASE = "p,apple,100";
    private static ParseService parseService;

    @BeforeAll
    public static void initialize() {
        parseService = new ParseServiceImpl();
    }

    @Test
    public void parsingCorrectRowOperationBalance_Ok() {
        FruitRecord actual = parseService.getParsedLine(CORRECT_ROW_BALANCE);
        FruitRecord expected = new FruitRecord(FruitRecord.Operation.BALANCE, new Fruit("apple"), 100);
        assertEquals(actual, expected);
    }

    @Test
    public void parsingCorrectRowOperationReturn_Ok() {
        FruitRecord actual = parseService.getParsedLine(CORRECT_ROW_RETURN);
        FruitRecord expected = new FruitRecord(FruitRecord.Operation.RETURN, new Fruit("apple"), 100);
        assertEquals(actual, expected);
    }

    @Test
    public void parsingCorrectRowOperationSupply_Ok() {
        FruitRecord actual = parseService.getParsedLine(CORRECT_ROW_SUPPLY);
        FruitRecord expected = new FruitRecord(FruitRecord.Operation.SUPPLY, new Fruit("apple"), 100);
        assertEquals(actual, expected);
    }

    @Test
    public void parsingCorrectRowOperationPurchase_Ok() {
        FruitRecord actual = parseService.getParsedLine(CORRECT_ROW_PURCHASE);
        FruitRecord expected = new FruitRecord(FruitRecord.Operation.PURCHASE, new Fruit("apple"), 100);
        assertEquals(actual, expected);
    }

    @Test
    public void parsingEmptyRow_notOk() {
        assertThrows(RuntimeException.class, () -> parseService.getParsedLine(WRONG_ROW_EMPTY_ROW));
    }

    @Test
    public void parsingRowWithWrongNumberOfParts_notOk() {
        assertThrows(RuntimeException.class, () -> parseService.getParsedLine(WRONG_ROW_EXTRA_PART));
    }

    @Test
    public void parsingRowWithNegativeAmountNumber_notOk() {
        assertThrows(RuntimeException.class, () -> parseService.getParsedLine(WRONG_ROW_NEGATIVE_AMOUNT));
    }

    @Test
    public void parsingRowWithStringInsteadOfAmountNumber_notOk() {
        assertThrows(RuntimeException.class, () -> parseService.getParsedLine(WRONG_ROW_WRONG_AMOUNT_TYPE));
    }

    @Test
    public void parsingRowWithEmptyName_notOk() {
        assertThrows(RuntimeException.class, () -> parseService.getParsedLine(WRONG_ROW_EMPTY_NAME_TYPE));
    }

    @Test
    public void parsingRowWithWrongOperation_notOk() {
        assertThrows(RuntimeException.class, () -> parseService.getParsedLine(WRONG_ROW_WRONG_OPERATION));
    }
}
