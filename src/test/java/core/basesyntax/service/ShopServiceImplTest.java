package core.basesyntax.service;

import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {

    @Test
    void shopServiceTest_AmountLessThanOne_NotOk() {
        List<FruitTransaction> transactions = new ArrayList<>();
        FruitTransaction.Operation operation = FruitTransaction.Operation.SUPPLY;
        String fruit = "apple";
        int amount = 0;
        FruitTransaction transaction = new FruitTransaction(operation, fruit, amount);
        transactions.add(transaction);
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        ShopService shopService = new ShopServiceImpl(operationStrategy);
        assertThrows(RuntimeException.class, () ->
                shopService.process(transactions));
    }

    @Test
    void shopServiceTest_FruitIsNull_NotOk() {
        List<FruitTransaction> transactions = new ArrayList<>();
        FruitTransaction.Operation operation = FruitTransaction.Operation.PURCHASE;
        String fruit = null;
        int amount = 50;
        FruitTransaction transaction = new FruitTransaction(operation, fruit, amount);
        transactions.add(transaction);
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        ShopService shopService = new ShopServiceImpl(operationStrategy);
        assertThrows(RuntimeException.class, () ->
                shopService.process(transactions));
    }

    @Test
    void shopServiceTest_FruitIsEmpty_NotOk() {
        List<FruitTransaction> transactions = new ArrayList<>();
        FruitTransaction.Operation operation = FruitTransaction.Operation.SUPPLY;
        String fruit = "";
        int amount = 50;
        FruitTransaction transaction = new FruitTransaction(operation, fruit, amount);
        transactions.add(transaction);
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        ShopService shopService = new ShopServiceImpl(operationStrategy);
        assertThrows(RuntimeException.class, () ->
                shopService.process(transactions));
    }

    @Test
    void shopServiceTest_FruitWithSpeciallySymbols_NotOk() {
        List<FruitTransaction> transactions = new ArrayList<>();
        FruitTransaction.Operation operation = FruitTransaction.Operation.RETURN;
        String fruit = "!#*%YB&!#(%";
        int amount = 50;
        FruitTransaction transaction = new FruitTransaction(operation, fruit, amount);
        transactions.add(transaction);
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        ShopService shopService = new ShopServiceImpl(operationStrategy);
        assertThrows(RuntimeException.class, () ->
                shopService.process(transactions));
    }

    @Test
    void shopServiceTest_CheckForNotException_Ok() {
        List<FruitTransaction> transactions = new ArrayList<>();
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        String fruit = "banana";
        int amount = 100;
        FruitTransaction transaction = new FruitTransaction(operation, fruit, amount);
        transactions.add(transaction);
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        ShopService shopService = new ShopServiceImpl(operationStrategy);
        shopService.process(transactions);
    }
}
