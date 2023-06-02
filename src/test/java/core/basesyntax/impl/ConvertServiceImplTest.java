package core.basesyntax.impl;

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
        List<String> nullString = null;
        Assertions.assertThrows(RuntimeException.class,
                () -> convertService.convertData(nullString));
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
        int actual = convertService.convertData(validString).size();
        int expected = 3;
        Assertions.assertEquals(expected, actual);
    }
}
