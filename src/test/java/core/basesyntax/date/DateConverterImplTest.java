package core.basesyntax.date;

import core.basesyntax.FruitTransaction;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

class DateConverterImplTest {
    private DateConverter dateConverter;

    @BeforeEach
    public void setUp() {
        dateConverter = new DateConverterImpl();
    }

    @Test
    public void convertToTransaction_ValidDate_Ok() {
        List<String> rawData = List.of(
                "type,fruit,quantity",
                "s,apple,10",
                "p,banana,8"
        );

        List<FruitTransaction> result = dateConverter.convertToTransaction(rawData);
        assertEquals(2, result.size());

        assertEquals(FruitTransaction.Operation.SUPPLY, result.get(0).getOperation());
        assertEquals(FruitTransaction.Operation.PURCHASE, result.get(1).getOperation());

        assertEquals("apple", result.get(0).getFruit());
        assertEquals("banana", result.get(1).getFruit());

        assertEquals(10, result.get(0).getAmount());
        assertEquals(8, result.get(1).getAmount());
    }

    @Test
    public void convertToTransaction_NullDate_NotOk() {
        List<FruitTransaction> result = dateConverter.convertToTransaction(null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void convertToTransaction_EmptyDate_NotOk() {
        List<String> rawData = new ArrayList<>();

        List<FruitTransaction> result = dateConverter.convertToTransaction(rawData);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void convertToTransaction_NullOperation_NotOk() {
        List<String> rawData = List.of(
                "type,fruit,quantity",
                "apple,10",
                "p,banana,8"
        );

        List<FruitTransaction> result = dateConverter.convertToTransaction(rawData);

        assertEquals(1, result.size());

        assertEquals(FruitTransaction.Operation.PURCHASE, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(8, result.get(0).getAmount());
    }

    @Test
    public void convertToTransaction_EmptyFruit_NotOk() {
        List<String> rawData = List.of(
                "type,fruit,quantity",
                "s,,10",
                "p,banana,8"
        );

        List<FruitTransaction> result = dateConverter.convertToTransaction(rawData);

        assertEquals(1, result.size());

        assertEquals(FruitTransaction.Operation.PURCHASE, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(8, result.get(0).getAmount());
    }

    @Test
    public void convertToTransaction_NegativeAmount_NotOk() {
        List<String> rawData = List.of(
                "type,fruit,quantity",
                "s,apple,-10",
                "p,banana,8"
        );

        List<FruitTransaction> result = dateConverter.convertToTransaction(rawData);

        assertEquals(1, result.size());

        assertEquals(FruitTransaction.Operation.PURCHASE, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(8, result.get(0).getAmount());
    }

    @Test
    public void convertToTransaction_MissingParameter_NotOk() {
        List<String> rawData = List.of(
                "type,fruit,quantity",
                "apple,10",
                "p,banana,8"
        );

        List<FruitTransaction> result = dateConverter.convertToTransaction(rawData);

        assertEquals(1, result.size());

        assertEquals(FruitTransaction.Operation.PURCHASE, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(8, result.get(0).getAmount());
    }
}
