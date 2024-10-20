package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {

    private final DataConverter dataConverter = new DataConverterImpl();

    @Test
    void convertToTransaction_validData_success() {
        List<String> inputData = Arrays.asList(
                "type,fruit,quantity",
                "b,apple,50",
                "s,banana,30",
                "p,orange,20",
                "r,grape,10"
        );

        List<FruitTransaction> expected = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 30),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange", 20),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "grape", 10)
        );

        List<FruitTransaction> actual = dataConverter.convertToTransaction(inputData);

        assertEquals(expected, actual, "Должен быть корректный список транзакций.");
    }
}
