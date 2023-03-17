package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final Optional<FruitTransaction.Operation> BALANCE
            = Optional.ofNullable(FruitTransaction.Operation.BALANCE);
    private static final Optional<FruitTransaction.Operation> SUPPLY
            = Optional.ofNullable(FruitTransaction.Operation.SUPPLY);
    private static final Optional<FruitTransaction.Operation> PURCHASE
            = Optional.ofNullable(FruitTransaction.Operation.PURCHASE);
    private static final Optional<FruitTransaction.Operation> RETURN
            = Optional.ofNullable(FruitTransaction.Operation.RETURN);
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
        validDataTransactions = List.of(new FruitTransaction(BALANCE.get(), BANANA, 20),
                new FruitTransaction(BALANCE.get(), APPLE, 100),
                new FruitTransaction(SUPPLY.get(), BANANA, 100),
                new FruitTransaction(PURCHASE.get(), BANANA, 13),
                new FruitTransaction(RETURN.get(), APPLE, 10),
                new FruitTransaction(PURCHASE.get(), APPLE, 20),
                new FruitTransaction(PURCHASE.get(), BANANA, 5),
                new FruitTransaction(SUPPLY.get(), BANANA, 50));
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
