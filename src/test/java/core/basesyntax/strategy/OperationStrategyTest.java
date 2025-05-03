package core.basesyntax.strategy;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImp;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.handler.BalanceHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseHandler;
import core.basesyntax.strategy.handler.ReturnHandler;
import core.basesyntax.strategy.handler.SupplyHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static FruitDao fruitDao;
    private static final Map<Operation, OperationHandler> map = new HashMap<>();
    private static List<FruitTransaction> fruitTransactions;
    private static OperationStrategy operationStrategy;
    private static BalanceHandler balanceHandler;
    private static PurchaseHandler purchaseHandler;
    private static ReturnHandler returnHandler;
    private static SupplyHandler supplyHandler;

    @BeforeAll
    static void setUp() {
        fruitDao = new FruitDaoImp();
        balanceHandler = new BalanceHandler(fruitDao);
        purchaseHandler = new PurchaseHandler(fruitDao);
        returnHandler = new ReturnHandler(fruitDao);
        supplyHandler = new SupplyHandler(fruitDao);
        map.put(Operation.BALANCE, balanceHandler);
        map.put(Operation.PURCHASE, purchaseHandler);
        map.put(Operation.RETURN, returnHandler);
        map.put(Operation.SUPPLY, supplyHandler);
        operationStrategy = new OperationStrategyImp(map);
    }

    @Test
    void validList_Ok() {
        fruitTransactions = List.of(new FruitTransaction(Operation.BALANCE,"banan",20),
                new FruitTransaction(Operation.SUPPLY, "banan",200),
                new FruitTransaction(Operation.PURCHASE, "banan", 3),
                new FruitTransaction(Operation.RETURN, "banan",3));
        assertTrue(operationStrategy.getHandle(fruitTransactions));
    }

    @Test
    void nullValue_NotOk() {
        assertThrows(RuntimeException.class,
                () -> operationStrategy.getHandle(null));
    }

    @Test
    void emptyList_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> operationStrategy.getHandle(List.of()));
    }
}
