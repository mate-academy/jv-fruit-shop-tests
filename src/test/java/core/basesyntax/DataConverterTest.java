package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.DataConverterImpl;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DataConverterTest {

    private final DataConverter dataConverter = new DataConverterImpl();

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

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(4, actual.size());

        Assertions.assertEquals(FruitTransaction.Operation.BALANCE, actual.get(0).getOperation());
        Assertions.assertEquals("banana", actual.get(0).getFruit());
        Assertions.assertEquals(10, actual.get(0).getQuantity());

        Assertions.assertEquals(FruitTransaction.Operation.SUPPLY, actual.get(1).getOperation());
        Assertions.assertEquals("apple", actual.get(1).getFruit());
        Assertions.assertEquals(5, actual.get(1).getQuantity());

        Assertions.assertEquals(FruitTransaction.Operation.PURCHASE, actual.get(2).getOperation());
        Assertions.assertEquals("orange", actual.get(2).getFruit());
        Assertions.assertEquals(20, actual.get(2).getQuantity());

        Assertions.assertEquals(FruitTransaction.Operation.RETURN, actual.get(3).getOperation());
        Assertions.assertEquals("banana", actual.get(3).getFruit());
        Assertions.assertEquals(2, actual.get(3).getQuantity());
    }

    @Test
    void convertToTransaction_nullList_notOk() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            dataConverter.convertToTransaction(null);
        });
    }

    @Test
    void convertToTransaction_emptyList_notOk() {
        List<String> inputList = List.of();
        List<FruitTransaction> actual = dataConverter.convertToTransaction(inputList);

        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void convertToTransaction_negativeQuantity_notOk() {
        List<String> inputLines = List.of(
                "type,fruit,quantity",
                "p,banana,-10"
        );
        Assertions.assertThrows(RuntimeException.class, () -> {
            dataConverter.convertToTransaction(inputLines);
        });
    }
}
