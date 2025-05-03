package core.basesyntax.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.ProcessData;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProcessDataImplTest {
    private static final String VALID_INPUT_DATA = "type,fruit,quantity"
            + System.lineSeparator()
            + "b,apple,2"
            + System.lineSeparator()
            + "s,apple,3"
            + System.lineSeparator()
            + "p,apple,4";
    private ProcessData processData;

    @BeforeEach
    void setUp() {
        processData = new ProcessDataImpl();
    }

    @Test
    void process_validData_ok() {
        List<FruitTransaction> actual = processData.process(VALID_INPUT_DATA);
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.OperationType.BALANCE, "apple", 2),
                new FruitTransaction(FruitTransaction.OperationType.SUPPLY, "apple", 3),
                new FruitTransaction(FruitTransaction.OperationType.PURCHASE, "apple", 4));
        assertEquals(expected,actual);
    }
}
