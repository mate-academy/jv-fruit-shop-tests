package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitConverter;
import core.basesyntax.service.exceptions.InvalidDataException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverterImpl dataConverter = new DataConverterImpl();

    @Test
    void convertToTransaction_incorrectData_notOk() {
        List<String> inputReport = List.of("a,b,c","d,e");
        Assertions.assertThrows(InvalidDataException.class, ()
                -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_correctData_Ok() {
        List<String> inputData = List.of("b,banana,20");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                FruitConverter.convertToFruit("banana"),20));
        List<FruitTransaction> actual = dataConverter.convertToTransaction(inputData);

        Assertions.assertEquals(expected.get(0).getOperation(),actual.get(0).getOperation());
        Assertions.assertEquals(expected.get(0).getFruit(),actual.get(0).getFruit());
        Assertions.assertEquals(expected.get(0).getQuantity(),actual.get(0).getQuantity());
    }
}
