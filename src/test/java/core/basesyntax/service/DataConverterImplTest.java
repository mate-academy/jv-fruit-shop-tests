package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.exception.FruitShopException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {

    private final DataConverter converter = new DataConverterImpl();

    @Test
    void convertToTransaction_validLines_ok() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,banana,100",
                "s,apple,50",
                "p,orange,20"
        );

        List<FruitTransaction> transactions = converter.convertToTransaction(lines);

        assertEquals(3, transactions.size());

        // BALANCE
        assertEquals(Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("banana", transactions.get(0).getFruit());
        assertEquals(100, transactions.get(0).getQuantity());

        // SUPPLY
        assertEquals(Operation.SUPPLY, transactions.get(1).getOperation());
        assertEquals("apple", transactions.get(1).getFruit());
        assertEquals(50, transactions.get(1).getQuantity());

        // PURCHASE
        assertEquals(Operation.PURCHASE, transactions.get(2).getOperation());
        assertEquals("orange", transactions.get(2).getFruit());
        assertEquals(20, transactions.get(2).getQuantity());
    }

    @Test
    void convertToTransaction_invalidColumns_throwsException() {
        List<String> lines = List.of("b,banana"); // only 2 columns
        assertThrows(FruitShopException.class, () -> converter.convertToTransaction(lines));
    }

    @Test
    void convertToTransaction_invalidOperationCode_throwsException() {
        List<String> lines = List.of("x,banana,10");
        assertThrows(FruitShopException.class, () -> converter.convertToTransaction(lines));
    }

    @Test
    void convertToTransaction_invalidQuantity_throwsException() {
        List<String> lines = List.of("b,banana,abc"); // non-numeric quantity
        assertThrows(FruitShopException.class, () -> converter.convertToTransaction(lines));
    }

    @Test
    void convertToTransaction_emptyList_returnsEmpty() {
        List<FruitTransaction> transactions = converter.convertToTransaction(List.of());
        assertTrue(transactions.isEmpty());
    }

    @Test
    void convertToTransaction_nullList_throwsException() {
        assertThrows(FruitShopException.class, () -> converter.convertToTransaction(null));
    }
}
