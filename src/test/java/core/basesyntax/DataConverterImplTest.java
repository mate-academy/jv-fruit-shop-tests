package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.DataConverterImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private static DataConverter converter;

    @BeforeAll
    static void beforeAll() {
        converter = new DataConverterImpl();
    }

    @Test
    void convert_notEmptyList_Ok() {
        List<String> strings = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        List<FruitTransaction> transactions = converter.convertToTransaction(strings);
        for (int i = 0; i < transactions.size(); i++) {
            assertEquals(expected.get(i), transactions.get(i));
        }
    }
}
