package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopServiceImpl shopService;
    private List<String> handledTransactions;

    @BeforeEach
    void setUp() {
        handledTransactions = new ArrayList<>();
        OperationStrategy operationStrategy = operation -> (fruit, quantity) ->
                handledTransactions.add(operation + ":" + fruit + ":" + quantity);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_validTransactions_appliesHandlers() {
        FruitTransaction firstTransaction = createTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20);
        FruitTransaction secondTransaction = createTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 15);

        shopService.process(List.of(firstTransaction, secondTransaction));

        List<String> expected = List.of("BALANCE:banana:20", "SUPPLY:apple:15");
        assertEquals(expected, handledTransactions);
    }

    private FruitTransaction createTransaction(FruitTransaction.Operation operation,
            String fruit, int quantity) {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(operation);
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(quantity);
        return fruitTransaction;
    }
}
