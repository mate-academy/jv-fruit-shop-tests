package services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.impl.ProcessDataImpl;
import core.basesyntax.service.ProcessData;
import core.basesyntax.transactor.FruitTransaction;
import core.basesyntax.transactor.Operation;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProcessDataImplTest {
    private static final String VALID_INPUT_DATA = "type,fruit,quantity;"
            + System.lineSeparator()
            + "b,apple,2" + System.lineSeparator()
            + "s,apple,3" + System.lineSeparator()
            + "p,apple,4";
    private ProcessData processData;

    @BeforeEach
    void setUp() {
        processData = new ProcessDataImpl();
    }

    @Test
    void process_ok() {
        List<FruitTransaction> actual =
                processData.process(VALID_INPUT_DATA);
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(Operation.BALANCE, "apple", 2),
                new FruitTransaction(Operation.SUPPLY, "apple", 3),
                new FruitTransaction(Operation.PURCHASE, "apple", 4));
        assertEquals(actual, expected);
    }
}
