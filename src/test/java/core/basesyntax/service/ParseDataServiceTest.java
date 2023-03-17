package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParseDataServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseDataServiceTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final String TITLE = "type,fruit,quantity";
    private static final String FIRST_LINE = "b,banana,200";
    private static final String SECOND_LINE = "p,apple,100";
    private static final String THIRD_LINE = "s,banana,50";
    private static final String FOUR_LINE = "r,apple,100";
    private static final String NOT_LIQUID_LINE = "o,banana,200";
    private static ParseDataService parseDataService;
    private static List<FruitTransaction> expected;
    private static List<String> input;

    @BeforeClass
    public static void beforeAll() {
        parseDataService = new ParseDataServiceImpl();
        expected = new ArrayList<>();
        expected.add(creteTransaction(FruitTransaction.Operation.BALANCE, BANANA, 200));
        expected.add(creteTransaction(FruitTransaction.Operation.PURCHASE, APPLE, 100));
        expected.add(creteTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 50));
        expected.add(creteTransaction(FruitTransaction.Operation.RETURN, APPLE, 100));
        input = new ArrayList<>();
    }

    @Test
    public void parseDataService_parseData_Ok() {
        input.clear();
        input.add(TITLE);
        input.add(FIRST_LINE);
        input.add(SECOND_LINE);
        input.add(THIRD_LINE);
        input.add(FOUR_LINE);
        List<FruitTransaction> actual = parseDataService.parseData(input);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseDataService_inputNullData_NotOk() {
        parseDataService.parseData(null);
    }

    @Test(expected = RuntimeException.class)
    public void parseDataService_inputInvalidData_NotOk() {
        input.clear();
        input.add(TITLE);
        input.add(NOT_LIQUID_LINE);
        parseDataService.parseData(input);
    }

    private static FruitTransaction creteTransaction(FruitTransaction.Operation operation,
                                                     String fruitName,
                                                     int quantity) {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(operation);
        fruitTransaction.setFruit(fruitName);
        fruitTransaction.setQuantity(quantity);
        return fruitTransaction;
    }
}
