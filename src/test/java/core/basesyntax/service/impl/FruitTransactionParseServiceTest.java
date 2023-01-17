package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParseServiceTest {
    private static final String VALID_DATA = "b,banana,50";
    private static final String INVALID_DATA = "b,banana,-50";
    private static final String WRONG_DELIMITER_DATA = "b banana 50";
    private static ParseService<FruitTransaction> parseService;

    @BeforeClass
    public static void init() {
        parseService = new FruitTransactionParseService();
    }

    @Test
    public void parse_isOk() {
        String fruit = "banana";
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        int quantity = 50;
        FruitTransaction expected = new FruitTransaction();
        expected.setOperation(operation);
        expected.setFruit(fruit);
        expected.setQuantity(quantity);
        FruitTransaction actual = parseService.parse(VALID_DATA);
        assertEquals(operation, actual.getOperation());
        assertEquals(fruit, actual.getFruit());
        assertEquals(quantity, actual.getQuantity());
    }

    @Test (expected = RuntimeException.class)
    public void parse_invalidData_notOk() {
        parseService.parse(INVALID_DATA);
    }

    @Test (expected = NullPointerException.class)
    public void parse_nullData_notOk() {
        parseService.parse(null);
    }

    @Test (expected = RuntimeException.class)
    public void parse_wrongDelimiterData_notOk() {
        parseService.parse(WRONG_DELIMITER_DATA);
    }
}
