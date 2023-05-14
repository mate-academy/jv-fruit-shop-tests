package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.dao.FruitShopDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.OperationStrategy;
import core.basesyntax.service.operation.impl.BalanceOperationHandler;
import core.basesyntax.service.operation.impl.OperationStrategyImpl;
import core.basesyntax.service.operation.impl.PurchaseOperationHandler;
import core.basesyntax.service.operation.impl.ReturnOperationHandler;
import core.basesyntax.service.operation.impl.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static final String FRUIT_APPLE = "apple";
    private static final int BALANCE_QUANTITY = 20;
    private static final int PURCHASE_QUANTITY = 10;
    private static final int SUPPLY_QUANTITY = 20;
    private static final int RETURN_QUANTITY = 10;
    private static final int EXPECTED_QUANTITY = 40;
    private static List<FruitTransaction> fruitTransactionsList;
    private static OperationStrategy operationStrategy;
    private static FruitShopDao fruitShopDao;
    private static FruitService fruitService;
    private static Map<String, Integer> expectedMap;

    @BeforeClass
    public static void beforeClass() {
        fruitTransactionsList = new ArrayList<>();
        fruitService = new FruitServiceImpl();
        fruitShopDao = new FruitShopDaoImpl();
        expectedMap = new HashMap<>();

        FruitTransaction fruitTransactionBalance = new FruitTransaction(BALANCE_QUANTITY,
                FruitTransaction.Operation.BALANCE, FRUIT_APPLE);
        FruitTransaction fruitTransactionPurchase = new FruitTransaction(PURCHASE_QUANTITY,
                FruitTransaction.Operation.PURCHASE, FRUIT_APPLE);
        FruitTransaction fruitTransactionSupply = new FruitTransaction(SUPPLY_QUANTITY,
                FruitTransaction.Operation.SUPPLY, FRUIT_APPLE);
        FruitTransaction fruitTransactionReturn = new FruitTransaction(RETURN_QUANTITY,
                FruitTransaction.Operation.RETURN, FRUIT_APPLE);

        fruitTransactionsList.add(fruitTransactionBalance);
        fruitTransactionsList.add(fruitTransactionPurchase);
        fruitTransactionsList.add(fruitTransactionSupply);
        fruitTransactionsList.add(fruitTransactionReturn);

        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(fruitShopDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(fruitShopDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(fruitShopDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(fruitShopDao));

        operationStrategy = new OperationStrategyImpl(operationHandlerMap);

        expectedMap.put(FRUIT_APPLE, EXPECTED_QUANTITY);
    }

    @Test
    public void fruitService_ok() {
        fruitService.processFruit(fruitTransactionsList, operationStrategy);
        assertEquals(Storage.fruits.get(FRUIT_APPLE), expectedMap.get(FRUIT_APPLE));
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
        expectedMap.clear();
    }
}
