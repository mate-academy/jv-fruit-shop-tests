package service.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.Test;

class CsvTransactionParserTest {
    private final TransactionParser parser = new CsvTransactionParser();

    @Test
    void validCase_validFullData() {
        List<String> data = new ArrayList<>(List.of("fruit, quantity", "b,banana,120",
                "s,banana,12", "r,banana,9", "p,banana,50"));
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.OperationType.BALANCE, "banana",120),
                new FruitTransaction(FruitTransaction.OperationType.SUPPLY, "banana",12),
                new FruitTransaction(FruitTransaction.OperationType.RETURN, "banana",9),
                new FruitTransaction(FruitTransaction.OperationType.PURCHASE, "banana",50));
        List<FruitTransaction> actual = parser.parse(data);
        assertEquals(expected, actual);
    }

    @Test
    void validCase_validEmptyData() {
        List<String> data = new ArrayList<>(List.of("fruit, quantity"));
        List<FruitTransaction> expected = List.of();
        List<FruitTransaction> actual = parser.parse(data);
        assertEquals(expected, actual);
    }

    @Test
    void invalidCase_invalidData() {
        List<String> data = new ArrayList<>(List.of("fruit, quantity", "b,banana,120", "banana,1"));
        assertThrows(IllegalStateException.class, () -> parser.parse(data),
                "Invalid transaction data - banana,1");
    }

    @Test
    void invalidCase_emptyData() {
        List<String> data = new ArrayList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> parser.parse(data));
    }

    @Test
    void invalidCase_nullData() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }
}
