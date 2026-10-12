package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.DataConverterImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DataConverterTest {

    private static DataConverter dataConverter;

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validList_ok() {
        List<String> inputLines = List.of(
                "type,fruit,quantity",
                "",
                "b,banana,10",
                "s,apple,5",
                "p,orange,20",
                "r,banana,2");

        List<FruitTransaction> actual = dataConverter.convertToTransaction(inputLines);

        assertNotNull(actual);
        assertEquals(4, actual.size());

        assertEquals(FruitTransaction.Operation.BALANCE, actual.get(0).getOperation());
        assertEquals("banana", actual.get(0).getFruit());
        assertEquals(10, actual.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY, actual.get(1).getOperation());
        assertEquals("apple", actual.get(1).getFruit());
        assertEquals(5, actual.get(1).getQuantity());

        assertEquals(FruitTransaction.Operation.PURCHASE, actual.get(2).getOperation());
        assertEquals("orange", actual.get(2).getFruit());
        assertEquals(20, actual.get(2).getQuantity());

        assertEquals(FruitTransaction.Operation.RETURN, actual.get(3).getOperation());
        assertEquals("banana", actual.get(3).getFruit());
        assertEquals(2, actual.get(3).getQuantity());
    }

    @Test
    void convertToTransaction_nullList_notOk() {
        assertThrows(NullPointerException.class, () -> {
            dataConverter.convertToTransaction(null);
        });
    }

    @Test
    void convertToTransaction_emptyList_notOk() {
        List<String> inputList = List.of();
        List<FruitTransaction> actual = dataConverter.convertToTransaction(inputList);

        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    @Test
    void convertToTransaction_negativeQuantity_notOk() {
        List<String> inputLines = List.of(
                "type,fruit,quantity",
                "p,banana,-10"
        );
        assertThrows(RuntimeException.class, () -> {
            dataConverter.convertToTransaction(inputLines);
        });
    }
}
