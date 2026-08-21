package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private final DataConverter dataConverter = new DataConverterImpl();

    @Test
    void convert_validInput_Ok() {
        List<String> inputReport = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,10",
                "p,banana,5",
                "r,apple,3"
        );

        List<FruitTransaction> actual = dataConverter.convert(inputReport);

        assertEquals(4, actual.size());
        assertEquals(FruitTransaction.Operation.BALANCE, actual.get(0).getOperation());
        assertEquals("banana", actual.get(0).getFruit());
        assertEquals(20, actual.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY, actual.get(1).getOperation());
        assertEquals("apple", actual.get(1).getFruit());
        assertEquals(10, actual.get(1).getQuantity());
    }

    @Test
    void convert_nullInput_notOk() {
        List<String> inputReport = null;
        assertThrows(RuntimeException.class, () -> dataConverter.convert(inputReport));
    }

    @Test
    void convert_emptyInput_notOk() {
        List<String> inputReport = List.of();

        assertThrows(RuntimeException.class, () -> dataConverter.convert(inputReport));
    }

    @Test
    void convert_invalidFieldsCount_notOk() {
        List<String> inputReport = List.of(
                "type,fruit,quantity",
                "b,banana"
        );

        assertThrows(RuntimeException.class, () -> dataConverter.convert(inputReport));
    }

    @Test
    void convert_invalidOperation_notOk() {
        List<String> inputReport = List.of(
                "type,fruit,quantity",
                "a,banana,20"
        );

        assertThrows(RuntimeException.class, () -> dataConverter.convert(inputReport));
    }

    @Test
    void convert_negativeQuantity_notOk() {
        List<String> inputReport = List.of(
                "type,fruit,quantity",
                "b,banana,-20"
        );

        assertThrows(RuntimeException.class, () -> dataConverter.convert(inputReport));
    }

    @Test
    void convert_nonNumericQuantity_notOk() {
        List<String> inputReport = List.of(
                "type,fruit,quantity",
                "b,banana,hh"
        );

        assertThrows(RuntimeException.class, () -> dataConverter.convert(inputReport));
    }
}
