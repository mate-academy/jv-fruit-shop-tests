package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.converter.DataConverter;
import core.basesyntax.converter.DataConverterImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConverterTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validTransactions_ok() {
        List<String> inputData = List.of(
                "operation,fruit,quantity",
                "b,banana,20",
                "s,apple,50",
                "p,banana,10"
        );
        List<FruitTransaction> actualTransaction = dataConverter.convertToTransaction(inputData);
        List<FruitTransaction> expectedTransaction = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10)
        );
        assertEquals(expectedTransaction.size(), actualTransaction.size(),
                "The number of transactions does not match");
        for (int i = 0; i < expectedTransaction.size(); i++) {
            FruitTransaction expected = expectedTransaction.get(i);
            FruitTransaction actual = actualTransaction.get(i);

            assertEquals(expected.getOperation(), actual.getOperation(),
                    "Transactions do not match");
            assertEquals(expected.getFruit(), actual.getFruit(),
                    "The fruits do not match");
            assertEquals(expected.getQuantity(), actual.getQuantity(),
                    "The quantity does not match");
        }
    }

    @Test
    void convertToTransaction_testInvalidDataToConvert_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana"
        );
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(input);
        });
        assertEquals("Invalid number of fields in row: b,banana",
                exception.getMessage());
    }

    @Test
    void convertToTransaction_invalidQuantity_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "p,banana,notANumber"
        );

        Exception exception = assertThrows(NumberFormatException.class, () -> {
            dataConverter.convertToTransaction(input);
        });
        assertEquals("Invalid quantity for fruit 'banana': notANumber",
                exception.getMessage());
    }

    @Test
    void convertToTransaction_extraFields_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "p,banana,10, extraField"
        );
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(input);
        });
        assertEquals("Invalid number of fields in row: p,banana,10, extraField",
                exception.getMessage());
    }
}
