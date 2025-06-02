package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.parser.TransactionParserImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionParserImplTest {

    private TransactionParserImpl parser;

    @BeforeEach
    void setUp() {
        parser = new TransactionParserImpl();
    }

    @Test
    void parse_shouldParseValidLines() {
        List<String> lines = List.of("b,banana,10", "s,apple,20");
        List<FruitTransaction> result = parser.parse(lines);
        assertEquals(2, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(10, result.get(0).getQuantity());
    }

    @Test
    void parse_shouldThrowIllegalArgumentException_whenLineFormatInvalid() {
        List<String> invalidLines = List.of(
                "banana,10",
                "b,banana",
                "b,banana,ten",
                "x,banana,10",
                "b,banana,-10",
                "b,ban@na,10",
                "b,banana,10,extra"
        );

        for (String line : invalidLines) {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> parser.parse(List.of(line)), "Expected to fail for line: " + line);
            assertTrue(ex.getMessage().contains("Invalid line format")
                    || ex.getMessage().contains("Unknown operation code"));
        }
    }

    @Test
    void parse_shouldParseValidLinesCorrectly() {
        List<String> input = List.of(
                "b,banana,10",
                "s,apple,20",
                "p,orange,5",
                "r,grape,3"
        );

        List<FruitTransaction> result = parser.parse(input);

        assertEquals(4, result.size());

        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(10, result.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY, result.get(1).getOperation());
        assertEquals("apple", result.get(1).getFruit());
        assertEquals(20, result.get(1).getQuantity());

        assertEquals(FruitTransaction.Operation.PURCHASE, result.get(2).getOperation());
        assertEquals("orange", result.get(2).getFruit());
        assertEquals(5, result.get(2).getQuantity());

        assertEquals(FruitTransaction.Operation.RETURN, result.get(3).getOperation());
        assertEquals("grape", result.get(3).getFruit());
        assertEquals(3, result.get(3).getQuantity());
    }

    @Test
    void parse_shouldTrimLinesBeforeParsing() {
        List<String> input = List.of("   b,banana,10   ");

        List<FruitTransaction> result = parser.parse(input);

        assertEquals(1, result.size());
        assertEquals("banana", result.get(0).getFruit());
    }

    @Test
    void parse_shouldThrowIllegalArgumentException_whenUnknownOperationCode() {
        List<String> input = List.of("x,banana,10"); // 'x' is not a valid operation code

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> parser.parse(input));
        assertTrue(ex.getMessage().contains("Unknown operation code"));
    }

    @Test
    void parse_shouldReturnEmptyList_whenInputIsEmpty() {
        List<FruitTransaction> result = parser.parse(List.of());
        assertTrue(result.isEmpty());
    }
}
