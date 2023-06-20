package core.basesyntax;

import core.basesyntax.db.FruitShopStorage;
import core.basesyntax.handler.BalanceOperationHandler;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.PurchaseOperationHandler;
import core.basesyntax.handler.ReturnOperationHandler;
import core.basesyntax.handler.SupplyOperationHandler;
import core.basesyntax.impl.OperationStrategyImpl;
import core.basesyntax.impl.ProcessFruitShopStorageImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.util.Operation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationHandlerTests {
    private FruitShopStorage fruitShopStorageDefault;
    private Map<Operation, OperationHandler> operationTypeMap;
    private ProcessFruitShopStorageImpl process;

    @BeforeEach
    void setUp() {
        fruitShopStorageDefault = new FruitShopStorage();
        fruitShopStorageDefault.put("banana", 100);
        fruitShopStorageDefault.put("apple", 100);
        setupOperationTypeMap();
        setupProcess();
    }

    private void setupOperationTypeMap() {
        operationTypeMap = new HashMap<>();
        operationTypeMap.put(Operation.BALANCE,
                new BalanceOperationHandler(fruitShopStorageDefault));
        operationTypeMap.put(Operation.SUPPLY,
                new SupplyOperationHandler(fruitShopStorageDefault));
        operationTypeMap.put(Operation.RETURN,
                new ReturnOperationHandler(fruitShopStorageDefault));
        operationTypeMap.put(Operation.PURCHASE,
                new PurchaseOperationHandler(fruitShopStorageDefault));
    }

    private void setupProcess() {
        process = new ProcessFruitShopStorageImpl(
                new OperationStrategyImpl(operationTypeMap), fruitShopStorageDefault);
    }

    @Test
    void processFruitShopStorage_fillStorage_ok() {
        FruitTransaction fruitTransactionZero =
                new FruitTransaction(Operation.BALANCE, "banana", 50);
        final FruitTransaction fruitTransactionOne =
                new FruitTransaction(Operation.SUPPLY, "banana", 50);
        final FruitTransaction fruitTransactionTwo =
                new FruitTransaction(Operation.PURCHASE, "banana", 100);
        final FruitTransaction fruitTransactionThree =
                new FruitTransaction(Operation.RETURN, "banana", 50);

        int actualDefault = fruitShopStorageDefault.getQuantity("banana");
        Assertions.assertEquals(100, actualDefault);

        List<FruitTransaction> listFruitTransaction = new ArrayList<>();
        listFruitTransaction.add(fruitTransactionZero);
        process.fillFruitShopStorage(listFruitTransaction);
        int actual = fruitShopStorageDefault.getQuantity("banana");
        Assertions.assertEquals(50, actual);

        listFruitTransaction.add(fruitTransactionOne);
        process.fillFruitShopStorage(listFruitTransaction);
        int actual2 = fruitShopStorageDefault.getQuantity("banana");
        Assertions.assertEquals(100, actual2);

        listFruitTransaction.add(fruitTransactionTwo);
        process.fillFruitShopStorage(listFruitTransaction);
        int actual3 = fruitShopStorageDefault.getQuantity("banana");
        Assertions.assertEquals(0, actual3);

        listFruitTransaction.add(fruitTransactionThree);
        process.fillFruitShopStorage(listFruitTransaction);
        int actual4 = fruitShopStorageDefault.getQuantity("banana");
        Assertions.assertEquals(50, actual4);
    }

    @Test
    void purchaseOperationHandler_handle_notOk() {
        List<FruitTransaction> listFruitTransaction = new ArrayList<>();
        listFruitTransaction.add(new FruitTransaction(Operation.PURCHASE, "banana", 101));
        Assertions.assertThrows(Throwable.class, () -> {
            process.fillFruitShopStorage(listFruitTransaction);
        });
    }

    @Test
    void operationServiceImpl_getOperationService_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE, "banana", 50);
        Operation actual = fruitTransaction.getOperation();
        Assertions.assertEquals(Operation.BALANCE, actual);
    }

    @Test
    void operation_getCode_notOk() {
        String operationCodeWrong = "a";
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Operation.getCode(operationCodeWrong);
        });
    }

    @AfterEach
    public void afterEachTest() {
        fruitShopStorageDefault.clear();
    }
}
