package core.basesyntax.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterToObjectTest {
    private DataConverter dataConverter = new DataConverterToObject();

    @Test
    void convert_ValidData_Ok() {
        List<String> inputData = new ArrayList<>();
        inputData.add("type,fruit,quantity");
        inputData.add("b,apple,10");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, "apple", 10));
        List<FruitTransaction> actual = dataConverter.convert(inputData);
        assertTrue(expected.equals(actual));
    }

    @Test
    void convert_NullData_NotOk() {
        List<String> inputDataNull = null;
        assertThrows(RuntimeException.class,
                () -> dataConverter.convert(inputDataNull));
        List<String> emptyInputData = new ArrayList<>();
        assertThrows(RuntimeException.class,
                () -> dataConverter.convert(emptyInputData));
    }
}
