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
    private static Converter converter;

    private List<String> data;
    private List<FruitTransaction> expectedResult;

    @BeforeAll
    static void beforeAll() {
        converter = new ConverterImpl();
    }

    @BeforeEach
    void setUp() {
        data = new ArrayList<>();
        expectedResult = new ArrayList<>();

        data.add("b,banana,20");
        data.add("b,apple,120");
        data.add("s,banana,100");
        data.add("r,apple,10");
        data.add("p,banana,5");

        expectedResult.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        expectedResult.add(new FruitTransaction(Operation.BALANCE, "apple", 120));
        expectedResult.add(new FruitTransaction(Operation.SUPPLY, "banana", 100));
        expectedResult.add(new FruitTransaction(Operation.RETURN, "apple", 10));
        expectedResult.add(new FruitTransaction(Operation.PURCHASE, "banana", 5));
    }

    @Test
    void convertToTransaction_Ok() {
        List<FruitTransaction> actualResult = converter.convertToTransaction(data);
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
