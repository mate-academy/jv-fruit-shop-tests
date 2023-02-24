package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.WarehouseDao;
import core.basesyntax.dao.WarehouseDaoImpl;
import core.basesyntax.db.Warehouse;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.operation.BalanceOperationHandler;
import core.basesyntax.service.impl.operation.PurchaseOperationHandler;
import core.basesyntax.service.impl.operation.ReturnOperationHandler;
import core.basesyntax.service.impl.operation.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ShopServiceImplTest {
    private WarehouseDao warehouseDao;
    private Map<FruitTransaction.Operation, OperationHandler> operationStrategyMap;
    private OperationStrategy operationStrategy;
    private ShopService shopService;
    private List<FruitTransaction> transactions;

    @Before
    public void setUp() {
        warehouseDao = new WarehouseDaoImpl();
        operationStrategyMap = new HashMap<>();
        operationStrategyMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(warehouseDao));
        operationStrategyMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(warehouseDao));
        operationStrategyMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(warehouseDao));
        operationStrategyMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(warehouseDao));
        operationStrategy = new OperationStrategyImpl(operationStrategyMap);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @After
    public void afterEachTest() {
        Warehouse.warehouse.clear();
    }

    @Test
    public void processTransactions_ok() {
        FruitTransaction balanceBanana20 = new FruitTransaction();
        balanceBanana20.setOperation(BALANCE);
        balanceBanana20.setFruit("banana");
        balanceBanana20.setQuantity(20);
        FruitTransaction balanceApple100 = new FruitTransaction();
        balanceApple100.setOperation(BALANCE);
        balanceApple100.setFruit("apple");
        balanceApple100.setQuantity(100);
        FruitTransaction supplyBanana100 = new FruitTransaction();
        supplyBanana100.setOperation(SUPPLY);
        supplyBanana100.setFruit("banana");
        supplyBanana100.setQuantity(100);
        FruitTransaction purchaseBanana13 = new FruitTransaction();
        purchaseBanana13.setOperation(PURCHASE);
        purchaseBanana13.setFruit("banana");
        purchaseBanana13.setQuantity(13);
        FruitTransaction returnApple10 = new FruitTransaction();
        returnApple10.setOperation(RETURN);
        returnApple10.setFruit("apple");
        returnApple10.setQuantity(10);
        FruitTransaction purcheseApple20 = new FruitTransaction();
        purcheseApple20.setOperation(PURCHASE);
        purcheseApple20.setFruit("apple");
        purcheseApple20.setQuantity(20);
        FruitTransaction purchaseBanana5 = new FruitTransaction();
        purchaseBanana5.setOperation(PURCHASE);
        purchaseBanana5.setFruit("banana");
        purchaseBanana5.setQuantity(5);
        FruitTransaction supplyBanana50 = new FruitTransaction();
        supplyBanana50.setOperation(SUPPLY);
        supplyBanana50.setFruit("banana");
        supplyBanana50.setQuantity(50);
        transactions = new ArrayList<>();
        transactions.add(balanceBanana20);
        transactions.add(balanceApple100);
        transactions.add(supplyBanana100);
        transactions.add(purchaseBanana13);
        transactions.add(returnApple10);
        transactions.add(purcheseApple20);
        transactions.add(purchaseBanana5);
        transactions.add(supplyBanana50);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 152);
        expected.put("apple", 90);
        Map<String, Integer> actual = Warehouse.warehouse;
        shopService.processTransactions(transactions);
        assertEquals(expected, actual);
    }

    @Test
    public void processTransactions_emptyTransactionsList_ok() {
        transactions = new ArrayList<>();
        Map<String, Integer> expected = new HashMap<>();
        Map<String, Integer> actual = Warehouse.warehouse;
        shopService.processTransactions(transactions);
        assertEquals(expected, actual);
    }

    @Test
    public void processTransactions_null_notOk() {
        assertThrows(RuntimeException.class, () -> {
            shopService.processTransactions(null);
        });
    }
}

