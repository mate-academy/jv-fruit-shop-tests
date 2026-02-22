package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitTransaction.Operation;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter converter;

    @BeforeEach
    void setConverter() {
        converter = new DataConverterImpl();
    }

    @Test
    void convert_oneValidLine_ok() {
        List<String> lines = List.of("b,banana,20");

        List<FruitTransaction> actual = converter.convert(lines);

        assertEquals(1, actual.size());

        FruitTransaction tx = actual.get(0);
        assertEquals(Operation.BALANCE, tx.getOperation());
        assertEquals("banana", tx.getFruit());
        assertEquals(20, tx.getQuantity());
    }

    @Test
    void convert_unknownOperation_exception() {
        List<String> lines = List.of("x, banana, 34");
        assertThrows(IllegalArgumentException.class, () -> converter.convert(lines));
    }

    @Test
    void convert_wrongInput_exception() {
        List<String> lines = List.of("b,banana, 3, off");
        assertThrows(IllegalArgumentException.class, () -> converter.convert(lines));
    }

    @Test
    void convert_emptyLine_exception() {
        List<String> lines = List.of("");
        assertThrows(IllegalArgumentException.class, () -> converter.convert(lines));
    }

    @Test
    void convert_multipleLines_ok() {
        List<String> lines = List.of(
                "  b, mango, 30",
                "  s, banana, 24",
                "  p, apple, 40"
        );

        List<FruitTransaction> transactions = new ArrayList<>();
        transactions = converter.convert(lines);

        assertEquals(3, transactions.size());

        assertEquals(Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("mango", transactions.get(0).getFruit());
        assertEquals(30, transactions.get(0).getQuantity());

        assertEquals(Operation.SUPPLY, transactions.get(1).getOperation());
        assertEquals("banana", transactions.get(1).getFruit());
        assertEquals(24, transactions.get(1).getQuantity());

        assertEquals(Operation.PURCHASE, transactions.get(2).getOperation());
        assertEquals("apple", transactions.get(2).getFruit());
        assertEquals(40, transactions.get(2).getQuantity());
    }
}
