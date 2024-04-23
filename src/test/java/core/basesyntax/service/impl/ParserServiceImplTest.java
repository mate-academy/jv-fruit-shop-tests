package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.exception.InvalidOperationCodeException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ParserServiceImplTest {
    private static final ParserService parserService = new ParserServiceImpl();

    @BeforeAll
    static void beforeAll() {
    }

    @Test
    void parse_ok() {
        List<String> inputData = List.of("type,fruit,quantity",
                "b,banana,20",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10");
        List<FruitTransaction> expectedFruitTransactions = List.of(
                new FruitTransaction(BALANCE, "banana", 20),
                new FruitTransaction(SUPPLY, "banana", 100),
                new FruitTransaction(PURCHASE, "banana", 13),
                new FruitTransaction(RETURN, "apple", 10));
        List<FruitTransaction> actualTransactions = parserService.parse(inputData);
        assertEquals(expectedFruitTransactions, actualTransactions);
    }

    @Test
    void parse_invalidCodeTransaction_notOk() {
        List<String> inputData = List.of("type,fruit,quantity",
                "y,banana,20");
        assertThrows(InvalidOperationCodeException.class,
                () -> parserService.parse(inputData));
    }

    @Test
    void parse_nullData_notOk() {
        assertThrows(NullPointerException.class,
                () -> parserService.parse(null));
    }
}
