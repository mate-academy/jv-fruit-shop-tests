package core.basesyntax.model.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterTest {

    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static DataConverter dataConverter;

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void incorrectStringOperation_NotOk() {
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(
                List.of("type,fruit,quantity", "y,banana,20")));
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(
                List.of("type,fruit,quantity", "bb,banana,20")));
    }

    @Test
    void incorrectQuantityFormat_NotOk() {
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(
                List.of("type,fruit,quantity", "s,banana,20L")));
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(
                List.of("type,fruit,quantity", "b,banana,20.00")));
    }

    @Test
    void convertToTransaction_Ok() {
        List<FruitTransaction> expectedList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 15),
                new FruitTransaction(FruitTransaction.Operation.RETURN, APPLE, 10)
        );
        List<String> inputLines = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,banana,100",
                "p,banana,15",
                "r,apple,10"
        );
        List<FruitTransaction> resultList = dataConverter.convertToTransaction(inputLines);
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i).getOperation(), resultList.get(i).getOperation());
            assertEquals(expectedList.get(i).getFruit(), resultList.get(i).getFruit());
            assertEquals(expectedList.get(i).getQuantity(), resultList.get(i).getQuantity());
        }
    }
}
