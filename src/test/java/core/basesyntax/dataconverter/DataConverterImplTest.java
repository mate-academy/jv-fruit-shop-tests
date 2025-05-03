package core.basesyntax.dataconverter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private final DataConverterImpl converter = new DataConverterImpl();

    @Test
    void testConvertToTransaction_validInput() {
        List<String> inputReport = Arrays.asList(
                "b,apple,50",
                "s,banana,30",
                "p,orange,20",
                "r,grape,10"
        );

        List<FruitTransaction> transactions = converter.convertToTransaction(inputReport);

        assertEquals(4, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit());
        assertEquals(50, transactions.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY, transactions.get(1).getOperation());
        assertEquals("banana", transactions.get(1).getFruit());
        assertEquals(30, transactions.get(1).getQuantity());

        assertEquals(FruitTransaction.Operation.PURCHASE, transactions.get(2).getOperation());
        assertEquals("orange", transactions.get(2).getFruit());
        assertEquals(20, transactions.get(2).getQuantity());

        assertEquals(FruitTransaction.Operation.RETURN, transactions.get(3).getOperation());
        assertEquals("grape", transactions.get(3).getFruit());
        assertEquals(10, transactions.get(3).getQuantity());
    }

    @Test
    void testConvertToTransaction_emptyList() {
        List<FruitTransaction> transactions = converter.convertToTransaction(List.of());
        assertTrue(transactions.isEmpty());
    }

    @Test
    void testConvertToTransaction_invalidQuantity() {
        List<String> inputReport = List.of("b,apple,abc");
        assertThrows(NumberFormatException.class, () ->
                converter.convertToTransaction(inputReport));
    }
}
