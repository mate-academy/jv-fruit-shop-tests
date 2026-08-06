package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    @Test
    void getHandler_validOperationsMap_returnsCorrectHandler() {
        Map<FruitTransaction.Operation, OperationHandler> map = new HashMap<>();
        map.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        OperationStrategy strategy = new OperationStrategyImpl(map);
        Assertions.assertNotNull(strategy.getHandler(FruitTransaction.Operation.BALANCE));
        Assertions.assertTrue(strategy.getHandler(FruitTransaction.Operation.BALANCE)
                instanceof BalanceOperation);
    }

    @Test
    void getHandler_nullMap_throwsException() {
        OperationStrategy strategy = new OperationStrategyImpl(null);
        Assertions.assertThrows(NullPointerException.class,
                () -> strategy.getHandler(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void getHandler_unknownOperation_returnsNull() {
        Map<FruitTransaction.Operation, OperationHandler> map = new HashMap<>();
        map.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        OperationStrategy strategy = new OperationStrategyImpl(map);
        Assertions.assertNull(strategy.getHandler(FruitTransaction.Operation.RETURN));
    }

    @Test
    void getHandler_purchaseOperation_returnsCorrectHandler() {
        Map<FruitTransaction.Operation, OperationHandler> map = new HashMap<>();
        map.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        OperationStrategy strategy = new OperationStrategyImpl(map);
        Assertions.assertTrue(strategy.getHandler(FruitTransaction.Operation.PURCHASE)
                instanceof PurchaseOperation);
    }

    @Test
    void getHandler_balanceOperation_returnsCorrectHandler() {
        Map<FruitTransaction.Operation, OperationHandler> map = new HashMap<>();
        map.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        OperationStrategy strategy = new OperationStrategyImpl(map);
        Assertions.assertTrue(strategy.getHandler(FruitTransaction.Operation.BALANCE)
                instanceof BalanceOperation);
    }

    @Test
    void getHandler_returnOperation_returnsCorrectHandler() {
        Map<FruitTransaction.Operation, OperationHandler> map = new HashMap<>();
        map.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        OperationStrategy strategy = new OperationStrategyImpl(map);
        Assertions.assertTrue(strategy.getHandler(FruitTransaction.Operation.RETURN)
                instanceof ReturnOperation);
    }

    @Test
    void getHandler_supplyOperation_returnsCorrectHandler() {
        Map<FruitTransaction.Operation, OperationHandler> map = new HashMap<>();
        map.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy strategy = new OperationStrategyImpl(map);
        Assertions.assertTrue(strategy.getHandler(FruitTransaction.Operation.SUPPLY)
                instanceof SupplyOperation);
    }

    @Test
    void purchaseOperation_decreasesQuantity() {
        Storage.getFruits().put("banana", 50);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(10);
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        OperationHandler handler = new PurchaseOperation();
        handler.operate(Storage.getFruits(), transaction);
        Assertions.assertEquals(40, Storage.getFruits().get("banana"));
    }

    @Test
    void returnOperation_increaseQuantity() {
        Storage.getFruits().put("banana", 0);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(10);
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        OperationHandler handler = new ReturnOperation();
        handler.operate(Storage.getFruits(), transaction);
        Assertions.assertEquals(10, Storage.getFruits().get("banana"));
    }

    @Test
    void supplyOperation_increaseQuantity() {
        Storage.getFruits().put("banana", 0);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(10);
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        OperationHandler handler = new SupplyOperation();
        handler.operate(Storage.getFruits(), transaction);
        Assertions.assertEquals(10, Storage.getFruits().get("banana"));
    }

    @AfterEach
    void cleaningStorage() {
        Storage.getFruits().clear();
    }
}
