package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.domain.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {

    private static final String HEADER_LINE = "fruit,quantity";
    private static final String FIRST_APPLE_EXPECTED_LINE = "p,apple,10";
    private static final String SECOND_APPLE_EXPECTED_LINE = "b,banana,20";

    @Test
    @DisplayName("Convert input lines to fruit test")
    void convertToFruit_ok() {
        List<String> inputContent = new ArrayList<>();
        inputContent.add(HEADER_LINE);
        inputContent.add(FIRST_APPLE_EXPECTED_LINE);
        inputContent.add(SECOND_APPLE_EXPECTED_LINE);
        List<FruitTransaction> expectedContent = new ArrayList<>();
        expectedContent.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                                                 FruitTransaction.FruitName.APPLE,
                                                 10));
        expectedContent.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                                                 FruitTransaction.FruitName.BANANA,
                                                 20));
        DataConverterService dataConverterService = new DataConverterImpl();
        List<FruitTransaction> actualContent = dataConverterService.convertToFruit(inputContent);
        assertEquals(expectedContent, actualContent);
    }

    @Test
    @DisplayName("Convert null input lines to fruit")
    void convertNullToFruit_notOk() {
        DataConverterService dataConverterService = new DataConverterImpl();
        assertThrows(NullPointerException.class, () -> dataConverterService.convertToFruit(null));
    }
}
