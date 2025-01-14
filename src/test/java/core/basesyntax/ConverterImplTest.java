package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.Converter;
import core.basesyntax.service.impl.ConverterImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConverterImplTest {
    private static List<FruitTransaction> expectedResult;
    private static Converter converter;

    private List<String> data;

    @BeforeAll
    static void beforeAll() {
        converter = new ConverterImpl();
        expectedResult = new ArrayList<>();

        expectedResult.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        expectedResult.add(new FruitTransaction(Operation.SUPPLY, "banana", 100));
        expectedResult.add(new FruitTransaction(Operation.PURCHASE, "banana", 5));
    }

    @BeforeEach
    void setUp() {
        data = new ArrayList<>();
    }

    @Test
    void convertToTransaction_ValidData_Ok() {
        data.add("b,banana,20");
        data.add("s,banana,100");
        data.add("p,banana,5");

        List<FruitTransaction> actualResult = converter.convertToTransaction(data);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void convertToTransaction_EmptyData_NotOk() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            converter.convertToTransaction(new ArrayList<>());
        });
        String actualMessage = exception.getMessage();
        String expectedMessage = "Transaction list cannot be null or empty";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void convertToTransaction_InvalidData_NotOk() {
        data.add("banana,20");
        data.add("s,banana");
        data.add("banana,5");

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            converter.convertToTransaction(new ArrayList<>());
        });
        String actualMessage = exception.getMessage();
        String expectedMessage = "Transaction list cannot be null or empty";
        Assertions.assertEquals(actualMessage, expectedMessage);
    }
}
