package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.model.FruitTransaction;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseServiceImplTest {
    private static final String WRONG_OPERATION_TRANSACTION = "dfg,kiwi,123";
    private static final String WRIGHT_TRANSACTION = "b,apple,123";
    private static ParseServiceImpl parseService;
    private final FruitTransaction expectedFruitTransaction =
            new FruitTransaction(FruitTransaction.TypeOperation.BALANCE, "apple", 123);

    @BeforeClass
    public static void initParser() {
        parseService = new ParseServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void parse_stringIsNull_NotOK() {
        parseService.parse(null);
    }

    @Test (expected = RuntimeException.class)
    public void parse_wrongOperation_NotOK() {
        parseService.parse(WRONG_OPERATION_TRANSACTION);
    }

    @Test
    public void parse_rightTransaction_OK() {
        FruitTransaction fruitTransaction = parseService.parse(WRIGHT_TRANSACTION);
        assertEquals(expectedFruitTransaction, fruitTransaction);
    }
}
