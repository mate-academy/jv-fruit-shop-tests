package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.StorageOfFruits;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.CalculatorServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationImpl;
import core.basesyntax.strategy.impl.ChooseStrategyHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseOperationImpl;
import core.basesyntax.strategy.impl.ReturnOperationImpl;
import core.basesyntax.strategy.impl.SupplyOperationImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CalculatorServiceImplTest {
    private static final String KEY = "banana";
    private static final Integer VALUE = 100;
    private static final Integer EXCEPTED_VALUE = 100;
    private static CalculatorService calculatorService;
    private static Map<FruitTransaction.Operation, OperationHandler> mapOfOperations;

    @BeforeAll
    static void beforeAll() {
        mapOfOperations = new HashMap<>();
        mapOfOperations.put(FruitTransaction.Operation.BALANCE, new BalanceOperationImpl());
        mapOfOperations.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationImpl());
        mapOfOperations.put(FruitTransaction.Operation.RETURN, new ReturnOperationImpl());
        mapOfOperations.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationImpl());
        calculatorService = new CalculatorServiceImpl(
                new ChooseStrategyHandlerImpl(mapOfOperations));
    }

    @Test
    void changing_Operations_isOK() {
        StorageOfFruits.fruitStorage.put(KEY,VALUE);
        calculatorService.calculate(List.of(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, KEY, VALUE),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, KEY, VALUE)));
        assertEquals(StorageOfFruits.fruitStorage.get(KEY), EXCEPTED_VALUE);
    }
}
