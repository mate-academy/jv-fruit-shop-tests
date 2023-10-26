package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParserServiceTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static ParserService parserService;

    @BeforeAll
    static void beforeAll() {
        parserService = new ParserServiceImpl();
    }

    @Test
    void parse_stringsIntoObjects_isOk() {
        List<String> strings = List.of("b,apple,100", "r,apple,50",
                "b,banana,100", "p,banana,20", "s,apple,50");

        List<FruitTransaction> actual
                = parserService.parseStringsIntoObjects(strings);

        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.BALANCE, APPLE, 100);

        FruitTransaction fruitTransaction1
                = FruitTransaction.of(Operation.RETURN, APPLE, 50);

        FruitTransaction fruitTransaction2
                = FruitTransaction.of(Operation.BALANCE, BANANA, 100);

        FruitTransaction fruitTransaction3
                = FruitTransaction.of(Operation.PURCHASE, BANANA, 20);

        FruitTransaction fruitTransaction4
                = FruitTransaction.of(Operation.SUPPLY, APPLE, 50);

        List<FruitTransaction> expected
                = List.of(fruitTransaction, fruitTransaction1, fruitTransaction2,
                fruitTransaction3, fruitTransaction4);

        assertIterableEquals(expected, actual);
    }

    @Test
    void parse_nullInObjects_isNotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> parserService.parseStringsIntoObjects(null));
    }
}
