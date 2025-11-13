package core.basesyntax.serviceimpltest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.FruitStock;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.serviceimpl.ShopServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategyimpl.OperationStrategyImpl;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {

    @AfterEach
    void clearStock() {
        FruitStock.stock.clear();
    }

    @Test
    void process_validTransactions_shouldUpdateFruitStock() {
        FruitStock fruitStock = new FruitStock();
        fruitStock.add("apple", 0);
        fruitStock.add("banana", 0);
        OperationHandler balanceHandler = (fruit, quantity) ->
                fruitStock.updateFruitQuantity(fruit, quantity);
        OperationHandler supplyHandler = (fruit, quantity) ->
                fruitStock.add(fruit, quantity);
        Map<Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(Operation.BALANCE, balanceHandler);
        handlers.put(Operation.SUPPLY, supplyHandler);
        OperationStrategyImpl strategy = new OperationStrategyImpl(handlers);
        ShopServiceImpl shopService = new ShopServiceImpl(fruitStock, strategy);
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(Operation.BALANCE, "apple", 50),
                new FruitTransaction(Operation.SUPPLY, "apple", 20),
                new FruitTransaction(Operation.SUPPLY, "banana", 10)
        );
        shopService.process(transactions);
        assertEquals(70, fruitStock.getQuantity("apple"));
        assertEquals(10, fruitStock.getQuantity("banana"));
    }
}
