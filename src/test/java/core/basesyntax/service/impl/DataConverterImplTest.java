package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private final DataConverter dataConverter = new DataConverterImpl();

    @Test
    void convertBasicCase_Ok() {
        List<FruitTransaction> expectedList = new ArrayList<>();
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,"apple",10));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,"orange",12));

        List<String> list = new ArrayList<>(
                Arrays.asList("type,fruit,quantity",
                        "b,apple,10",
                        "p,orange,12"
            ));
        assertEquals(expectedList, dataConverter.convertToTransaction(list));
    }

    @Test
    void convertInvalidData_NotOk() {
        List<String> list = new ArrayList<>(
                Arrays.asList("type,fruit,quantity,additionalInformation",
                        "b,10",
                        "p,12"
                ));
        String expected = "Illegal format of data"
                + " : expected 3 parts , but was"
                + 2;
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(list));
        assertEquals(expected, exception.getMessage());
    }
}
