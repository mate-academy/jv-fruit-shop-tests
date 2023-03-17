package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final FruitTransaction.Operation BALANCE
            = FruitTransaction.Operation.BALANCE;
    private static final FruitTransaction.Operation SUPPLY
            = FruitTransaction.Operation.SUPPLY;
    private static final FruitTransaction.Operation PURCHASE
            = FruitTransaction.Operation.PURCHASE;
    private static final FruitTransaction.Operation RETURN
            = FruitTransaction.Operation.RETURN;
    private static final List<String> VALID_DATA = List.of("type,fruit,quantity", "b,banana,20",
            "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
            "p,apple,20", "p,banana,5", "s,banana,50");
    private static final List<String> EMPTY_DATA = List.of();
    private static final List<String> INVALID_DATA = List.of("cross across, math",
            "chemical, addiction, good, boy", "approximately, beautiful, bananas, apples, fruits");
    private static ParserService parserService;
    private List<FruitTransaction> validDataTransactions;
    private List<FruitTransaction> emptyDataTransactions;

    @BeforeClass
    public static void beforeClass() {
        parserService = new ParserServiceImpl();
    }

    @Before
    public void setUp() {
        validDataTransactions = List.of(new FruitTransaction(BALANCE, BANANA, 20),
                new FruitTransaction(BALANCE, APPLE, 100),
                new FruitTransaction(SUPPLY, BANANA, 100),
                new FruitTransaction(PURCHASE, BANANA, 13),
                new FruitTransaction(RETURN, APPLE, 10),
                new FruitTransaction(PURCHASE, APPLE, 20),
                new FruitTransaction(PURCHASE, BANANA, 5),
                new FruitTransaction(SUPPLY, BANANA, 50));
        emptyDataTransactions = Collections.emptyList();
    }

    @Test
    public void parseData_inputDataIsValid_ok() {
        List<FruitTransaction> expected = validDataTransactions;
        List<FruitTransaction> actual = parserService.parseData(VALID_DATA);
        assertEquals(expected, actual);
    }

    @Test
    public void parseData_emptyData_ok() {
        List<FruitTransaction> expected = emptyDataTransactions;
        List<FruitTransaction> actual = parserService.parseData(EMPTY_DATA);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseData_invalidData_notOk() {
        parserService.parseData(INVALID_DATA);
        fail("You must throw Runtime Exception, if the data from the file is invalid");
    }

    @Test(expected = RuntimeException.class)
    public void parseData_dataIsNull_notOk() {
        parserService.parseData(null);
        fail("You must throw Runtime Exception, if the data from the file is null");
    }
}
