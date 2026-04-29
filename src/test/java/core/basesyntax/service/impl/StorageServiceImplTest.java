package core.basesyntax.service.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.StorageService;
import core.basesyntax.strategy.OperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageServiceImplTest {
    private Map<Operation, OperationHandler> map = new HashMap<>();
    private StorageService storageService;
    private OperationHandler operationHandlerReturn;
    private OperationHandler operationHandlerBalance;
    private OperationHandler operationHandlerSupply;
    private OperationHandler operationHandlerPurchase;
    private List<FruitTransaction> fruitTransactions = new ArrayList<>();

    @BeforeEach
    void beforeEach() {
        map.clear();
        operationHandlerReturn = mock(OperationHandler.class);
        operationHandlerBalance = mock(OperationHandler.class);
        operationHandlerSupply = mock(OperationHandler.class);
        operationHandlerPurchase = mock(OperationHandler.class);
        map.put(Operation.RETURN, operationHandlerReturn);
        map.put(Operation.BALANCE, operationHandlerBalance);
        map.put(Operation.SUPPLY, operationHandlerSupply);
        map.put(Operation.PURCHASE, operationHandlerPurchase);
        storageService = new StorageServiceImpl(map);
        fruitTransactions.clear();
    }

    @Test
    void process_returnTransaction_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.RETURN, "apple", 230);
        fruitTransactions.add(fruitTransaction);
        storageService.process(fruitTransactions);
        verify(operationHandlerReturn).execute(fruitTransaction);
        verify(operationHandlerBalance, never()).execute(fruitTransaction);
        verify(operationHandlerPurchase, never()).execute(fruitTransaction);
        verify(operationHandlerSupply, never()).execute(fruitTransaction);
    }

    @Test
    void process_balanceTransaction_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE, "apple", 230);
        fruitTransactions.add(fruitTransaction);
        storageService.process(fruitTransactions);
        verify(operationHandlerBalance).execute(fruitTransaction);
        verify(operationHandlerReturn, never()).execute(fruitTransaction);
        verify(operationHandlerPurchase, never()).execute(fruitTransaction);
        verify(operationHandlerSupply, never()).execute(fruitTransaction);
    }

    @Test
    void process_supplyTransaction_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.SUPPLY, "apple", 230);
        fruitTransactions.add(fruitTransaction);
        storageService.process(fruitTransactions);
        verify(operationHandlerSupply).execute(fruitTransaction);
        verify(operationHandlerReturn, never()).execute(fruitTransaction);
        verify(operationHandlerPurchase, never()).execute(fruitTransaction);
        verify(operationHandlerBalance, never()).execute(fruitTransaction);
    }

    @Test
    void process_purchaseTransaction_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.PURCHASE, "apple", 230);
        fruitTransactions.add(fruitTransaction);
        storageService.process(fruitTransactions);
        verify(operationHandlerPurchase).execute(fruitTransaction);
        verify(operationHandlerReturn, never()).execute(fruitTransaction);
        verify(operationHandlerBalance, never()).execute(fruitTransaction);
        verify(operationHandlerSupply, never()).execute(fruitTransaction);
    }

    @Test
    void process_multipleTransactions_ok() {
        FruitTransaction fruitTransaction1 = new FruitTransaction(Operation.RETURN, "apple", 230);
        FruitTransaction fruitTransaction2 = new FruitTransaction(Operation.BALANCE, "apple", 230);
        FruitTransaction fruitTransaction3 = new FruitTransaction(Operation.SUPPLY, "apple", 230);
        FruitTransaction fruitTransaction4 = new FruitTransaction(Operation.PURCHASE, "apple", 230);
        fruitTransactions.add(fruitTransaction1);
        fruitTransactions.add(fruitTransaction1);
        fruitTransactions.add(fruitTransaction1);
        fruitTransactions.add(fruitTransaction2);
        fruitTransactions.add(fruitTransaction3);
        fruitTransactions.add(fruitTransaction3);
        fruitTransactions.add(fruitTransaction3);
        fruitTransactions.add(fruitTransaction3);
        fruitTransactions.add(fruitTransaction4);
        fruitTransactions.add(fruitTransaction4);
        storageService.process(fruitTransactions);
        verify(operationHandlerBalance, times(1)).execute(fruitTransaction2);
        verify(operationHandlerReturn, times(3)).execute(fruitTransaction1);
        verify(operationHandlerSupply, times(4)).execute(fruitTransaction3);
        verify(operationHandlerPurchase, times(2)).execute(fruitTransaction4);
    }

}
