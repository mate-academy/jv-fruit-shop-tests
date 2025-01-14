package core.basesyntax.converter;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private final DataConverter dataConverter = new DataConverterImpl();

    @Test
    void convertToTransaction_validData_ok() {
        List<String> input = Arrays.asList(
                "type,fruit,quantity", " b,banana,20", " p,apple,20 ");
        List<FruitTransaction> result;
        result = dataConverter.convertToTransaction(input);
        // Перевіряється, що список містить 2 елементи
        Assert.assertEquals(2, result.size());
        // Перевіряється, що фрукт першої транзакції — "apple"
        Assert.assertTrue(result.get(0).toString().contains("apple"));
        // Перевіряється, що кількість першої транзакції дорівнює 50
        Assert.assertTrue(result.get(0).toString().contains("50"));
    }

    @Test
    void convertToTransaction_invalidData_throwsException() {
        List<String> input = Arrays.asList("invalid data");
        // Перевіряється, що при виклику методу
        // з не валідними даними викидається виняток RuntimeException
        Assert.assertThrows(RuntimeException.class, () ->
                dataConverter.convertToTransaction(input));
    }

    @Test
    void convertToTransaction_emptyList_returnsEmptyList() {
        List<FruitTransaction> result = dataConverter.convertToTransaction(
                Collections.emptyList());
        Assert.assertTrue(result.isEmpty());
    }
}
