package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandlerImpl;
import core.basesyntax.strategy.impl.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandlerImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
//
public class FruitShopServiceTest {
    private static OperationStrategy operationStrategy;
    private static List<FruitTransaction> fruitTransactionList;
    private static Map<String, Integer> fruitsStorageExpected;
    private static Map<OperationType, OperationHandler> operationTypeMap;

    @BeforeClass
    public static void beforeClass() {
        FruitTransaction bananaForBalance = new FruitTransaction(OperationType.BALANCE,
                "banana", 40);
        FruitTransaction appleForBalance = new FruitTransaction(OperationType.BALANCE,
                "apple", 80);
        FruitTransaction orangeForBalance = new FruitTransaction(OperationType.BALANCE,
                "orange", 30);

        FruitTransaction fruitTransaction4 = new FruitTransaction(OperationType.SUPPLY,
                "banana", 10);
        FruitTransaction appleForSupply = new FruitTransaction(OperationType.SUPPLY,
                "apple", 15);
        FruitTransaction orangeForSupply = new FruitTransaction(OperationType.SUPPLY,
                "orange", 20);

        FruitTransaction bananaForPurchase = new FruitTransaction(OperationType.PURCHASE,
                "banana", 30);
        FruitTransaction appleForPurchase = new FruitTransaction(OperationType.PURCHASE,
                "apple", 45);
        FruitTransaction orangeForPurchase = new FruitTransaction(OperationType.PURCHASE,
                "orange", 50);

        FruitTransaction bananaForReturn = new FruitTransaction(OperationType.RETURN,
                "banana", 5);
        FruitTransaction appleForReturn = new FruitTransaction(OperationType.RETURN,
                "apple", 10);
        FruitTransaction orangeForReturn = new FruitTransaction(OperationType.RETURN,
                "orange", 15);

        fruitTransactionList = List.of(bananaForBalance, appleForBalance,
                orangeForBalance, fruitTransaction4, appleForSupply, orangeForSupply,
                bananaForPurchase, appleForPurchase, orangeForPurchase, bananaForReturn,
                appleForReturn, orangeForReturn);

        operationTypeMap = new HashMap<>();

        OperationHandler balanceOperationHandler = new BalanceOperationHandlerImpl();
        OperationHandler purchaseOperationHandler = new PurchaseOperationHandlerImpl();
        OperationHandler supplyOperationHandler = new SupplyOperationHandlerImpl();
        OperationHandler returnOperationHandler = new ReturnOperationHandlerImpl();

        operationTypeMap.put(OperationType.BALANCE, balanceOperationHandler);
        operationTypeMap.put(OperationType.SUPPLY, supplyOperationHandler);
        operationTypeMap.put(OperationType.RETURN, returnOperationHandler);
        operationTypeMap.put(OperationType.PURCHASE, purchaseOperationHandler);

        operationStrategy = new OperationStrategyImpl(operationTypeMap);

        fruitsStorageExpected = new HashMap<>();
        fruitsStorageExpected.put("banana", 25);
        fruitsStorageExpected.put("apple", 60);
        fruitsStorageExpected.put("orange", 15);
    }

    @Test
    public void add_fruitsTransactionListToFruitShopService_Ok() {
        FruitShopService fruitShopService = new FruitShopServiceImpl(operationStrategy);
        fruitShopService.getFruitBalance(fruitTransactionList);

        Assert.assertEquals("FruitsStorage has wrong data values",
                fruitsStorageExpected, FruitsStorage.fruitsStorage);
    }

    @Test
    public void add_nullToGetFruitShopServiceImplConstructor_NotOk() {
        assertThrows(ValidationException.class, () ->
                new FruitShopServiceImpl(null));
    }

    @Test
    public void add_emptyListToGetFruitBalance_NotOk() {
        FruitShopService fruitShopService = new FruitShopServiceImpl(operationStrategy);
        assertThrows(ValidationException.class,
                () -> fruitShopService.getFruitBalance(new ArrayList<>()));
    }

    @AfterClass
    public static void afterClass() {
        FruitsStorage.fruitsStorage.clear();
        operationTypeMap.clear();
        fruitsStorageExpected.clear();
    }
}
