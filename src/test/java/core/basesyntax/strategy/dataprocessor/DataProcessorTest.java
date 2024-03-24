package core.basesyntax.strategy.dataprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataProcessorTest {
    private static final String VALID_DATA =
            "type,fruit,quantity\n"
                    + "b,banana,20\n"
                    + "b,apple,100\n"
                    + "s,banana,100\n"
                    + "p,banana,13";

    private static final String INVALID_DATA =
            "type,fruit,quantity\n"
                    + "b,banana,20\n"
                    + "b,apple\n"
                    + "s,banana,100\n"
                    + "p,banana,13";

    private static final List<FruitTransaction> EXPECTED_CORRECT =
            List.of(new FruitTransaction(Operation.BALANCE, "banana", 20),
                    new FruitTransaction(Operation.BALANCE, "apple", 100),
                    new FruitTransaction(Operation.SUPPLY, "banana", 100),
                    new FruitTransaction(Operation.PURCHASE, "banana", 13)
            );

    private DataProcessor dataProcessor;
    private List<FruitTransaction> actualTransactions;

    @BeforeEach
    void setUp() {
        dataProcessor = new DataProcessorImpl();
    }

    @Test
    public void dataProcessor_Correct_Ok() {
        actualTransactions = dataProcessor.parseTransactions(VALID_DATA);
        assertEquals(EXPECTED_CORRECT.size(), actualTransactions.size());
        for (int i = 0; i < EXPECTED_CORRECT.size(); i++) {
            assertEquals(EXPECTED_CORRECT.get(i), actualTransactions.get(i));
        }
    }

    @Test
    public void dataProcessor_Invalid_ThrowsException() {
        assertThrows(RuntimeException.class,
                () -> dataProcessor.parseTransactions(INVALID_DATA));
    }
}
