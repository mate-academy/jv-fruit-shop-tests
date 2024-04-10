package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseOperationHandler;
import core.basesyntax.operation.ReturnOperationHandler;
import core.basesyntax.operation.SupplyOperationHandler;
import core.basesyntax.service.ProcessData;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProcessDataImplTest {
    private static ProcessData processData;
    private static BalanceOperationHandler balanceOperationHandler;
    private static PurchaseOperationHandler purchaseOperationHandler;
    private static ReturnOperationHandler returnOperationHandler;
    private static SupplyOperationHandler supplyOperationHandler;
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static List<FruitTransaction> checkTransactionsList;
    private static Map<String, Integer> checkStorage1;
    private static Map<String, Integer> checkStorage2;
    private static List<FruitTransaction> fruitTransactions;
    private static final String BANANA = "banana";
    private static final int NEGATIVE_QUANTITY = -20;
    private static final FruitTransaction BALANCE_OPERATION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 20);
    private static final FruitTransaction SUPPLY_OPERATION =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 102);
    private static final FruitTransaction PURCHASE_OPERATION1 =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 20);
    private static final FruitTransaction RETURN_OPERATION =
            new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA, 30);
    private static final FruitTransaction PURCHASE_OPERATION2 =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 0);
    private static final FruitTransaction PURCHASE_OPERATION3 =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 132);
    private static final int FINAL_BALANCE1 = 0;
    private static final int FINAL_BALANCE2 = 102;

    @BeforeEach
    void setUp() {
        balanceOperationHandler = new BalanceOperationHandler();
        supplyOperationHandler = new SupplyOperationHandler();
        purchaseOperationHandler = new PurchaseOperationHandler();
        returnOperationHandler = new ReturnOperationHandler();
        operationHandlerMap = Map.of(FruitTransaction.Operation.BALANCE, balanceOperationHandler,
                FruitTransaction.Operation.PURCHASE, purchaseOperationHandler,
                FruitTransaction.Operation.RETURN, returnOperationHandler,
                FruitTransaction.Operation.SUPPLY, supplyOperationHandler);
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        processData = new ProcessDataImpl(operationStrategy);
        checkTransactionsList = new ArrayList<>(List.of(
                BALANCE_OPERATION,
                SUPPLY_OPERATION,
                PURCHASE_OPERATION1,
                RETURN_OPERATION,
                PURCHASE_OPERATION2,
                PURCHASE_OPERATION3
        ));
        checkStorage1 = new HashMap<>(Map.of(BANANA, FINAL_BALANCE1));
        checkStorage2 = new HashMap<>(Map.of(BANANA, FINAL_BALANCE2));
        fruitTransactions = new ArrayList<>();
    }

    @AfterEach
    void afterEach() {
        Storage.clear();
        fruitTransactions.clear();
    }

    @Test
    void operation_negativeAfterPurchase_throwsException() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(BALANCE_OPERATION);
        fruitTransactions.add(PURCHASE_OPERATION3);
        assertThrows(
                RuntimeException.class, () -> processData.operation(
                        fruitTransactions
                ));
    }

    @Test
    void operation_negativeQuantity_throwsException() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(
                new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, BANANA, NEGATIVE_QUANTITY
                ));
        assertThrows(
                RuntimeException.class, () -> processData.operation(fruitTransactions));
        fruitTransactions.clear();
        fruitTransactions.add(
                new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE, BANANA, NEGATIVE_QUANTITY
                ));
        assertThrows(
                RuntimeException.class, () -> processData.operation(fruitTransactions));
        fruitTransactions.clear();
        fruitTransactions.add(
                new FruitTransaction(
                        FruitTransaction.Operation.RETURN, BANANA, NEGATIVE_QUANTITY
                ));
        assertThrows(
                RuntimeException.class, () -> processData.operation(fruitTransactions));
        fruitTransactions.clear();
        fruitTransactions.add(
                new FruitTransaction(
                        FruitTransaction.Operation.SUPPLY, BANANA, NEGATIVE_QUANTITY
                ));
        assertThrows(
                RuntimeException.class, () -> processData.operation(fruitTransactions));
    }

    @Test
    void operation_validFile_Ok() {
        processData.operation(checkTransactionsList);
        assertEquals(checkStorage1, Storage.getFruitBalance());
        Storage.clear();
        checkTransactionsList.add(SUPPLY_OPERATION);
        processData.operation(checkTransactionsList);
        assertEquals(checkStorage2, Storage.getFruitBalance());
    }
}
