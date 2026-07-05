package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
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
    void convertToTransaction_validInput_Ok() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,banana,100"
        );
        List<FruitTransaction> result = dataConverter.convertToTransaction(lines);
        assertEquals(1, result.size());
        FruitTransaction fruitTransaction = result.get(0);
        String fruit = fruitTransaction.getFruit();
        assertEquals("banana", fruit);
        int quantity = fruitTransaction.getQuantity();
        assertEquals(100, quantity);
        FruitTransaction.Operation operation = fruitTransaction.getOperation();
        assertEquals(FruitTransaction.Operation.BALANCE, operation);
    }

    @Test
    void convertToTransaction_emptyInput_ThrowsException() {
        List<String> lines = new ArrayList<>();
        assertThrows(
                RuntimeException.class,
                () -> dataConverter.convertToTransaction(lines));
    }

    @Test
    void convertToTransaction_nullInput_ThrowsException() {
        List<String> lines = null;
        assertThrows(
                RuntimeException.class,
                () -> dataConverter.convertToTransaction(lines));
    }

    @Test
    void convertToTransaction_blankLine_Ok() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,banana,100",
                "",
                "s,apple,20"
        );
        List<FruitTransaction> result = dataConverter.convertToTransaction(lines);
        assertEquals(2, result.size());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals("apple", result.get(1).getFruit());
    }

    @Test
    void convertToTransaction_invalidCsvRow_ThrowsException() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,banana"
        );
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(lines));
    }

    @Test
    void convertToTransaction_blankFruitName_ThrowsException() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,,100"
        );
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(lines));
    }

    @Test
    void convertToTransaction_negativeQuantity_ThrowsException() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,banana,-10"
        );
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(lines));
    }

    @Test
    void convertToTransaction_invalidOperation_ThrowsException() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "x,banana,100"
        );
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(lines));
    }
}
