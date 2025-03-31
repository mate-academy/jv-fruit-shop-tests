package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitBalanceDao;
import core.basesyntax.dao.FruitBalanceDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitBalance;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitBalanceService;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.impl.BalanceOperation;
import core.basesyntax.service.strategy.impl.PurchaseOperation;
import core.basesyntax.service.strategy.impl.ReturnOperation;
import core.basesyntax.service.strategy.impl.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private FruitBalanceDao fruitBalanceDao;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        fruitBalanceDao = new FruitBalanceDaoImpl();
        FruitBalanceService fruitBalanceService = new FruitBalanceServiceImpl(fruitBalanceDao);
        shopService = new ShopServiceImpl(operationStrategy, fruitBalanceService);
    }

    @Test
    void process_nullInputTransactions_notOk() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> shopService.process(null));
        assertEquals("Input transactions list cannot be null", exception.getMessage());
    }

    @Test
    void process_emptyTransactionList_ok() {
        assertTrue(Storage.fruitBalances.isEmpty());
        shopService.process(List.of());
        assertTrue(Storage.fruitBalances.isEmpty());
    }

    @Test
    void process_withMultiplyTransactionsAndEmptyStorage_ok() {
        assertTrue(Storage.fruitBalances.isEmpty());
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,"apple", 50),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,"apple", 30),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,"apple", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE,"kiwi", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,"kiwi", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,"kiwi", 40));
        shopService.process(transactions);
        int expectedAppleBalance = 60;
        int actualAppleBalance = fruitBalanceDao.get("apple").getBalance();
        assertEquals(2, Storage.fruitBalances.size());
        assertEquals(expectedAppleBalance, actualAppleBalance);
    }

    @Test
    void process_withMultiplyTransactionsAndExistingFruitInStorage_ok() {
        Storage.fruitBalances.add(new FruitBalance("kiwi", 50));
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "kiwi", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "kiwi", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "kiwi", 40),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "kiwi", 40));
        shopService.process(transactions);
        int expectedBalance = 150;
        int actualBalance = fruitBalanceDao.get("kiwi").getBalance();
        assertEquals(expectedBalance, actualBalance);
    }

    @AfterEach
    public void afterEachTest() {
        Storage.fruitBalances.clear();
    }
}
