package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private ParserService parserService;
    private List<String> testCsvData;
    private List<FruitTransaction> expectedTransactions;

    @BeforeEach
    void setUp() {
        parserService = new ParserServiceImpl();
        testCsvData = List.of(
                "b, banana, 100",
                "b, apple, 100",
                "p, banana, 20",
                "p, apple, 50"
        );
        expectedTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, new Fruit("banana"), 100),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, new Fruit("apple"), 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, new Fruit("banana"), 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, new Fruit("apple"), 50)
        );
    }

    @Test
    void parseTransactions_parsesDataCorrectly_Ok() {
        List<FruitTransaction> testTransactionsList = parserService.parseTransactions(testCsvData);
        assertEquals(testTransactionsList, expectedTransactions);
    }

    @Test
    void parseTransactions_emptyData_Ok() {
        List<FruitTransaction> testEmptyList = parserService.parseTransactions(List.of());
        List<FruitTransaction> expectedTransactions = List.of();
        assertEquals(testEmptyList, expectedTransactions);
    }

    @Test
    void parseTransactions_throwsNumberFormatException_Ok() {
        testCsvData = List.of(
                "b, banana, $",
                "b, apple, 100",
                "p, banana, 20",
                "p, apple, 50"
        );

        assertThrows(NumberFormatException.class,
                () -> parserService.parseTransactions(testCsvData));
    }

    @Test
    void parseTransactions_throwsIllegalTransactionArgument_Ok() {
        testCsvData = List.of(
                "l, banana, 100",
                "b, apple, 100",
                "p, banana, 20",
                "p, apple, 50"
        );

        assertThrows(IllegalArgumentException.class,
                () -> parserService.parseTransactions(testCsvData));
    }
}
