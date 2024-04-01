package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.impl.BalanceHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseHandlerImpl;
import core.basesyntax.strategy.impl.ReturnHandlerImpl;
import core.basesyntax.strategy.impl.SupplyHandlerImpl;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private static Map<Operation, OperationHandler> mapOfHandlers;

    @BeforeAll
    static void beforeAll() {
        mapOfHandlers = Map.of(
                Operation.BALANCE, new BalanceHandlerImpl(),
                Operation.SUPPLY, new SupplyHandlerImpl(),
                Operation.PURCHASE, new PurchaseHandlerImpl(),
                Operation.RETURN, new ReturnHandlerImpl());
        operationStrategy = new OperationStrategy(mapOfHandlers);
    }

    @Test
    void validOperationHandler_Ok() {
        OperationHandler expectedHandler = mapOfHandlers.get(Operation.BALANCE);
        FruitTransaction fruitTransaction = createFruitTransaction("b", "apple", 20);
        OperationHandler actualHandler = operationStrategy
                .getHandler(fruitTransaction.getOperation());
        assertEquals(expectedHandler, actualHandler);
    }

    @Test
    void invalidOperationHandler_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            FruitTransaction fruitTransaction = createFruitTransaction("h", "apple", 20);
            operationStrategy.getHandler(fruitTransaction.getOperation());
        });
    }

    @Test
    void nullOperationHandler_NotOk() {
        assertThrows(RuntimeException.class, () -> operationStrategy.getHandler(null));
    }

    private FruitTransaction createFruitTransaction(String operation, String fruit, int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(Operation.getOperationFromCode(operation));
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }
}
