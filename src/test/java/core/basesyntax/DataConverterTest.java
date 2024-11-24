package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterTest {

    @Test
    void convert_emptyData_returnsEmptyList() {
        DataConverter dataConverter = new DataConverter();
        List<String> rawData = List.of();

        List<FruitTransaction> transactions = dataConverter.convert(rawData);

        assertEquals(0, transactions.size());
    }

    @Test
    void convert_validData_createsTransactions() {
        // Arrange
        DataConverter dataConverter = new DataConverter();
        List<String> rawData = List.of(
                "b,banana,100",
                "s,apple,50",
                "p,banana,30"
        );

        List<FruitTransaction> transactions = dataConverter.convert(rawData);

        assertEquals(3, transactions.size());
        String[] expectedFruits = {"banana", "apple", "banana"};
        int[] expectedQuantities = {100, 50, 30};

        for (int i = 0; i < transactions.size(); i++) {
            assertEquals(expectedFruits[i], transactions.get(i).getFruit());
            assertEquals(expectedQuantities[i], transactions.get(i).getQuantity());
        }
    }
}

