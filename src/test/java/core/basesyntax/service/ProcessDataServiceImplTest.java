package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ProcessDataServiceImpl;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandlerImpl;
import core.basesyntax.strategy.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.SupplyOperationHandlerImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class ProcessDataServiceImplTest {
    @Test
    public void processData_correctData_ok() {
        List<FruitTransaction> data = new ArrayList<>();
        data.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, new Fruit("banana"), 20));
        data.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, new Fruit("apple"), 100));
        data.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, new Fruit("banana"), 100));
        data.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, new Fruit("banana"), 13));
        data.add(new FruitTransaction(
                FruitTransaction.Operation.RETURN, new Fruit("apple"), 10));
        Map<FruitTransaction.Operation, OperationHandler> operations = new HashMap<>();
        operations.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandlerImpl());
        operations.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandlerImpl());
        operations.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandlerImpl());
        operations.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandlerImpl());
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 107);
        expected.put(new Fruit("apple"), 110);
        ProcessDataService processDataService = new ProcessDataServiceImpl(operations);
        processDataService.processData(data);
        Map<Fruit, Integer> actual = Storage.dataBase;
        Assert.assertEquals("Wrong data processing.", expected, actual);
    }
}
