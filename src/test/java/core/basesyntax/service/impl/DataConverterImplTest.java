package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter converter;

    @BeforeEach
    void setUp() {
        converter = new DataConverterImpl();
    }

    @Test
    void toTransactions_validInput_ok() {
        List<String> lines = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,100"
        );

        List<FruitTransaction> result = converter.toTransactions(lines);
        assertEquals(2, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(20, result.get(0).getQuantity());
        assertEquals(FruitTransaction.Operation.SUPPLY, result.get(1).getOperation());
        assertEquals("apple", result.get(1).getFruit());
        assertEquals(100, result.get(1).getQuantity());
    }

    @Test
    void toTransactions_emptyLines_shouldSkip() {
        List<String> lines = Arrays.asList(
                "type,fruit,quantity",
                "",
                "b,banana,20",
                "  ",
                "s,apple,100"
        );
        List<FruitTransaction> result = converter.toTransactions(lines);
        assertEquals(2, result.size());
    }

    @Test
    void toTransactions_headerInMiddle_shouldSkip() {
        List<String> lines = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "TYPE,FRUIT,QUANTITY",
                "s,apple,100"
        );
        List<FruitTransaction> result = converter.toTransactions(lines);
        assertEquals(2, result.size());
    }

    @Test
    void toTransactions_withWhitespace_shouldTrim() {
        List<String> lines = Arrays.asList(
                "type,fruit,quantity",
                " b , banana , 20 "
        );
        List<FruitTransaction> result = converter.toTransactions(lines);
        assertEquals(1, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(20, result.get(0).getQuantity());
    }

    @Test
    void toTransactions_allOperations_ok() {
        List<String> lines = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,100",
                "p,banana,5",
                "r,apple,10"
        );
        List<FruitTransaction> result = converter.toTransactions(lines);
        assertEquals(4, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals(FruitTransaction.Operation.SUPPLY, result.get(1).getOperation());
        assertEquals(FruitTransaction.Operation.PURCHASE, result.get(2).getOperation());
        assertEquals(FruitTransaction.Operation.RETURN, result.get(3).getOperation());
    }

    @Test
    void toTransactions_invalidLineFormat_throwsException() {
        List<String> lines = Arrays.asList(
                "type,fruit,quantity",
                "b,banana"
        );
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> converter.toTransactions(lines));
        assertTrue(exception.getMessage().contains("Bad csv line"));
    }

    @Test
    void toTransactions_invalidOperationCode_throwsException() {
        List<String> lines = Arrays.asList(
                "type,fruit,quantity",
                "x,banana,20"
        );
        assertThrows(IllegalArgumentException.class,
                () -> converter.toTransactions(lines));
    }

    @Test
    void toTransactions_negativeQuantity_throwsException() {
        List<String> lines = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,-10"
        );
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> converter.toTransactions(lines));
        assertTrue(exception.getMessage().contains("Negative quantity"));
    }

    @Test
    void toTransactions_invalidQuantityFormat_throwsException() {
        List<String> lines = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,abc"
        );
        assertThrows(NumberFormatException.class,
                () -> converter.toTransactions(lines));
    }

    @Test
    void toTransactions_emptyList_returnsEmptyList() {
        List<String> lines = new ArrayList<>();
        List<FruitTransaction> result = converter.toTransactions(lines);
        assertTrue(result.isEmpty());
    }

    @Test
    void toTransactions_onlyHeader_returnsEmptyList() {
        List<String> lines = Arrays.asList("type,fruit,quantity");
        List<FruitTransaction> result = converter.toTransactions(lines);
        assertTrue(result.isEmpty());
    }
}
