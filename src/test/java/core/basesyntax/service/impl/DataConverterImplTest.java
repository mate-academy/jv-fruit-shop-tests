package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    @Test
    void convert_validLine_ok() {
        DataConverterImpl converter = new DataConverterImpl();
        List<FruitTransaction> result = converter.convertToTransaction(
                List.of("b,apple,10", "s,banana,5"));

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("apple", result.get(0).getFruit());
    }

    @Test
    void convert_headerSkipped_ok() {
        DataConverterImpl converter = new DataConverterImpl();
        List<FruitTransaction> result = converter.convertToTransaction(
                List.of("type,fruit,quantity", "b,apple,10"));

        Assertions.assertEquals(1, result.size());
    }

    @Test
    void convert_invalidQuantity_notOk() {
        DataConverterImpl converter = new DataConverterImpl();
        Assertions.assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(List.of("b,apple,abc")));
    }

    @Test
    void convert_negativeQuantity_notOk() {
        DataConverterImpl converter = new DataConverterImpl();
        Assertions.assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(List.of("b,apple,-5")));
    }
}
