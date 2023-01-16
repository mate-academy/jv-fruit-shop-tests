package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParseServiceTest {
    private static final String VALID_DATA = "b,banana,50";
    private static final String INVALID_DATA = "b,banana,-50";
    private static final String NO_DELIMITER_DATA = "b banana 50";
    private static final String FRUIT = "banana";
    private static final FruitTransaction.Operation OPERATION = FruitTransaction.Operation.BALANCE;
    private static final int QUANTITY = 50;
    private static ParseService<FruitTransaction> parseService;

    @BeforeClass
    public static void beforeClass() {
        parseService = new FruitTransactionParseService();
    }

    @Test
    public void parse_isOk() {
        FruitTransaction expected = new FruitTransaction();
        expected.setOperation(FruitTransaction.Operation.BALANCE);
        expected.setFruit("banana");
        expected.setQuantity(50);
        FruitTransaction actual = parseService.parse(VALID_DATA);
        assertEquals(OPERATION, actual.getOperation());
        assertEquals(FRUIT, actual.getFruit());
        assertEquals(QUANTITY, actual.getQuantity());
    }

    @Test (expected = RuntimeException.class)
    public void parse_invalidData_notOk() {
        parseService.parse(INVALID_DATA);
    }

    @Test (expected = RuntimeException.class)
    public void parse_emptyData_notOk() {
        parseService.parse(NO_DELIMITER_DATA);
    }
}
