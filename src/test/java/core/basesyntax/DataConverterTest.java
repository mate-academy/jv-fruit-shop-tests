package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.DataConverter;
import core.basesyntax.db.DataConverterImpl;
import core.basesyntax.service.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DataConverterTest {
    private final DataConverter dataConverter = new DataConverterImpl();

    @Test
    void fruitTransaction_convert_Ok() {
        List<String> input = List.of("b,apple,80", "p,orange,10");
        List<FruitTransaction> transaction = dataConverter.fruitTransaction(input);

        assertEquals(2, transaction.size());

        assertEquals(FruitTransaction.Operation.BALANCE, transaction.get(0).getOperation());
        assertEquals("apple", transaction.get(0).getFruit());
        assertEquals(80, transaction.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.PURCHASE, transaction.get(1).getOperation());
        assertEquals("orange", transaction.get(1).getFruit());
        assertEquals(10, transaction.get(1).getQuantity());
    }

    @Test
    void fruitTransaction_skipNotAllowedOperation_Ok() {
        List<String> input = List.of("z,apple,100", "b,banana,50");
        List<FruitTransaction> transaction = dataConverter.fruitTransaction(input);

        assertEquals(1, transaction.size());
    }

    @Test
    void fruitTransaction_returnEmptyListForEmptyInput_Ok() {
        List<String> input = List.of();
        List<FruitTransaction> transaction = dataConverter.fruitTransaction(input);

        assertTrue(transaction.isEmpty(), "Expected empty list: " + transaction);
    }
}
