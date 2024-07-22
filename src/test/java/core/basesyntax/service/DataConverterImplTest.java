package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.domain.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {

    @Test
    @DisplayName("Convert input lines to fruit test")
    void convertToFruit_ok() {
        List<String> inputContent = new ArrayList<>();
        inputContent.add("fruit,quantity");
        inputContent.add("p,apple,10");
        inputContent.add("b,banana,20");
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                                                 FruitTransaction.FruitName.APPLE,
                                                 10));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                                                 FruitTransaction.FruitName.BANANA,
                                                 20));
        DataConverterService dataConverterService = new DataConverterImpl();
        List<FruitTransaction> actualFruitTransactions =
                dataConverterService.convertToFruit(inputContent);
        assertEquals(fruitTransactions, actualFruitTransactions);
    }

    @Test
    @DisplayName("Convert null input lines to fruit")
    void convertNullToFruit_notOk() {
        DataConverterService dataConverterService = new DataConverterImpl();
        assertThrows(NullPointerException.class, () -> dataConverterService.convertToFruit(null));
    }

    @Test
    @DisplayName("Convert empty input lines to fruit")
    void convertEmptyLinesToFruit_notOk() {
        DataConverterService dataConverterService = new DataConverterImpl();
        List<String> expectedInput = new ArrayList<>();
        assertThrows(RuntimeException.class,
                () -> dataConverterService.convertToFruit(expectedInput));
    }
}
