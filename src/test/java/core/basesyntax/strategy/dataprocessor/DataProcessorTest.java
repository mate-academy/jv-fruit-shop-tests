package core.basesyntax.strategy.dataprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataProcessorTest {
    private static final String DATA =
            "type,fruit,quantity\n"
            + "b,banana,20\n"
            + "b,apple,100\n"
            + "s,banana,100\n"
            + "p,banana,13";

    private static final List<FruitTransaction> EXPECTED_CORRECT =
            List.of(new FruitTransaction(Operation.BALANCE, "banana", 20),
                    new FruitTransaction(Operation.BALANCE, "apple", 100),
                    new FruitTransaction(Operation.SUPPLY, "banana", 100),
                    new FruitTransaction(Operation.PURCHASE, "banana", 13)
            );

    private static final List<FruitTransaction> EXPECTED_INCORRECT =
            List.of(new FruitTransaction(Operation.BALANCE, "banana", 20),
                    new FruitTransaction(Operation.BALANCE, "apple", 100),
                    new FruitTransaction(Operation.SUPPLY, "banana", 100)
            );

    private DataProcessor dataProcessor;
    private List<FruitTransaction> actualTransactions;

    @BeforeEach
    void setUp() {
        dataProcessor = new DataProcessorImpl();
        actualTransactions = dataProcessor.parseTransactions(DATA);
    }

    @Test
    public void dataProcessor_Correct_Ok() {
        assertEquals(EXPECTED_CORRECT.size(), actualTransactions.size());
        for (int i = 0; i < EXPECTED_CORRECT.size(); i++) {
            assertEquals(EXPECTED_CORRECT.get(i), actualTransactions.get(i));
        }
    }

    @Test
    public void dataProcessor_Incorrect_NotOk() {
        assertNotEquals(EXPECTED_INCORRECT.size(), actualTransactions.size());
    }
}
