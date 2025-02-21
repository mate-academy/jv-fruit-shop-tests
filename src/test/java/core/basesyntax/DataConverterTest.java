package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.impl.DataConverter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterTest {

    private DataConverter dataConverter;

    @BeforeEach
    public void setUp() {
        dataConverter = new DataConverter();
    }

    @Test
    public void parseTransactions_validData_ok() {
        List<String> inputData = List.of("b,apple,100", "s,banana,150");
        List<FruitTransaction> expectedTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 150)
        );

        List<FruitTransaction> actualTransactions = dataConverter.parseTransactions(inputData);
        assertEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void parseTransactions_invalidData_throwsException() {
        List<String> inputData = List.of("invalid_data");

        assertThrows(RuntimeException.class, () -> dataConverter.parseTransactions(inputData));
    }
}
