package core.basesyntax.converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataConverterImplTest {
    private final DataConverter dataConverter = new DataConverterImpl();

    @Test
    void convertToTransaction_validData_ok() {
        List<String> input = Arrays.asList(
                "type,fruit,quantity", " b,banana,20", " p,apple,20 ");
        List<FruitTransaction> result;
        result = dataConverter.convertToTransaction(input);
        // Перевіряється, що список містить 2 елементи
        assertEquals(2, result.size());
        // Перевіряється, що фрукт першої транзакції — "apple"
        assertTrue(result.get(0).toString().contains("apple"));
        // Перевіряється, що кількість першої транзакції дорівнює 50
        assertTrue(result.get(0).toString().contains("50"));
    }

    @Test
    void convertToTransaction_invalidData_throwsException() {
        List<String> input = Arrays.asList("invalid data");
        // Перевіряється, що при виклику методу
        // з не валідними даними викидається виняток RuntimeException
        assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(input));
    }

    @Test
    void convertToTransaction_emptyList_returnsEmptyList() {
        List<FruitTransaction> result = dataConverter.convertToTransaction(
                Collections.emptyList());
        assertTrue(result.isEmpty());
    }
}
