package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.parser.TransactionParser;
import core.basesyntax.service.parser.TransactionParserImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TransactionParserTest {

    private final TransactionParser parser = new TransactionParserImpl();

    @Test
    void parse_validTransactions_success() {
        List<String> lines = List.of("b,apple,100", "p,apple,20");
        List<FruitTransaction> result = parser.parse(lines);

        assertEquals(2, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("apple", result.get(0).getFruit());
        assertEquals(100, result.get(0).getQuantity());
    }

    @Test
    void parse_emptyInput_returnsEmptyList() {
        List<FruitTransaction> result = parser.parse(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    void parse_allOperationsSupported_correctParsing() {
        List<String> lines = List.of("b,apple,100", "s,apple,50", "p,apple,20", "r,apple,10");

        List<FruitTransaction> result = parser.parse(lines);

        assertEquals(4, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals(FruitTransaction.Operation.SUPPLY, result.get(1).getOperation());
        assertEquals(FruitTransaction.Operation.PURCHASE, result.get(2).getOperation());
        assertEquals(FruitTransaction.Operation.RETURN, result.get(3).getOperation());
    }

    @Test
    void parse_negativeQuantity_throwsException() {
        List<String> lines = List.of("p,apple,-10");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parse(lines);
        });
        assertTrue(exception.getMessage().contains("Invalid line format"));
    }

    @Test
    void parse_zeroQuantity_validAccordingToRegex_butCheckLogical() {
        List<String> lines = List.of("p,apple,0");
        List<FruitTransaction> result = parser.parse(lines);
        assertEquals(1, result.size());
        assertEquals(0, result.get(0).getQuantity());
    }

    @Test
    void parse_upperCaseFruitName_throwsException() {
        List<String> lines = List.of("b,Apple,10");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parse(lines);
        });
        assertTrue(exception.getMessage().contains("Invalid line format"));
    }

    @Test
    void parse_longFruitName_success() {
        List<String> lines = List.of("s,superlongfruitname,50");
        List<FruitTransaction> result = parser.parse(lines);
        assertEquals("superlongfruitname", result.get(0).getFruit());
        assertEquals(50, result.get(0).getQuantity());
    }

    @Test
    void parse_invalidFormat_throwsException() {
        List<String> lines = List.of("b-apple-100");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parse(lines);
        });
        assertTrue(exception.getMessage().contains("Invalid line format"));
    }

    @Test
    void parse_lineWithExtraSpaces_throwsException() {
        List<String> lines = List.of(" b , apple , 100 "); // пробіли між елементами не дозволені
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parse(lines);
        });
        assertTrue(exception.getMessage().contains("Invalid line format"));
    }

    @Test
    void parse_unknownOperation_throwsException() {
        List<String> lines = List.of("x,apple,10");
        assertThrows(IllegalArgumentException.class, () -> {
            parser.parse(lines);
        });
    }

    @Test
    void parse_emptyLine_throwsException() {
        List<String> lines = List.of("");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parse(lines);
        });
        assertTrue(exception.getMessage().contains("Invalid line format"));
    }
}
