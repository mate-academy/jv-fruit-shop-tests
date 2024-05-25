package core.basesyntax.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionProcessor;
import core.basesyntax.service.StrategyService;
import core.basesyntax.service.impl.FruitTransactionProcessorImpl;
import core.basesyntax.service.impl.StrategyServiceImpl;
import core.basesyntax.strategy.BalanceStrategyHandlerImpl;
import core.basesyntax.strategy.PurchaseStrategyHandlerImpl;
import core.basesyntax.strategy.ReturnStrategyHandlerImpl;
import core.basesyntax.strategy.StrategyHandler;
import core.basesyntax.strategy.SupplyStrategyHandlerImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionProcessorImplTest {
    private static Map<FruitTransaction.Operation, StrategyHandler> strategyHandlerMap;
    private static StrategyService strategyService;
    private static FruitTransactionProcessor fruitTransactionProcessor;
    private static FruitDao fruitDao;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        strategyHandlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceStrategyHandlerImpl(fruitDao),
                FruitTransaction.Operation.SUPPLY, new SupplyStrategyHandlerImpl(fruitDao),
                FruitTransaction.Operation.PURCHASE, new PurchaseStrategyHandlerImpl(fruitDao),
                FruitTransaction.Operation.RETURN, new ReturnStrategyHandlerImpl(fruitDao));
        strategyService = new StrategyServiceImpl(strategyHandlerMap);
        fruitTransactionProcessor = new FruitTransactionProcessorImpl(strategyService);
    }

    @Test
    void fillStorage_validData_ok() {
        List<FruitTransaction> fruitTransactionList
                = List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 1));
        fruitTransactionProcessor.fillStorage(fruitTransactionList);
        final int zeroSizeThreshold = 0;
        assertTrue(Storage.FRUITS.size() > zeroSizeThreshold);
    }

    @Test
    void fillStorage_nullArgument_notOk() {
        assertThrows(RuntimeException.class, () -> fruitTransactionProcessor.fillStorage(null));
    }
}
