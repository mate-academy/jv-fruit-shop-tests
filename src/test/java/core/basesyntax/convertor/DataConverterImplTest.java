package core.basesyntax.convertor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private final DataConverter dataConverter = new DataConverterImpl();

    @Test
    void convertToTransaction_validOutput_ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,100",
                "p,banana,5",
                "r,apple,10");

        List<FruitTransaction> actual = dataConverter.convertToTransaction(input);

        assertEquals(4, actual.size());

        assertEquals(FruitTransaction.Operation.BALANCE, actual.get(0).getOperation());
        assertEquals("banana", actual.get(0).getFruit());
        assertEquals(20, actual.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY, actual.get(1).getOperation());
        assertEquals("apple", actual.get(1).getFruit());
        assertEquals(100, actual.get(1).getQuantity());

        assertEquals(FruitTransaction.Operation.PURCHASE, actual.get(2).getOperation());
        assertEquals("banana", actual.get(2).getFruit());
        assertEquals(5, actual.get(2).getQuantity());

        assertEquals(FruitTransaction.Operation.RETURN, actual.get(3).getOperation());
        assertEquals("apple", actual.get(3).getFruit());
        assertEquals(10, actual.get(3).getQuantity());
    }
}
