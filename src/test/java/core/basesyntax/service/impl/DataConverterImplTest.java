package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataConverter;
import core.basesyntax.validator.DataValidator;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private final DataValidator dataValidator = new DataValidator();
    private final DataConverter dataConverter = new DataConverterImpl(dataValidator);

    @Test
    void convertToTransaction_validData_success() {
        List<String> inputData = Arrays.asList(
                "type,fruit,quantity",
                "b,apple,50",
                "s,banana,30",
                "p,orange,20");
        List<FruitTransaction> expected = Arrays.asList(
            new FruitTransaction(Operation.BALANCE, "apple", 50),
            new FruitTransaction(Operation.SUPPLY, "banana", 30),
            new FruitTransaction(Operation.PURCHASE, "orange", 20));
        List<FruitTransaction> actual = dataConverter.convertToTransaction(inputData);
        assertEquals(expected, actual, "Incorrect data");
    }
}
