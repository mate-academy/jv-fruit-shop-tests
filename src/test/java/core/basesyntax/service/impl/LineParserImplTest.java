package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitTransaction;
import core.basesyntax.service.LineParser;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LineParserImplTest {

    private static final List<FruitTransaction> EXPECTED_TRANSACTIONS = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
            new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
    private LineParser lineParser;

    @BeforeEach
    void setUp() {
        lineParser = new LineParserImpl();
    }

    @Test
    void createListOfTransactions_validLines_ok() {
        List<String> validLines = List.of("type.fruit.quantity",
                "b.banana.20",
                "b.apple.100",
                "s.banana.100",
                "p.banana.13",
                "r.apple.10",
                "p.apple.20",
                "p.banana.5",
                "s.banana.50");
        List<FruitTransaction> actualTransactions = lineParser
                .createListOfTransactions(validLines);
        assertEquals(EXPECTED_TRANSACTIONS, actualTransactions);
    }

    @Test
    void createListOfTransactions_invalidLines_notOk() {
        List<String> invalidLines = List.of("type.fruit.quantity",
                "b.banana.20",
                "b.apple.100",
                "s.banana.r",
                "p.banana.13",
                "r.apple.10",
                "p.apple.-20",
                "p.banana.5",
                "s.banana.50",
                "x.orange.30");
        assertThrows(RuntimeException.class, () -> lineParser
                .createListOfTransactions(invalidLines));
    }

    @Test
    void createListOfTransactions_emptyLines_ok() {
        List<String> emptyLines = List.of();
        List<FruitTransaction> actualTransactions = lineParser
                .createListOfTransactions(emptyLines);
        assertEquals(List.of(), actualTransactions);
    }

    @Test
    void createListOfTransactions_unknownOperation_notOk() {
        List<String> unknownOperationLines = List.of("type.fruit.quantity",
                "b.banana.20",
                "u.apple.100");
        assertThrows(RuntimeException.class, () -> lineParser
                .createListOfTransactions(unknownOperationLines));
    }

    @Test
    void createListOfTransactions_invalidFormat_notOk() {
        List<String> invalidFormatLines = List.of("type.fruit.quantity",
                "b.banana.20",
                "s.apple.error");
        assertThrows(RuntimeException.class, () -> lineParser
                .createListOfTransactions(invalidFormatLines));
    }

    @Test
    void createListOfTransactions_emptyLine_ok() {
        List<String> emptyLine = List.of("");
        List<FruitTransaction> actualTransactions = lineParser
                .createListOfTransactions(emptyLine);
        assertEquals(List.of(), actualTransactions);
    }
}
