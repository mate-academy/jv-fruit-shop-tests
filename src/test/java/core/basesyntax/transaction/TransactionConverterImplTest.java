package core.basesyntax.transaction;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.Operation;
import core.basesyntax.db.Storage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionConverterImplTest {
    private TransactionConverterImpl converter;
    private final List<String> lines = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        converter = new TransactionConverterImpl();
    }

    @AfterEach
    public void tearDown() throws IOException {
        lines.clear();
        Storage.storage.clear();
    }

    @Test
    public void convert_EmptyList_ReturnsEmptyList() {
        List<FruitTransaction> result = converter.convert(lines);
        assertTrue(result.isEmpty());
    }

    @Test
    public void convert_SingleTransaction_ReturnsCorrectTransaction() {
        List<String> lines = Arrays.asList("type,fruit,quantity", "s,apple,10");
        List<FruitTransaction> result = converter.convert(lines);
        assertEquals(1, result.size());
        FruitTransaction transaction = result.get(0);
        assertEquals(Operation.SUPPLY, transaction.getOperation());
        assertEquals("apple", transaction.getFruit());
        assertEquals(10, transaction.getQuantity());
    }

    @Test
    public void convert_MultipleTransactions_ReturnsCorrectTransactions() {
        List<String> lines = Arrays.asList("type,fruit,quantity", "s,apple,10", "p,banana,5");
        List<FruitTransaction> result = converter.convert(lines);
        assertEquals(2, result.size());

        FruitTransaction transaction1 = result.get(0);
        assertEquals(Operation.SUPPLY, transaction1.getOperation());
        assertEquals("apple", transaction1.getFruit());
        assertEquals(10, transaction1.getQuantity());

        FruitTransaction transaction2 = result.get(1);
        assertEquals(Operation.PURCHASE, transaction2.getOperation());
        assertEquals("banana", transaction2.getFruit());
        assertEquals(5, transaction2.getQuantity());
    }

    @Test
    void convert_OnlyHeaderLine_Ok() {
        lines.add("type,fruit,quantity");
        assertDoesNotThrow(() -> converter.convert(lines));
    }
}
