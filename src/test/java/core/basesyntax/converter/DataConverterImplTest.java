package core.basesyntax.converter;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
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
        Assertions.assertEquals(2, transactions.size());

        FruitTransaction expectedFirst =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20);
        Assertions.assertEquals(expectedFirst, transactions.get(0));

        FruitTransaction expectedSecond =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 100);
        Assertions.assertEquals(expectedSecond, transactions.get(1));
    }

    @Test
    void convert_Invalid_Format() {
        List<String> input = Arrays.asList(
                "type,fruit,quantity",
                "b,banana"
        );
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransactions(input));
    }

    @Test
    void convertNegativeQuantity() {
        List<String> input = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,-20"
        );
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransactions(input));
    }
}
