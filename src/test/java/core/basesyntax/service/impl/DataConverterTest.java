package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterTest {

    private final DataConverter dataConverter = new DataConverter();

    @Test
    void convertData_validData() {
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
    }

    @Test
    void convertData_emptyLine() {
        List<String> data = List.of(
                "",
                "   ",
                "s,banana,100"
        );

        List<FruitTransaction> result = dataConverter.convertData(data);

        assertEquals(1, result.size());
    }

    @Test
    void convertData_nullLine() {
        List<String> data = new java.util.ArrayList<>();
        data.add(null);
        data.add("s,banana,100");

        List<FruitTransaction> result = dataConverter.convertData(data);

        assertEquals(1, result.size());
    }

    @Test
    void convertData_wrongNumberOfColumns() {
        List<String> data = List.of(
                "s,banana",
                "s,apple,100,extra",
                "s,orange,20"
        );

        List<FruitTransaction> result = dataConverter.convertData(data);

        assertEquals(1, result.size());
    }

    @Test
    void convertData_emptyFruit() {
        List<String> data = List.of(
                "s,,100",
                "s,apple,50"
        );

        List<FruitTransaction> result = dataConverter.convertData(data);

        assertEquals(1, result.size());
        assertEquals("apple", result.get(0).getFruit());
    }

    @Test
    void convertData_unknownOperation() {
        List<String> data = List.of(
                "x,banana,100",
                "s,apple,50"
        );

        List<FruitTransaction> result = dataConverter.convertData(data);

        assertEquals(1, result.size());
        assertEquals("apple", result.get(0).getFruit());
    }

    @Test
    void convertData_invalidQuantity() {
        List<String> data = List.of(
                "s,banana,abc",
                "s,apple,50"
        );

        List<FruitTransaction> result = dataConverter.convertData(data);

        assertEquals(1, result.size());
        assertEquals("apple", result.get(0).getFruit());
    }

    @Test
    void convertData_negativeQuantity() {
        List<String> data = List.of(
                "s,banana,-10",
                "s,apple,50"
        );

        List<FruitTransaction> result = dataConverter.convertData(data);

        assertEquals(2, result.size());
    }

    @Test
    void convertData_spacesAroundValues() {
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
