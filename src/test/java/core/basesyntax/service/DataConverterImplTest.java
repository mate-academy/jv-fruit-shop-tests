package core.basesyntax.service;

import core.basesyntax.domain.FruitTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DataConverterImplTest {

    @Test
    @DisplayName("Convert input lines to fruit test")
    void convertToFruit_ok() {
        DataConverterService dataConverterService = new DataConverterImpl();
        List<String> inputContent = new ArrayList<>();
        inputContent.add("fruit,quantity");
        inputContent.add("p,apple,10");
        inputContent.add("b,banana,20");
        List<FruitTransaction> expectedContent = new ArrayList<>();
        expectedContent.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                                                 FruitTransaction.FruitName.APPLE,
                                                 10));
        expectedContent.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                                                 FruitTransaction.FruitName.BANANA,
                                                 20));
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