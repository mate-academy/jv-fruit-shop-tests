package core.basesyntax.service.impl;

import static core.basesyntax.db.Storage.fruitStorage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.CalculationService;
import core.basesyntax.service.CalculationStrategy;
import core.basesyntax.service.OperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculationServiceImplTest {
    private static Map<Operation, OperationHandler> handlerHashMap = new HashMap<>();
    private static CalculationStrategy calculationStrategy;
    private static List<FruitTransaction> fruitTransactions = new ArrayList<>();
    private static CalculationService calculationService;
    private static String banana = "banana";
    private static String apple = "apple";

    @BeforeAll
    static void beforeAll() {
        handlerHashMap.put(Operation.RETURN, new AddingOperationHandler());
        handlerHashMap.put(Operation.SUPPLY, new AddingOperationHandler());
        handlerHashMap.put(Operation.BALANCE, new AddingOperationHandler());
        handlerHashMap.put(Operation.PURCHASE, new SubtractOperationHandler());

        calculationStrategy = new CalculationStrategyImpl(handlerHashMap);

        calculationService = new CalculationServiceImpl(calculationStrategy);

        fruitTransactions.add(new FruitTransaction(Operation.SUPPLY, banana, 1));
        fruitTransactions.add(new FruitTransaction(Operation.RETURN, apple, 10));
        fruitTransactions.add(new FruitTransaction(Operation.PURCHASE, apple, 1));
        fruitTransactions.add(new FruitTransaction(Operation.BALANCE, banana, 1));

    }

    @BeforeEach
    void beforeEach() {
        fruitStorage.clear();
    }

    @Test
    void constructor_nullInput_notOk() {
        assertThrows(IllegalArgumentException.class, () -> new CalculationServiceImpl(null),
                "IllegalArgumentException expected");
    }

    @Test
    void calculate_validInput_Ok() {
        calculationService.calculate(fruitTransactions);
        int actualBanana = fruitStorage.get(banana);
        int expectedBanana = 1 + 1;
        int actualApple = fruitStorage.get(apple);
        int expectedApple = 10 - 1;
        assertEquals(expectedBanana, actualBanana);
        assertEquals(expectedApple, actualApple);
    }

    @Test
    void calculate_invalidInput_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> calculationService.calculate(null),
                "IllegalArgumentException expected");
        fruitTransactions.clear();
        assertThrows(IllegalArgumentException.class,
                () -> calculationService.calculate(fruitTransactions),
                "IllegalArgumentException expected");
    }
}
