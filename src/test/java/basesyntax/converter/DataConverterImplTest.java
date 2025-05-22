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
        FruitTransaction expectedTransaction = new FruitTransaction(Operation.BALANCE, "apple", 20);
        FruitTransaction actualTransaction = transactions.get(0);
        assertEquals(expectedTransaction, actualTransaction);
    }

    @Test
    void convertToTransaction_moreThenOneConvert_Ok() {
        List<String> lines = List.of("type,fruit,quantity",
                "s,banana,25",
                "b,apple,20",
                "p,kiwi,5");
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(lines);
        FruitTransaction expectedTransaction1 = new FruitTransaction(
                Operation.SUPPLY, "banana", 25);
        FruitTransaction actualTransaction1 = transactions.get(0);
        assertEquals(expectedTransaction1, actualTransaction1);

        FruitTransaction expectedTransaction2 = new FruitTransaction(
                Operation.BALANCE, "apple", 20);
        FruitTransaction actualTransaction2 = transactions.get(1);
        assertEquals(expectedTransaction2, actualTransaction2);

        FruitTransaction expectedTransaction3 = new FruitTransaction(
                Operation.PURCHASE, "kiwi", 5);
        FruitTransaction actualTransaction3 = transactions.get(2);
        assertEquals(expectedTransaction3, actualTransaction3);
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
