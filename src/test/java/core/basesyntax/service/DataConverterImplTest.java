package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {

    private DataConverter converter;

    @BeforeEach
    void setUp() {
        converter = new DataConverterImpl();
    }

    @Test
    void convert_validData_ok() {
        List<String> data = Arrays.asList("type,fruit,quantity", "b,banana,20", "p,apple,5");
        List<FruitTransaction> result = converter.convertToTransaction(data);
        assertEquals(2, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(20, result.get(0).getQuantity());
    }

    @Test
    void convert_invalidColumns_exception() {
        List<String> data = Arrays.asList("type,fruit,quantity", "b,banana");
        assertThrows(RuntimeException.class, () -> converter.convertToTransaction(data));
    }

    @Test
    void convert_unknownOperation_exception() {
        List<String> data = Arrays.asList("type,fruit,quantity", "x,banana,20");
        assertThrows(RuntimeException.class, () -> converter.convertToTransaction(data));
    }
}
