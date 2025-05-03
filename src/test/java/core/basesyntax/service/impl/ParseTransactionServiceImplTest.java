package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseTransactionService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ParseTransactionServiceImplTest {
    private static ParseTransactionService parser;

    @BeforeAll
    static void beforeAll() {
        parser = new ParseTransactionServiceImpl();
    }

    @Test
    void parse_CorrectData_Ok() {
        List<String> inputData = List.of("type,fruit,quantity",
                "b,banana,20",
                "r,apple,100",
                "s,banana,100",
                "p,banana,13");
        List<FruitTransaction> expectedData = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13)
                );
        List<FruitTransaction> actualData = parser.parseTransactions(inputData);
        assertEquals(expectedData, actualData);
    }

    @Test
    void parse_IncorrectData_NotOk() {
        List<String> inputData = List.of("type,fruit,quantity",
                "b,banana,b",
                "1,1,100",
                "apple,banana,100",
                "p,banana,13");
        assertThrows(IllegalArgumentException.class, () -> parser.parseTransactions(inputData));
    }

    @Test
    void parseEmptyList_Ok() {
        List<String> inputData = Collections.emptyList();
        List<FruitTransaction> actualData = parser.parseTransactions(inputData);
        assertEquals(0, actualData.size());
    }

}
