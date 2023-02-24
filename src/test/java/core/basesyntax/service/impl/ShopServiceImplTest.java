package core.basesyntax.service.impl;

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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.*;

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
        operationStrategyMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(warehouseDao));
        operationStrategyMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(warehouseDao));
        operationStrategyMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(warehouseDao));
        operationStrategyMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler(warehouseDao));
        operationStrategy = new OperationStrategyImpl(operationStrategyMap);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @After
    public void afterEachTest() {
        Warehouse.warehouse.clear();
    }

    @Test
    public void processTransactions_ok() {
        FruitTransaction bBanana20 = new FruitTransaction();
        bBanana20.setOperation(BALANCE);
        bBanana20.setFruit("banana");
        bBanana20.setQuantity(20);
        FruitTransaction bApple100 = new FruitTransaction();
        bApple100.setOperation(BALANCE);
        bApple100.setFruit("apple");
        bApple100.setQuantity(100);
        FruitTransaction sBanana100 = new FruitTransaction();
        sBanana100.setOperation(SUPPLY);
        sBanana100.setFruit("banana");
        sBanana100.setQuantity(100);
        FruitTransaction pBanana13 = new FruitTransaction();
        pBanana13.setOperation(PURCHASE);
        pBanana13.setFruit("banana");
        pBanana13.setQuantity(13);
        FruitTransaction rApple10 = new FruitTransaction();
        rApple10.setOperation(RETURN);
        rApple10.setFruit("apple");
        rApple10.setQuantity(10);
        FruitTransaction pApple20 = new FruitTransaction();
        pApple20.setOperation(PURCHASE);
        pApple20.setFruit("apple");
        pApple20.setQuantity(20);
        FruitTransaction pBanana5 = new FruitTransaction();
        pBanana5.setOperation(PURCHASE);
        pBanana5.setFruit("banana");
        pBanana5.setQuantity(5);
        FruitTransaction sBanana50 = new FruitTransaction();
        sBanana50.setOperation(SUPPLY);
        sBanana50.setFruit("banana");
        sBanana50.setQuantity(50);
        transactions = new ArrayList<>();
        transactions.add(bBanana20);
        transactions.add(bApple100);
        transactions.add(sBanana100);
        transactions.add(pBanana13);
        transactions.add(rApple10);
        transactions.add(pApple20);
        transactions.add(pBanana5);
        transactions.add(sBanana50);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 152);
        expected.put("apple", 90);
        Map<String, Integer> actual = Warehouse.warehouse;
        shopService.processTransactions(transactions);
        assertEquals(expected, actual);
    }

    @Test
    public void processEmptyTransactionsList_ok() {
        transactions = new ArrayList<>();
        Map<String, Integer> expected = new HashMap<>();
        Map<String, Integer> actual = Warehouse.warehouse;
        shopService.processTransactions(transactions);
        assertEquals(expected, actual);
    }

    @Test
    public void processTransactionsNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            shopService.processTransactions(null);
        });
    }
}

