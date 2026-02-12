package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.models.FruitTransfer;
import core.basesyntax.models.InvalidDataException;
import core.basesyntax.models.Operation;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static DataConverter dataConverter;

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransfer_validInputReportArgument_Ok() {
        List<String> inputList = List.of("type,fruit,quantity",
                "b,pear,50", "s,banana,100",
                "r,apple,10", "p,plum,10");
        List<FruitTransfer> expected = new ArrayList<>();
        expected.add(new FruitTransfer(Operation.BALANCE, "pear", 50));
        expected.add(new FruitTransfer(Operation.SUPPLY, "banana", 100));
        expected.add(new FruitTransfer(Operation.RETURN, "apple", 10));
        expected.add(new FruitTransfer(Operation.PURCHASE, "plum", 10));
        List<FruitTransfer> actual = dataConverter.convertToTransfer(inputList);
        assertEquals(expected, actual);
    }

    @Test
    void covertToTransfer_EmptyInputReportArgument_NotOk() {
        List<String> emptyInputList = List.of();
        assertThrows(InvalidDataException.class, () ->
                dataConverter.convertToTransfer(emptyInputList));
    }

    @Test
    void convertToTransfer_InvalidOperation_NotOk() {
        List<String> invalidOpereationList = List.of("type,fruit,quantity", "f,apple,15");
        assertThrows(InvalidDataException.class, () ->
                dataConverter.convertToTransfer(invalidOpereationList));
    }

    @Test
    void convertToTransfer_invalidQuantityData_NotOk() {
        List<String> invalidQuantityList = List.of("type,fruit,quantity", "b,pear,FF");
        assertThrows(InvalidDataException.class, () ->
                dataConverter.convertToTransfer(invalidQuantityList));
    }

    @Test
    void convertToTransfer_invalidDataFormatInputReportArgument_NotOk() {
        List<String> invalidDataFormatList = List.of("fox,dog,bird", "p,strawberry,10");
        assertThrows(InvalidDataException.class, () ->
                dataConverter.convertToTransfer(invalidDataFormatList));

    }

    @Test
    void convertToTransfer_NullValue_NotOk() {
        List<String> inputList = null;
        assertThrows(InvalidDataException.class, () ->
                dataConverter.convertToTransfer(inputList));
    }
}
