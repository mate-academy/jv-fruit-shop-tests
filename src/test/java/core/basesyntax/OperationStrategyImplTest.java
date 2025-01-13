package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> handlersMap;
    private static OperationStrategy strategy;

    @BeforeAll
    static void beforeAll() {
        handlersMap = new HashMap<>();
        handlersMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlersMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlersMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
    }

    @BeforeEach
    void setUp() {
        strategy = new OperationStrategyImpl(handlersMap);
    }

    @Test
    void get_TypeExists_Ok() {
        OperationHandler handler = strategy.get(FruitTransaction.Operation.BALANCE);
        Assertions.assertInstanceOf(BalanceOperation.class, handler);
    }

    @Test
    void get_TypeNotExist_NotOk() {
        NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () -> {
            strategy.get(FruitTransaction.Operation.SUPPLY);
        });

        String expectedMessage = "No handler found for type: "
                + FruitTransaction.Operation.SUPPLY;

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
