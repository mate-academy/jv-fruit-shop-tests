package core.basesyntax.service.imp;

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
import core.basesyntax.strategy.OperationStrategyImp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ProcessDataImpTest {
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
    private static FruitTransaction bananaBalance;
    private static List<FruitTransaction> fruitTransactions;
    private static final String BANANA = "banana";
    private static final int NEGATIVE_QUANTITY = -20;

    @BeforeAll
    static void beforeAll() {
        balanceOperationHandler = new BalanceOperationHandler();
        supplyOperationHandler = new SupplyOperationHandler();
        purchaseOperationHandler = new PurchaseOperationHandler();
        returnOperationHandler = new ReturnOperationHandler();
        operationHandlerMap = Map.of(FruitTransaction.Operation.BALANCE, balanceOperationHandler,
                FruitTransaction.Operation.PURCHASE, purchaseOperationHandler,
                FruitTransaction.Operation.RETURN, returnOperationHandler,
                FruitTransaction.Operation.SUPPLY, supplyOperationHandler);
        operationStrategy = new OperationStrategyImp(operationHandlerMap);
        processData = new ProcessDataImp(operationStrategy);
        bananaBalance = new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 100);
        checkTransactionsList = new ArrayList<>(List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 102),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 40),
                new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA, 30),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 0),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 112)
        ));
        checkStorage1 = new HashMap<>(Map.of(BANANA, 0));
        checkStorage2 = new HashMap<>(Map.of(BANANA, 20));
        fruitTransactions = new ArrayList<>();
    }

    @AfterEach
    void afterEach() {
        Storage.clear();
        fruitTransactions.clear();
    }

    @Test
    void operation_negativeAfterPurchase_notOk() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(bananaBalance);
        fruitTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, BANANA, 110
        ));
        assertThrows(
                RuntimeException.class, () -> processData.operation(
                        fruitTransactions
                ));
    }

    @Test
    void operation_negativeQuantity_notOk() {
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
    void operation_Ok() {
        processData.operation(checkTransactionsList);
        assertEquals(checkStorage1, Storage.getFruitBalance());
        Storage.clear();
        checkTransactionsList.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, BANANA, 20
        ));
        processData.operation(checkTransactionsList);
        assertEquals(checkStorage2, Storage.getFruitBalance());
    }
}
