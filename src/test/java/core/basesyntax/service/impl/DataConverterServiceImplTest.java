package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverterService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterServiceImplTest {
    private static DataConverterService dataConverterService;
    private List<FruitTransaction> expected;

    @BeforeAll
    static void beforeAll() {
        dataConverterService = new DataConverterServiceImpl();
    }

    @BeforeEach
    void setUp() {
        expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, new Fruit("banana"), 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, new Fruit("apple"), 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, new Fruit("banana"), 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, new Fruit("banana"), 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, new Fruit("apple"), 5)
        );
    }

    @Test
    void convertToTransaction_validInputReport_ok() {
        List<String> inputReport = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,10",
                "s,banana,100",
                "p,banana,13",
                "r,apple,5"
        );
        List<FruitTransaction> actual = dataConverterService.convertToTransaction(inputReport);
        assertEquals(expected, actual);
    }

    @Test
    void convertToTransaction_inValidInputReport_notOk() {
        List<String> inputReportWithoutTitle = List.of(
                "b,banana,20",
                "b,apple,10",
                "s,banana,100",
                "p,banana,13",
                "r,apple,5"
        );
        List<FruitTransaction> actual = dataConverterService
                .convertToTransaction(inputReportWithoutTitle);
        assertNotEquals(expected, actual);
    }

    @Test
    void convertToTransaction_inappropriateCountOfElements_notOk() {
        List<String> inputReport = List.of(
                "type,fruit,quantity",
                "b,apple"
        );
        assertThrows(RuntimeException.class,
                () -> dataConverterService.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_invalidQuantityType_notOk() {
        List<String> inputReport = List.of(
                "type,fruit,quantity",
                "b,apple,ten"
        );
        assertThrows(IllegalArgumentException.class,
                () -> dataConverterService.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_negativeQuantity_notOk() {
        List<String> inputReport = List.of(
                "type,fruit,quantity",
                "b,apple,-1"
        );
        assertThrows(IllegalArgumentException.class,
                () -> dataConverterService.convertToTransaction(inputReport));
    }
}
