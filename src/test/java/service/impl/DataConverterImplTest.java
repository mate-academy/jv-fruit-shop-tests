package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import model.FruitTransaction;
import model.Operation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.DataConverter;

class DataConverterImplTest {
    private static DataConverter dataConverter;

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_nullList_notOk() {
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(null));
    }

    @Test
    void convertToTransaction_incorrectDataLine_notOk() {
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(
                List.of("type,fruit,quantity", "b,banana20")));
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(
                List.of("type,fruit,quantity", "b,banana,20 b,apple,100")));
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(
                List.of("type,fruit,quantity", "")));
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(
                List.of("type,fruit,quantity", "b;banana;20")));
    }

    @Test
    void convertToTransaction_incorrectStringOperation_notOk() {
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(
                List.of("type,fruit,quantity", "y,banana,20")));
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(
                List.of("type,fruit,quantity", "bb,banana,20")));
    }

    @Test
    void convertToTransaction_incorrectQuantityFormat_notOk() {
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(
                List.of("type,fruit,quantity", "s,banana,20L")));
        assertThrows(IllegalArgumentException.class, () -> dataConverter.convertToTransaction(
                List.of("type,fruit,quantity", "b,banana,20.00")));
    }

    @Test
    void convertToTransaction_Ok() {
        List<FruitTransaction> expectedList = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.SUPPLY, "banana", 100),
                new FruitTransaction(Operation.PURCHASE, "banana", 15),
                new FruitTransaction(Operation.RETURN, "apple", 10)
        );
        List<String> inputLines = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,banana,100",
                "p,banana,15",
                "r,apple,10"
        );
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i).getOperation(),
                    dataConverter.convertToTransaction(inputLines).get(i).getOperation());
            assertEquals(expectedList.get(i).getFruit(),
                    dataConverter.convertToTransaction(inputLines).get(i).getFruit());
            assertEquals(expectedList.get(i).getQuantity(),
                    dataConverter.convertToTransaction(inputLines).get(i).getQuantity());

        }
    }
}
