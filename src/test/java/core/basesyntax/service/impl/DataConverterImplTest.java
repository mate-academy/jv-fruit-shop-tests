package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.InvalidInputException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.enums.Operation;
import core.basesyntax.service.DataConverter;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final String BANANA = "banana";

    private static DataConverter dataConverter;

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToFruitTransactions_convert_ok() {
        List<String> stringData = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "p,banana,10"
        );
        List<FruitTransaction> actual = dataConverter.convertToFruitTransactions(stringData);
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(Operation.BALANCE, BANANA, 20),
                new FruitTransaction(Operation.PURCHASE, BANANA, 10)
        );
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void convertToFruitTransactions_invalidInput_notOk() {
        List<String> stringData = List.of(
                "type,fruit,quantity",
                "b,banana,20,23"
        );
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            dataConverter.convertToFruitTransactions(stringData);
        });
        String actualMessage = exception.getMessage();
        String expectedMessage = "Invalid input";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void convertToFruitTransactions_invalidQuantity_notOk() {
        List<String> stringData = List.of(
                "type,fruit,quantity",
                "b,banana,-20"
        );
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            dataConverter.convertToFruitTransactions(stringData);
        });
        String actualMessage = exception.getMessage();
        String expectedMessage = "Quantity can't be less than 0";
        assertEquals(expectedMessage, actualMessage);
    }
}
