package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.OperationStrategySupplierImpl;
import core.basesyntax.service.operation.OperationStrategy;
import core.basesyntax.service.operation.impl.BalanceOperationStrategy;
import core.basesyntax.service.operation.impl.PurchaseOperationStrategy;
import core.basesyntax.service.operation.impl.ReturnOperationStrategy;
import core.basesyntax.service.operation.impl.SupplyOperationStrategy;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategySupplierTest {
    private static OperationStrategySupplier operationSupplier;
    private static Map<FruitTransaction.Operation, OperationStrategy> strategyMap;

    @BeforeAll
    public static void initSupplier() {
        FruitDao fruitDao = new FruitDaoImpl();
        strategyMap =
                new HashMap<>(Map.of(FruitTransaction.Operation.BALANCE,
                        new BalanceOperationStrategy(fruitDao),
                        FruitTransaction.Operation.SUPPLY,
                        new SupplyOperationStrategy(fruitDao),
                        FruitTransaction.Operation.PURCHASE,
                        new PurchaseOperationStrategy(fruitDao),
                        FruitTransaction.Operation.RETURN,
                        new ReturnOperationStrategy(fruitDao)));
        operationSupplier = new OperationStrategySupplierImpl(strategyMap);
    }

    @Test
    public void get_nullValue_notOk() {
        FruitTransaction.Operation nullOperation = null;
        Exception exception = assertThrows(NoSuchElementException.class,
                () -> operationSupplier.get(nullOperation));
        assertEquals("There is no such operation: "
                + nullOperation, exception.getMessage());
    }

    @Test
    public void get_defaultOperations_ok() {
        for (FruitTransaction.Operation operation : FruitTransaction.Operation.values()) {
            OperationStrategy expectedStrategy = strategyMap.get(operation);
            OperationStrategy actualStrategy = operationSupplier.get(operation);
            assertEquals(expectedStrategy, actualStrategy);
        }
    }
}
