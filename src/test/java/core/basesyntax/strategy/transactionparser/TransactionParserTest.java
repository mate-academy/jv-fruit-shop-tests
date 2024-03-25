package core.basesyntax.strategy.transactionparser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionParserTest {
    private static final Map<String, Integer> VALID_DATA = new TreeMap<>(Map.of(
            "banana", 152,
            "apple", 90
    ));

    private static final List<String> EXPECTED =
            List.of("fruit,quantity", "apple,90", "banana,152");

    private TransactionParser transactionParser;
    private List<String> actual;

    @BeforeEach
    void setUp() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    public void transactionParser_ValidData_Ok() {
        actual = transactionParser.generateReport(VALID_DATA);
        assertEquals(EXPECTED, actual);
    }

    @Test
    public void transactionParser_InvalidData_Null() {
        assertThrows(NullPointerException.class, () -> transactionParser.generateReport(null));
    }

    @Test
    public void transactionParser_InvalidData_Empty() {
        Map<String, Integer> invalidData = Map.of();
        List<String> expected = List.of("fruit,quantity");

        actual = transactionParser.generateReport(invalidData);

        assertEquals(expected, actual);
    }

    @Test
    public void transactionParser_InvalidData_NegativeQuantity() {
        Map<String, Integer> invalidData = Map.of("banana", 152, "apple", -90);
        List<String> expected = List.of("fruit,quantity", "banana,152");

        actual = transactionParser.generateReport(invalidData);

        assertEquals(expected, actual);
    }
}
