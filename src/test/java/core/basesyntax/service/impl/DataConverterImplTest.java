package core.basesyntax.service.impl;

import static core.basesyntax.TestConstants.APPLE;
import static core.basesyntax.TestConstants.BANANA;
import static core.basesyntax.TestConstants.REPORT;
import static core.basesyntax.TestConstants.TITLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static DataConverter dataConverter;

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_ShouldThrowException_WhenDataIsEmpty() {
        List<String> emptyData = new ArrayList<>();
        assertThrows(IllegalArgumentException.class,() -> dataConverter
                .convertToTransaction(emptyData));
    }

    @Test
    void convertReport_validReport_ok() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, APPLE, 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE, 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 50));
        List<FruitTransaction> actual = dataConverter.convertToTransaction(REPORT);
        assertEquals(expected, actual);
    }

    @Test
    void convertReport_inputMoreThanThreeParameters_notOk() {
        List<String> wrong = List.of("1,2,3,4", "1,2,3,4");
        assertThrows(IllegalArgumentException.class, () -> dataConverter
                .convertToTransaction(wrong));
    }

    @Test
    void convertReport_validInput_ok() {
        List<String> validList = List.of(TITLE, "b,apple,5", "s,banana,10");
        List<FruitTransaction> expectedTransactions = List.of(
                new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, APPLE, 5),
                new FruitTransaction(
                        FruitTransaction.Operation.SUPPLY, BANANA, 10)
        );

        List<FruitTransaction> actualTransactions
                = dataConverter.convertToTransaction(validList);
        assertEquals(expectedTransactions, actualTransactions);
    }

}
