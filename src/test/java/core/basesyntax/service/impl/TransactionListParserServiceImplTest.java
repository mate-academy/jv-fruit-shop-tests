package core.basesyntax.service.impl;

import static core.basesyntax.model.Product.APPLE;
import static core.basesyntax.model.Product.BANANA;
import static core.basesyntax.strategy.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.strategy.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.strategy.FruitTransaction.Operation.RETURN;
import static core.basesyntax.strategy.FruitTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.TransactionListParserService;
import core.basesyntax.strategy.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ParserImpl Test")
class TransactionListParserServiceImplTest {
    private static TransactionListParserService service;

    @BeforeAll
    static void beforeAll() {
        service = new TransactionListParserServiceImpl();
    }

    @DisplayName("Check correct input data - many lines")
    @Test
    void parse_validInput_ok() {
        List<String> inputList = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<FruitTransaction> expectedList = List.of(
                new FruitTransaction(BALANCE, BANANA, 20),
                new FruitTransaction(BALANCE, APPLE, 100),
                new FruitTransaction(SUPPLY, BANANA, 100),
                new FruitTransaction(PURCHASE, BANANA, 13),
                new FruitTransaction(RETURN, APPLE, 10),
                new FruitTransaction(PURCHASE, APPLE, 20),
                new FruitTransaction(PURCHASE, BANANA, 5),
                new FruitTransaction(SUPPLY, BANANA, 50));
        assertEquals(expectedList, service.parse(inputList));
    }

    @DisplayName("Check correct input data - only titles")
    @Test
    void parse_onlyTitleLineInput_ok() {
        List<String> inputList = List.of("type,fruit,quantity");
        assertTrue(service.parse(inputList).isEmpty());
    }

    @DisplayName("Check correct input data - one data line")
    @Test
    void parse_oneLineInput_ok() {
        List<String> inputList = List.of("type,fruit,quantity", "b,banana,20");
        List<FruitTransaction> expectedList =
                List.of(new FruitTransaction(BALANCE, BANANA, 20));
        assertEquals(expectedList, service.parse(inputList));
    }

    @DisplayName("Check correct input data - empty list")
    @Test
    void parse_emptyLineInput_ok() {
        List<String> inputList = new ArrayList<>();
        assertTrue(service.parse(inputList).isEmpty());
    }

    @DisplayName("Check incorrect input data - null-pointer")
    @Test
    void parse_nullInput_notOk() {
        assertThrows(RuntimeException.class, () -> service.parse(null));
    }

    @DisplayName("Check incorrect input data - bad line's format")
    @Test
    void parse_invalidStringInputFormat_notOk() {
        List<String> inputList = List.of("type,fruit", "b,banana");
        assertThrows(RuntimeException.class, () -> service.parse(inputList));
    }
}
