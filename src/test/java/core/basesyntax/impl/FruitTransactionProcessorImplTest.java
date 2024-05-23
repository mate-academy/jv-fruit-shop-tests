package core.basesyntax.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
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
    private static final FruitTransaction.Operation STRATEGY_BALANCE =
            FruitTransaction.Operation.BALANCE;
    private static final FruitTransaction.Operation STRATEGY_SUPPLY =
            FruitTransaction.Operation.SUPPLY;
    private static final FruitTransaction.Operation STRATEGY_PURCHASE =
            FruitTransaction.Operation.PURCHASE;
    private static final FruitTransaction.Operation STRATEGY_RETURN =
            FruitTransaction.Operation.RETURN;
    private static Map<FruitTransaction.Operation, StrategyHandler> strategyHandlerMap;
    private static StrategyService strategyService;
    private static FruitTransactionProcessor fruitTransactionProcessor;
    private static FruitDao fruitDao = new FruitDaoImpl();

    @BeforeAll
    static void beforeAll() {
        strategyHandlerMap = Map.of(
                STRATEGY_BALANCE, new BalanceStrategyHandlerImpl(fruitDao),
                STRATEGY_SUPPLY, new SupplyStrategyHandlerImpl(fruitDao),
                STRATEGY_PURCHASE, new PurchaseStrategyHandlerImpl(fruitDao),
                STRATEGY_RETURN, new ReturnStrategyHandlerImpl(fruitDao));
        strategyService = new StrategyServiceImpl(strategyHandlerMap);
        fruitTransactionProcessor = new FruitTransactionProcessorImpl(strategyService);
    }

    @Test
    void fillStorage_validData_Ok() {
        final String fruitName = "apple";
        final int fruitQuantity = 1;
        FruitTransaction fruitTransaction =
                new FruitTransaction(STRATEGY_BALANCE, fruitName, fruitQuantity);
        List<FruitTransaction> fruitTransactionList = List.of(fruitTransaction);
        fruitTransactionProcessor.fillStorage(fruitTransactionList);
        final int zeroSizeThreshold = 0;
        assertTrue(fruitDao.getFruitMap().size() > zeroSizeThreshold);
    }

    @Test
    void fillStorage_nullArgument_NotOk() {
        assertThrows(RuntimeException.class, () -> fruitTransactionProcessor.fillStorage(null));
    }
}
