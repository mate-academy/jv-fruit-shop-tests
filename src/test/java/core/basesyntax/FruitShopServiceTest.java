package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.FruitShopService;
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

public class FruitShopServiceTest {
    private static OperationStrategy operationStrategy;
    private static List<FruitTransaction> fruitTransactionList;
    private static Map<String, Integer> fruitsStorageExpected;
    private static Map<OperationType, OperationHandler> operationTypeMap;

    @BeforeClass
    public static void beforeClass() {
        FruitTransaction fruitTransaction1 = new FruitTransaction(OperationType.BALANCE,
                "banana", 40);
        FruitTransaction fruitTransaction2 = new FruitTransaction(OperationType.BALANCE,
                "apple", 80);
        FruitTransaction fruitTransaction3 = new FruitTransaction(OperationType.BALANCE,
                "orange", 30);

        FruitTransaction fruitTransaction4 = new FruitTransaction(OperationType.SUPPLY,
                "banana", 10);
        FruitTransaction fruitTransaction5 = new FruitTransaction(OperationType.SUPPLY,
                "apple", 15);
        FruitTransaction fruitTransaction6 = new FruitTransaction(OperationType.SUPPLY,
                "orange", 20);

        FruitTransaction fruitTransaction7 = new FruitTransaction(OperationType.PURCHASE,
                "banana", 30);
        FruitTransaction fruitTransaction8 = new FruitTransaction(OperationType.PURCHASE,
                "apple", 45);
        FruitTransaction fruitTransaction9 = new FruitTransaction(OperationType.PURCHASE,
                "orange", 50);

        FruitTransaction fruitTransaction10 = new FruitTransaction(OperationType.RETURN,
                "banana", 5);
        FruitTransaction fruitTransaction11 = new FruitTransaction(OperationType.RETURN,
                "apple", 10);
        FruitTransaction fruitTransaction12 = new FruitTransaction(OperationType.RETURN,
                "orange", 15);

        fruitTransactionList = List.of(fruitTransaction1, fruitTransaction2,
                fruitTransaction3, fruitTransaction4, fruitTransaction5, fruitTransaction6,
                fruitTransaction7, fruitTransaction8, fruitTransaction9, fruitTransaction10,
                fruitTransaction11, fruitTransaction12);

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
        assertThrows(ValidationException.class, () -> {
            FruitShopService fruitShopService =
                    new FruitShopServiceImpl(null);
        });
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
