package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.Parser;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionParserTest {
    private static Parser parser;
    private List<String> fileData;
    private List<FruitTransaction> expected;

    @BeforeAll
    static void beforeAll() {
        parser = new FruitTransactionParser();
    }

    @Test
    void validData_Ok() {
        expected = List.of(new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.BALANCE, "apple", 100),
                new FruitTransaction(Operation.SUPPLY, "banana", 100),
                new FruitTransaction(Operation.PURCHASE, "banana", 13),
                new FruitTransaction(Operation.RETURN, "apple", 10),
                new FruitTransaction(Operation.PURCHASE, "apple", 20),
                new FruitTransaction(Operation.PURCHASE, "banana", 5),
                new FruitTransaction(Operation.SUPPLY, "banana", 50));
        fileData = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<FruitTransaction> actual = parser.parse(fileData);
        assertEquals(expected, actual);
    }

    @Test
    void emptyInput_NotOk() {
        fileData = List.of();
        List<FruitTransaction> actual = parser.parse(fileData);
        assertTrue(actual.isEmpty());
    }

    @Test
    void malformedInput_NotOk() {
        fileData = List.of("b,banana,20", "invalid_line", "s,apple,30");
        assertThrows(IllegalArgumentException.class, () -> parser.parse(fileData));
    }

    @Test
    void caseInsensitiveOperationCode_Ok() {
        fileData = List.of("B,banana,20", "S,apple,30");
        expected = List.of(new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.SUPPLY, "apple", 30));
        List<FruitTransaction> actual = parser.parse(fileData);
        assertEquals(expected, actual);
    }

    @Test
    void invalidQuantity_NotOk() {
        fileData = List.of("b,banana,-20", "s,apple,invalid");
        assertThrows(IllegalArgumentException.class, () -> parser.parse(fileData));
    }
}
