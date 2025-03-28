package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransactions_validData_ok() {
        List<String> input = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,100"
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransactions(input);
        assertEquals(2, transactions.size());

        FruitTransaction expectedFirst =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20);
        assertEquals(expectedFirst, transactions.get(0));

        FruitTransaction expectedSecond =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 100);
        assertEquals(expectedSecond, transactions.get(1));
    }

    @Test
    void convertToTransactions_invalidFormat_throwsException() {
        List<String> input = Arrays.asList(
                "type,fruit,quantity",
                "b,banana"
        );
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransactions(input));
    }

    @Test
    void convertToTransactions_invalidQuantity_throwsException() {
        List<String> input = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,-20"
        );
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransactions(input));
    }

    @Test
    void convertToTransactions_nullInput_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransactions(null));
    }

    @Test
    void convertToTransactions_emptyInput_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransactions(Arrays.asList()));
    }

    @Test
    void convertToTransactions_invalidOperationCode_throwsException() {
        List<String> input = Arrays.asList(
                "type,fruit,quantity",
                "x,banana,20"
        );
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransactions(input));
    }

    @Test
    void convertToTransactions_nonNumericQuantity_throwsException() {
        List<String> input = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,twenty"
        );
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransactions(input));
    }
}