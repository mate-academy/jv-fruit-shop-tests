package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.BalanceOperation;
import core.basesyntax.operations.OperationHandler;
import core.basesyntax.operations.PurchaseOperation;
import core.basesyntax.operations.ReturnOperation;
import core.basesyntax.operations.SupplyOperation;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static ShopService shopService;
    private static BalanceOperation balanceOperation;
    private static SupplyOperation supplyOperation;
    private static PurchaseOperation purchaseOperation;
    private static ReturnOperation returnOperation;
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static List<FruitTransaction> fruitTransaction;
    private static Map<String, Integer> checkStorage1;
    private static Map<String, Integer> checkStorage2;
    private static List<FruitTransaction> checkFruitTransactionList;
    private static final String BANANA = "banana";
    private static final int NEGATIVE_QUANTITY = -12;
    private static final FruitTransaction BALANCE_OPERATION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 30);
    private static final FruitTransaction SUPPLY_OPERATION =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 100);
    private static final FruitTransaction PURCHASE_OPERATION =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 15);
    private static final FruitTransaction RETURN_OPERATION =
            new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA, 5);
    private static final FruitTransaction PURCHASE_OPERATION2 =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 120);
    private static final FruitTransaction PURCHASE_OPERATION3 =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE,BANANA,0);
    private static final int FINAL_BALANCE = 0;
    private static final int FINAL_BALANCE2 = 100;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceOperation();
        supplyOperation = new SupplyOperation();
        purchaseOperation = new PurchaseOperation();
        returnOperation = new ReturnOperation();
        operationHandlerMap = Map.of(FruitTransaction.Operation.BALANCE, balanceOperation,
                FruitTransaction.Operation.SUPPLY, supplyOperation,
                FruitTransaction.Operation.PURCHASE, purchaseOperation,
                FruitTransaction.Operation.RETURN, returnOperation);
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        shopService = new ShopServiceImpl(operationStrategy);
        checkFruitTransactionList = new ArrayList<>(List.of(
                BALANCE_OPERATION,
                SUPPLY_OPERATION,
                PURCHASE_OPERATION,
                RETURN_OPERATION,
                PURCHASE_OPERATION2,
                PURCHASE_OPERATION3
        ));
        checkStorage1 = new HashMap<>(Map.of(BANANA,FINAL_BALANCE));
        checkStorage2 = new HashMap<>(Map.of(BANANA, FINAL_BALANCE2));
        fruitTransaction = new ArrayList<>();
    }

    @AfterEach
    void afterEach() {
        Storage.clear();
        fruitTransaction.clear();
    }

    @Test
    void negativeAfterPurchase_NotOK() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(BALANCE_OPERATION);
        fruitTransactions.add(PURCHASE_OPERATION2);
        assertThrows(
                RuntimeException.class, () -> shopService.operation(
                        fruitTransactions
                ));
    }

    @Test
    void operationWithNegativeQuantity_NotOK() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,BANANA,NEGATIVE_QUANTITY)
        );
        assertThrows(
                RuntimeException.class,() -> shopService.operation(fruitTransactions)
        );
        fruitTransactions.clear();
        fruitTransactions.add(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,BANANA,NEGATIVE_QUANTITY)
        );
        assertThrows(
                RuntimeException.class,() -> shopService.operation(fruitTransactions)
        );
        fruitTransactions.clear();
        fruitTransactions.add(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,BANANA,NEGATIVE_QUANTITY)
        );
        assertThrows(
                RuntimeException.class,() -> shopService.operation(fruitTransactions)
        );
        fruitTransactions.clear();
        fruitTransactions.add(
                new FruitTransaction(FruitTransaction.Operation.RETURN,BANANA,NEGATIVE_QUANTITY)
        );
        assertThrows(
                RuntimeException.class,() -> shopService.operation(fruitTransactions)
        );
    }

    @Test
    void validFileData_Ok() {
        shopService.operation(checkFruitTransactionList);
        assertEquals(checkStorage1,Storage.getFruitBalance());
        Storage.clear();
        checkFruitTransactionList.add(SUPPLY_OPERATION);
        shopService.operation(checkFruitTransactionList);
        assertEquals(checkStorage2,Storage.getFruitBalance());
    }
}
