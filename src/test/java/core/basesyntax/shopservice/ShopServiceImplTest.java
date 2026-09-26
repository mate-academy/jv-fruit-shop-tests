package core.basesyntax.shopservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.fruittransaction.FruitTransaction;
import core.basesyntax.fruittransaction.Operation;
import core.basesyntax.handlers.BalanceOperation;
import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.handlers.PurchaseOperation;
import core.basesyntax.handlers.ReturnOperation;
import core.basesyntax.handlers.SupplyOperation;
import core.basesyntax.operationstrategy.OperationStrategy;
import core.basesyntax.operationstrategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static FruitsDao dao;
    private static OperationStrategy strategy;
    private static ShopService shopService;

    @BeforeAll
    static void beforeAll() {
        dao = new FruitsDaoImpl();
        final Map<Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceOperation(dao));
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperation(dao));
        operationHandlers.put(Operation.RETURN, new ReturnOperation(dao));
        operationHandlers.put(Operation.SUPPLY, new SupplyOperation(dao));
        strategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(strategy);
    }

    @Test
    void process_validArgs_ok() {
        String fruitName = "apple";
        int expected = 5;
        List<FruitTransaction> list = new java.util.ArrayList<>(List.of(
                new FruitTransaction(Operation.BALANCE, fruitName, 1),
                new FruitTransaction(Operation.SUPPLY, fruitName, 2),
                new FruitTransaction(Operation.RETURN, fruitName, 2)));

        shopService.process(list);

        assertEquals(expected, dao.getFruitFromStorage(fruitName));

        shopService.process(List.of(
                new FruitTransaction(Operation.PURCHASE, fruitName, expected)
        ));

        assertEquals(0, dao.getFruitFromStorage(fruitName));
    }

    @Test
    void process_NullList_notOk() {
        assertThrows(IllegalArgumentException.class, () -> shopService.process(null));
    }
}
