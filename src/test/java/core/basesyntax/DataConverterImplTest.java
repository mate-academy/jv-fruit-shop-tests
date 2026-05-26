package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class DataConverterImplTest {
    private DataConverter converter;

    @BeforeEach
    void setUp() {
        converter = new DataConverterImpl();
    }

    @Test
    void validLine_ok() {
        List<String> input = List.of("b,banana,10");
        List<FruitTransaction> actual = converter.convertToTransaction(input);
        assertEquals(1, actual.size());
        FruitTransaction transaction = actual.get(0);
        assertEquals("banana", transaction.getFruit());
        assertEquals(Operation.BALANCE, transaction.getOperation());
        assertEquals(10, transaction.getQuantity());
    }

    @Test
    void emptyLine_notOk() {
        List<String> input = List.of("");
        assertThrows(RuntimeException.class, () -> {
            converter.convertToTransaction(input);
        });
    }

    @Test
    void tooManyParts_notOk() {
        List<String> input = List.of("b,banana,12,smth");
        assertThrows(RuntimeException.class, () -> {
            converter.convertToTransaction(input);
        });
    }

    @Test
    void blankOperationCode_notOk() {
        List<String> input = List.of(",banana,12");
        assertThrows(RuntimeException.class, () -> {
            converter.convertToTransaction(input);
        });
    }

    @Test
    void blankFruit_notOk() {
        List<String> input = List.of("b,,12");
        assertThrows(RuntimeException.class, () -> {
            converter.convertToTransaction(input);
        });
    }

    @Test
    void blankQuantityString_notOk() {
        List<String> input = List.of("p,banana,");
        assertThrows(RuntimeException.class, () -> {
            converter.convertToTransaction(input);
        });
    }

    @Test
    void quantityIsNotNumber_notOk() {
        List<String> input = List.of("r,banana,abc");
        assertThrows(RuntimeException.class, () -> {
            converter.convertToTransaction(input);
        });
    }

    @Test
    void quantityLessOrEqualZero_notOk() {
        List<String> input = List.of("r,banana,-45");
        assertThrows(RuntimeException.class, () -> {
            converter.convertToTransaction(input);
        });
    }
}
