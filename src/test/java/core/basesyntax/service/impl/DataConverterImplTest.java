package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverterImpl dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validInput_Ok() {
        List<String> list = List.of(
                "type,fruit,quantity",
                "b,apple,25",
                "s,banana,13"
        );
        List<FruitTransaction> fruitTransactions = dataConverter.convertToTransaction(list);
        assertEquals(2, fruitTransactions.size());
        assertEquals("apple", fruitTransactions.get(0).getFruit());
        assertEquals(13, fruitTransactions.get(1).getQuantity());
        assertEquals(FruitTransaction.Operation.BALANCE, fruitTransactions.get(0).getOperation());
    }

    @Test
    void convertToTransaction_nullInput_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(null));
    }

    @Test
    void convertToTransaction_emptyList_notOk() {
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(new ArrayList<>()));
    }

    @Test
    void convertToTransaction_invalidHeaderFormat_notOk() {
        List<String> list = List.of(
                "fruit,TYPE,quantity:",
                "b,apple,25",
                "s,banana,13"
        );
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(list));
        assertTrue(exception.getMessage().contains("Invalid file header"));

    }

    @Test
    void convertToTransaction_emptyLine_skipped_Ok() {
        List<String> list = List.of(
                "type,fruit,quantity",
                "b,apple,25",
                "",
                "s,banana,13",
                "",
                ""
        );
        List<FruitTransaction> fruitTransactions = dataConverter.convertToTransaction(list);
        assertEquals(2, fruitTransactions.size());
    }

    @Test
    void parseLineToTransaction_invalidLine_notOk() {
        List<String> list = List.of(
                "type,fruit,quantity",
                "b,25"
        );
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(list));
        assertTrue(exception.getMessage().contains("Invalid line format. Expected 3 columns"));
    }

    @Test
    void getOperationByCode_unknownOperation_notOk() {
        List<String> list = List.of(
                "type,fruit,quantity",
                "t,banana,120"
        );
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(list));
        assertTrue(exception.getMessage().contains(
                "Input line is invalid: unknown operation code"));
    }

    @Test
    void parseFruitName_emptyFruit_notOk() {
        List<String> list = List.of(
                "type,fruit,quantity",
                "b, ,25"
        );
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(list));
        assertTrue(exception.getMessage().contains(
                "Input line is invalid: fruit name should not be empty"));
    }

    @Test
    void parseQuantity_negativeNumber_notOk() {
        List<String> list = List.of(
                "type,fruit,quantity",
                "b,apple,-10"
        );
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(list));
    }

    @Test
    void parseQuantity_invalidNumber_notOk() {
        List<String> list = List.of(
                "type,fruit,quantity",
                "b,apple,ten"
        );
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(list));
    }

}
