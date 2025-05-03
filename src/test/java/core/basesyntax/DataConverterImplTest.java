package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private static DataConverter converter;

    @BeforeAll
    static void beforeAll() {
        converter = new DataConverterImpl();
    }

    @Test
    void convert_emptyStrings_Ok() {
        assertEquals(0, converter.convertToTransaction(new ArrayList<>()).size());
    }

    @Test
    void convert_firstLineList_Ok() {
        List<String> oneLineString = List.of("type,fruit,quantity");
        assertEquals(0, converter.convertToTransaction(oneLineString).size());
    }

    @Test
    void convert_validStrings_Ok() {
        List<String> validStrings = List.of(
                "type,fruit,quantity",
                "b,apple,100",
                "s,banana,1",
                "p,cherry,13"
        );
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 1),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "cherry", 13)
                );
        List<FruitTransaction> actual = converter.convertToTransaction(validStrings);
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(actual.get(i), expected.get(i));
        }
    }

    @Test
    void convert_incorrectFormatStrings_notOk() {
        List<String> incorrectFormatStrings = List.of(
                "type,fruit,quantity",
                "s,banana,100",
                "b,100",
                "p,banana,13"
        );
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(incorrectFormatStrings));
        assertEquals("Incorrect format of data", exception.getMessage());
    }

    @Test
    void convert_incorrectQuantityFormatStrings_notOk() {
        List<String> incorrectQuantityFormatStrings = List.of(
                "type,fruit,quantity",
                "s,banana,100",
                "b,apple,n",
                "p,banana,13"
        );
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(incorrectQuantityFormatStrings));
        assertEquals("Invalid quantity format of given quantity: n", exception.getMessage());
    }

    @Test
    void process_negativeQuantity_notOk() {
        List<String> negativeQuantityList = List.of(
                "type,fruit,quantity",
                "s,banana,100",
                "b,banana,-99"
        );
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransaction(negativeQuantityList));
        assertEquals("Quantity cannot be negative! Given negative quantity: -99",
                exception.getMessage());
    }
}
