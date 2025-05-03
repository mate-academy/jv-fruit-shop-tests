package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.impl.ProcessDataImpl;
import core.basesyntax.service.ProcessData;
import core.basesyntax.transactor.FruitTransaction;
import core.basesyntax.transactor.Operation;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProcessDataImplTest {
    private static final String VALID_INPUT_DATA = "type,fruit,quantity"
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
        List<String> inputLines = List.of(VALID_INPUT_DATA.split(System.lineSeparator()));
        List<FruitTransaction> actual = processData.process(inputLines);
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(Operation.BALANCE, "apple", 2),
                new FruitTransaction(Operation.SUPPLY, "apple", 3),
                new FruitTransaction(Operation.PURCHASE, "apple", 4)
        );
        assertEquals(expected, actual);
    }

    @Test
    void process_invalidFormat_lessThanThreeValues_throwsException() {
        List<String> inputLines = List.of("type,fruit,quantity", "b,apple");
        RuntimeException runtimeException = assertThrows(IllegalArgumentException.class,
                () -> processData.process(inputLines));
        assertEquals("Invalid input data format: b,apple", runtimeException.getMessage());
    }

    @Test
    void process_invalidFormat_moreThanThreeValues_throwsException() {
        List<String> inputLines = List.of("type,fruit,quantity", "b,apple,10,extra");
        RuntimeException runtimeException = assertThrows(IllegalArgumentException.class,
                () -> processData.process(inputLines));
        assertEquals("Invalid input data format: b,apple,10,extra", runtimeException.getMessage());
    }

    @Test
    void process_invalidQuantity_notANumber_throwsException() {
        List<String> inputLines = List.of("type,fruit,quantity", "b,apple,ten");
        RuntimeException runtimeException = assertThrows(IllegalArgumentException.class,
                () -> processData.process(inputLines));
        assertEquals("Quantity must be a valid number: ten", runtimeException.getMessage());
    }

    @Test
    void process_invalidQuantity_negativeNumber_throwsException() {
        List<String> inputLines = List.of("type,fruit,quantity", "b,apple,-5");
        RuntimeException runtimeException = assertThrows(IllegalArgumentException.class,
                () -> processData.process(inputLines));
        assertEquals("Quantity cannot be negative: -5", runtimeException.getMessage());
    }

    @Test
    void process_emptyLine_throwsException() {
        List<String> inputLines = List.of("type,fruit,quantity", "");
        RuntimeException runtimeException = assertThrows(IllegalArgumentException.class,
                () -> processData.process(inputLines));
        assertEquals("The entrance row cannot be empty", runtimeException.getMessage());
    }

    @Test
    void process_missingValues_emptyOperation_throwsException() {
        List<String> inputLines = List.of("type,fruit,quantity", ",apple,10");
        RuntimeException runtimeException = assertThrows(IllegalArgumentException.class,
                () -> processData.process(inputLines));
        assertEquals("Data contains empty values: ,apple,10", runtimeException.getMessage());
    }

    @Test
    void process_missingValues_emptyQuantity_throwsException() {
        List<String> inputLines = List.of("type,fruit,quantity", "b,apple,");
        RuntimeException runtimeException = assertThrows(IllegalArgumentException.class,
                () -> processData.process(inputLines));
        assertEquals("Invalid input data format: b,apple,", runtimeException.getMessage());
    }
}
