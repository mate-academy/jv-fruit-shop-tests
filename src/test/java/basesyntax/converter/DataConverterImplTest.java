package basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import basesyntax.model.FruitTransaction;
import basesyntax.model.Operation;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_oneValidConvert_Ok() {
        List<String> lines = List.of("type,fruit,quantity", "b,apple,20");
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(lines);
        assertEquals(1, transactions.size());
        FruitTransaction transaction = transactions.get(0);
        assertEquals(Operation.BALANCE, transaction.getOperation());
        assertEquals("apple", transaction.getFruit());
        assertEquals(20, transaction.getQuantity());
    }

    @Test
    void convertToTransaction_moreThenOneConvert_Ok() {
        List<String> lines = List.of("type,fruit,quantity",
                "s,banana,25",
                "b,apple,20",
                "p,kiwi,5");
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(lines);
        assertEquals(3, transactions.size());

        FruitTransaction fruitTransaction1 = transactions.get(0);
        assertEquals(Operation.SUPPLY, fruitTransaction1.getOperation());
        assertEquals("banana", fruitTransaction1.getFruit());
        assertEquals(25, fruitTransaction1.getQuantity());

        FruitTransaction fruitTransaction2 = transactions.get(1);
        assertEquals(Operation.BALANCE, fruitTransaction2.getOperation());
        assertEquals("apple", fruitTransaction2.getFruit());
        assertEquals(20, fruitTransaction2.getQuantity());

        FruitTransaction fruitTransaction3 = transactions.get(2);
        assertEquals(Operation.PURCHASE, fruitTransaction3.getOperation());
        assertEquals("kiwi", fruitTransaction3.getFruit());
        assertEquals(5, fruitTransaction3.getQuantity());
    }

    @Test
    void convertToTransaction_invalidLineFormat_NotOk() {
        List<String> lines = List.of("type,fruit,quantity", "s,banana,25,45");
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(lines));
    }

    @Test
    void convertToTransaction_smallLineFormat_NotOk() {
        List<String> lines = List.of("type,fruit,quantity", "p,apple");
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(lines));
    }

    @Test
    void convertToTransaction_invalidQuantityFormat_NotOk() {
        List<String> lines = List.of("type,fruit,quantity", "b,kiwi,abc");
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(lines));
    }

    @Test
    void convertToTransaction_invalidOperationFormat_NotOk() {
        List<String> lines = List.of("type,fruit,quantity", "v,apple,25");
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(lines));
    }
}
