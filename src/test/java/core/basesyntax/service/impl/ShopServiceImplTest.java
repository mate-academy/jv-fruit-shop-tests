package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    @Test
    public void constructor_nullStrategy_notOk() {
        assertThrows(IllegalArgumentException.class, () -> new ShopServiceImpl(null));
    }

    @Test
    public void process_validTransactions_ok() {
        List<FruitTransaction> handledTransactions = new ArrayList<>();
        List<FruitTransaction.Operation> requestedOperations = new ArrayList<>();
        OperationStrategy strategy = operation -> {
            requestedOperations.add(operation);
            return handledTransactions::add;
        };
        ShopService shopService = new ShopServiceImpl(strategy);
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5)
        );

        shopService.process(transactions);

        assertEquals(transactions, handledTransactions);
        assertEquals(List.of(
                FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.PURCHASE
        ), requestedOperations);
    }

    @Test
    public void process_nullTransactions_notOk() {
        ShopService shopService = new ShopServiceImpl(operation -> transaction -> {
        });

        assertThrows(IllegalArgumentException.class, () -> shopService.process(null));
    }

    @Test
    public void process_transactionListWithNull_notOk() {
        ShopService shopService = new ShopServiceImpl(operation -> transaction -> {
        });
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        transactions.add(null);

        assertThrows(IllegalArgumentException.class, () -> shopService.process(transactions));
    }
}
