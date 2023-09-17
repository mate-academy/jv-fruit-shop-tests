package fruitshop.sevice.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import fruitshop.model.FruitTransaction;
import fruitshop.sevice.OperationProcessor;
import fruitshop.strategy.handlers.BalanceOperationHandler;
import fruitshop.strategy.handlers.PurchaseOperationHandler;
import fruitshop.strategy.handlers.ReturnOperationHandler;
import fruitshop.strategy.handlers.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationProcessorImplTest {
    private OperationProcessor operationProcessor = new OperationProcessorImpl(Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
            FruitTransaction.Operation.RETURN, new ReturnOperationHandler(),
            FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler()));

    @Test
    void processOperations_emptyMap_ok() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        assertDoesNotThrow(() -> operationProcessor.manageTransaction(fruitTransactions));
    }

    @Test
    void processOperations_validMap_ok() {
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 30),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 15)
        );
        assertDoesNotThrow(() -> operationProcessor.manageTransaction(fruitTransactions));
    }
}
