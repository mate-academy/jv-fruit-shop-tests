package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitTransactionServiceImpl;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitTransactionServiceTest {
    private static FruitDao fruitDao;
    private static OperationStrategy operationStrategy;
    private static FruitTransactionService fruitTransactionService;
    private static Map<FruitTransaction.Operation, OperationHandler> operationStrategyMap;

    @BeforeAll
    static void beforeAll() {
        operationStrategyMap = new HashMap<>();
        operationStrategyMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        fruitDao = new FruitDaoImpl();
        operationStrategy = new OperationStrategyImpl(operationStrategyMap);
        fruitTransactionService = new FruitTransactionServiceImpl(fruitDao, operationStrategy);
        fruitDao.getAll().clear();
    }

    @Test
    void balance_executeTransaction_ok() {
        FruitTransaction balanceTransactionBanana =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 147);
        FruitTransaction balanceTransactionApple =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 28);
        List<FruitTransaction> transactions =
                List.of(balanceTransactionBanana, balanceTransactionApple);

        Fruit banana = new Fruit("banana", 147);
        Fruit apple = new Fruit("apple", 28);

        fruitTransactionService.executeTransaction(transactions);
        List<Fruit> actual = Storage.getFruits();
        List<Fruit> expected = List.of(banana, apple);

        assertEquals(expected, actual);
    }
}
