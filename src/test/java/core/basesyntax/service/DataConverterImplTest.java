package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {

    @Test
    public void validParsingOfStrings_Ok() {
        List<String> testData = List.of(
                "type,fruit,quantity",
                "b,apple,29",
                "s,banana,45");

        DataConverter dataConverter = new DataConverterImpl();
        List<FruitTransaction> listOfTransactions = dataConverter.convertToTransaction(testData);

        assertEquals(2, listOfTransactions.size());

        assertEquals("b", listOfTransactions.get(0).getOperation().getCode());
        assertEquals("apple", listOfTransactions.get(0).getFruit());
        assertEquals(29, listOfTransactions.get(0).getQuantity());

        assertEquals("s", listOfTransactions.get(1).getOperation().getCode());
        assertEquals("banana", listOfTransactions.get(1).getFruit());
        assertEquals(45, listOfTransactions.get(1).getQuantity());
    }

    @Test
    public void headerOnly_NotOk() {
        List<String> testData = List.of("type,fruit,quantity");

        DataConverter dataConverter = new DataConverterImpl();

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dataConverter.convertToTransaction(testData)
        );
        assertEquals("list has only header", exception.getMessage());
    }

    @Test
    public void invalidOperationCode_NotOk() {
        List<String> testData = List.of(
                "type,fruit,quantity",
                "n,pan,er");

        DataConverter dataConverter = new DataConverterImpl();

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dataConverter.convertToTransaction(testData)
        );

        assertEquals("operation don't exist for this code", exception.getMessage());
    }

    @Test
    public void invalidFruitName_NotOk() {
        List<String> testData = List.of(
                "********",
                "s,pan,***");

        DataConverter dataConverter = new DataConverterImpl();

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dataConverter.convertToTransaction(testData)
        );

        assertEquals("such fruit don't exist", exception.getMessage());
    }

    @Test
    public void invalidQuantityValue_NotOk() {
        List<String> testData = List.of(
                "********",
                "s,apple,***");

        DataConverter dataConverter = new DataConverterImpl();

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dataConverter.convertToTransaction(testData)
        );

        assertEquals("invalid quantity value", exception.getMessage());
    }

    @Test
    public void nullInputData_NotOk() {
        DataConverter dataConverter = new DataConverterImpl();

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dataConverter.convertToTransaction(null)
        );

        assertEquals("input data can not be null", exception.getMessage());
    }

    @Test
    public void emptyList_NotOk() {
        List<String> testData = List.of();

        DataConverter dataConverter = new DataConverterImpl();

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dataConverter.convertToTransaction(testData)
        );

        assertEquals("input list is empty", exception.getMessage());
    }
}
