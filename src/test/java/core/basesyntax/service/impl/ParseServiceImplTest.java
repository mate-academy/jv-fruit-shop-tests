package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.interfaces.ParseService;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ParseServiceImplTest {
    private ParseService parseService;

    @Before
    public void setUp() {
        parseService = new ParseServiceImpl();
    }

    @Test
    public void parseTransactions_validInput_ok() {
        List<String> inputLines = Arrays.asList(
                "b,banana,20",
                "p,apple,100",
                "s,banana,50"
        );
        List<FruitTransaction> expectedTransactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        20
                ),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "apple",
                        100
                ),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        "banana",
                        50
                )
        );
        List<FruitTransaction> parsedTransactions = parseService
                .parseTransactions(inputLines);
        assertEquals(expectedTransactions, parsedTransactions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseTransactions_invalidInput_notOk() {
        List<String> inputLines = Arrays.asList(
                "b,banana",
                "p,apple,100",
                "s,banana,50"
        );
        parseService.parseTransactions(inputLines);
    }
}
