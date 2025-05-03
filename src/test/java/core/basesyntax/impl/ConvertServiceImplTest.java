package core.basesyntax.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ConvertService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ConvertServiceImplTest {
    private static ConvertService convertService;

    @BeforeAll
    static void beforeAll() {
        convertService = new ConvertServiceImpl();
    }

    @Test
    void convertData_invalidData_notOK() {
        List<String> invalidString = List.of(
                "b,apple,24",
                "a,apple,24",
                "r,apple,32");
        Assertions.assertThrows(RuntimeException.class,
                () -> convertService.convertData(invalidString));
    }

    @Test
    void convertData_nullList_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> convertService.convertData(null));
    }

    @Test
    void convertData_withoutOperation_notOk() {
        List<String> validString = List.of(
                "b,apple,24",
                "apple,24",
                "r,apple,32");
        int actual = convertService.convertData(validString).size();
        int expected = 3;
        Assertions.assertNotEquals(expected, actual);
    }

    @Test
    void convertData_validData_ok() {
        List<String> validString = List.of(
                "b,apple,24",
                "p,apple,24",
                "r,apple,32");
        List<FruitTransaction> expectedList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 24),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 24),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 32));
        List<FruitTransaction> actualList = convertService.convertData(validString);
        Assertions.assertEquals(expectedList.get(0).getOperation(),
                actualList.get(0).getOperation());
        Assertions.assertEquals(expectedList.get(0).getFruit(),
                actualList.get(0).getFruit());
        Assertions.assertEquals(expectedList.get(0).getQuantity(),
                actualList.get(0).getQuantity());

        Assertions.assertEquals(expectedList.get(1).getOperation(),
                actualList.get(1).getOperation());
        Assertions.assertEquals(expectedList.get(1).getFruit(),
                actualList.get(1).getFruit());
        Assertions.assertEquals(expectedList.get(1).getQuantity(),
                actualList.get(1).getQuantity());

        Assertions.assertEquals(expectedList.get(2).getOperation(),
                actualList.get(2).getOperation());
        Assertions.assertEquals(expectedList.get(2).getFruit(),
                actualList.get(2).getFruit());
        Assertions.assertEquals(expectedList.get(2).getQuantity(),
                actualList.get(2).getQuantity());
    }
}
