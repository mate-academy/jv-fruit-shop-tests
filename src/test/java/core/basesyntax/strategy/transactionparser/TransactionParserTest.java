package core.basesyntax.strategy.transactionparser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionParserTest {
    private static final Map<String, Integer> DATA =
            Map.of("banana", 152, "apple", 90);

    private static final List<String> EXPECT =
            List.of("fruit,quantity", "banana,152", "apple,90");

    private static final List<String> WRONG_EXPECT =
            List.of("fruit,quantity", "banana,152");

    private TransactionParser transactionParser;
    private List<String> actual;

    @BeforeEach
    void setUp() {
        transactionParser = new TransactionParserImpl();
        actual = transactionParser.generateReport(DATA);
    }

    @Test
    public void transactionParser_Ok() {
        assertEquals(EXPECT.size(), actual.size());
        for (int i = 0; i < EXPECT.size(); ++i) {
            assertEquals(EXPECT.get(i), actual.get(i));
        }
    }

    @Test
    public void transactionParser_NotOk() {
        assertNotEquals(WRONG_EXPECT.size(), actual.size());
    }
}
