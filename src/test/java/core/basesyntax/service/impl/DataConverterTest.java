package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterTest {

    private final DataConverter dataConverter = new DataConverter();

    @Test
    void convertData_validData_returnsTransaction() {
        List<String> data = List.of(
                "b,banana,20",
                "s,apple,100",
                "p,banana,13",
                "r,apple,10"
        );

        List<FruitTransaction> result = dataConverter.convertData(data);

        assertEquals(4, result.size());

        assertEquals(
                FruitTransaction.OperationType.BALANCE,
                result.get(0).getOperation()
        );
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(20, result.get(0).getQuantity());

        assertEquals(
                FruitTransaction.OperationType.SUPPLY,
                result.get(1).getOperation()
        );
        assertEquals("apple", result.get(1).getFruit());
        assertEquals(100, result.get(1).getQuantity());

        assertEquals(
                FruitTransaction.OperationType.PURCHASE,
                result.get(2).getOperation()
        );
        assertEquals("banana", result.get(2).getFruit());
        assertEquals(13, result.get(2).getQuantity());

        assertEquals(
                FruitTransaction.OperationType.RETURN,
                result.get(3).getOperation()
        );
        assertEquals("apple", result.get(3).getFruit());
        assertEquals(10, result.get(3).getQuantity());
    }

    @Test
    void convertData_emptyLine_skipEmptyLines() {
        List<String> data = List.of(
                "",
                "   ",
                "s,banana,100"
        );

        List<FruitTransaction> result = dataConverter.convertData(data);

        assertEquals(1, result.size());
    }

    @Test
    void convertData_nullLine_skipNull() {
        List<String> data = new java.util.ArrayList<>();
        data.add(null);
        data.add("s,banana,100");

        List<FruitTransaction> result = dataConverter.convertData(data);

        assertEquals(1, result.size());
    }

    @Test
    void convertData_wrongNumberOfColumns_skipWrongSizeOfData() {
        List<String> data = List.of(
                "s,banana",
                "s,apple,100,extra",
                "s,orange,20"
        );

        List<FruitTransaction> result = dataConverter.convertData(data);

        assertEquals(1, result.size());
    }

    @Test
    void convertData_emptyFruit_skipEmptyValue() {
        List<String> data = List.of(
                "s,,100",
                "s,apple,50"
        );

        List<FruitTransaction> result = dataConverter.convertData(data);

        assertEquals(1, result.size());
        assertEquals("apple", result.get(0).getFruit());
    }

    @Test
    void convertData_unknownOperation_skipUnknownOperation() {
        List<String> data = List.of(
                "x,banana,100",
                "s,apple,50"
        );

        List<FruitTransaction> result = dataConverter.convertData(data);

        assertEquals(1, result.size());
        assertEquals("apple", result.get(0).getFruit());
    }

    @Test
    void convertData_invalidQuantity_skipWrongType() {
        List<String> data = List.of(
                "s,banana,abc",
                "s,apple,50"
        );

        List<FruitTransaction> result = dataConverter.convertData(data);

        assertEquals(1, result.size());
        assertEquals("apple", result.get(0).getFruit());
    }

    @Test
    void convertData_negativeQuantity_isOk() {
        List<String> data = List.of(
                "s,banana,-10",
                "s,apple,50"
        );

        List<FruitTransaction> result = dataConverter.convertData(data);

        assertEquals(2, result.size());
        assertEquals(-10, result.get(0).getQuantity());
    }

    @Test
    void convertData_ConvertData_dataTrimmed() {
        List<String> data = List.of(
                " s , banana , 100 "
        );

        List<FruitTransaction> result = dataConverter.convertData(data);

        assertEquals(1, result.size());
        assertEquals(
                FruitTransaction.OperationType.SUPPLY,
                result.get(0).getOperation()
        );
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(100, result.get(0).getQuantity());
    }
}
