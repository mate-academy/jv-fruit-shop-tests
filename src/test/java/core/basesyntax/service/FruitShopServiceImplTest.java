package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.SupplyOperation;
import core.basesyntax.strategy.OperationStrategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitShopServiceImplTest {
    private FruitShopService fruitShopService;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationMap = new HashMap<>();
        operationMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationMap);

        fruitShopService = new FruitShopServiceImpl(operationStrategy);
    }

    @Test
    void process_validTransactions_returnValidMap() {
        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction1.setFruit("apple");
        fruitTransaction1.setQuantity(100);

        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction2.setFruit("banana");
        fruitTransaction2.setQuantity(100);

        FruitTransaction fruitTransaction3 = new FruitTransaction();
        fruitTransaction3.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction3.setQuantity(50);
        fruitTransaction3.setFruit("apple");

        FruitTransaction fruitTransaction4 = new FruitTransaction();
        fruitTransaction4.setQuantity(50);
        fruitTransaction4.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction4.setFruit("banana");

        List<FruitTransaction> transactions = List.of(fruitTransaction1, fruitTransaction2,
                fruitTransaction3, fruitTransaction4);

        Map<String, Integer> report = fruitShopService.process(transactions);

        assertEquals(50, report.get("apple"));
        assertEquals(150, report.get("banana"));
    }

    @Test
    void process_nullTransaction_throwException() {
        assertThrows(RuntimeException.class, () -> {
            Map<String, Integer> report = fruitShopService.process(null);
        });
    }

    @Test
    void process_nullOperation_throwException() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(null);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(100);

        List<FruitTransaction> transactions = List.of(fruitTransaction);

        assertThrows(RuntimeException.class, () -> {
            Map<String, Integer> report = fruitShopService.process(transactions);
        });
    }

}
