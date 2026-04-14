package mate.academy.fruitshop.service.impl;

import java.util.List;
import org.junit.jupiter.api.Test;
import mate.academy.fruitshop.model.FruitTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DataConverterTest {

    private final DataConverterImpl converter = new DataConverterImpl();

    @Test
    void shouldConvertCsvLineToTransaction() {
        List<String> input = List.of("b,banana,20");

        List<FruitTransaction> result = converter.convertToTransaction(input);

        assertEquals(1, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(20, result.get(0).getQuantity());
    }
}
