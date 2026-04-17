package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> handlers;
    private OperationStrategy operationStrategy;
    private Map<String, Integer> storage;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        handlers = new HashMap<>();
        storage = new HashMap<>();
    }

    private void initService(Map<FruitTransaction.Operation, OperationHandler> map) {
        operationStrategy = new OperationStrategyImpl(map);
        shopService = new ShopServiceImpl(operationStrategy, storage);
    }

    @Test
    void process_nullTransactionList_notOk() {
        initService(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation())
        );

        List<FruitTransaction> transactions = null;

        assertThrows(RuntimeException.class, () -> shopService.process(transactions));
    }

    @Test
    void process_emptyTransactionList_notOk() {
        initService(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation()
        ));

        List<FruitTransaction> transactions = new ArrayList<>();

        assertThrows(RuntimeException.class, () -> shopService.process(transactions));
    }

    @Test
    void process_balanceOperation_ok() {
        initService(Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperation()));

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10)
        );

        shopService.process(transactions);

        assertEquals(10, storage.get("banana"));
    }

    @Test
    void process_multipleBalanceOperationForSameFruit_ok() {
        initService(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation()
        ));

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana",20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 15)
        );

        shopService.process(transactions);

        assertEquals(15, storage.get("banana"));
    }

    @Test
    void process_multipleBalanceOperationForDifferentFruits_ok() {
        initService(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation()
        ));

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple",20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 15)
        );

        shopService.process(transactions);

        Map<String, Integer> expected = Map.of(
                "banana", 10,
                "apple", 20,
                "orange", 15
        );

        assertEquals(expected, storage);
    }

    @Test
    void process_purchaseOperation_ok() {
        initService(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation())
        );

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5)
        );

        shopService.process(transactions);

        assertEquals(5, storage.get("banana"));
    }

    @Test
    void process_multiplePurchaseOperation_ok() {
        initService(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation())
        );

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 2),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 1)
        );

        shopService.process(transactions);

        assertEquals(2, storage.get("banana"));
    }

    @Test
    void process_multiplePurchaseOperationForDifferentFruits_ok() {
        initService(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation())
        );

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple",20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 15),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 5),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange", 2)
        );

        shopService.process(transactions);

        Map<String, Integer> expected = Map.of(
                "banana", 0,
                "apple", 15,
                "orange", 13
        );

        assertEquals(expected, storage);
    }

    @Test
    void process_supplyOperation_ok() {
        initService(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation())
        );

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 5)
        );

        shopService.process(transactions);

        assertEquals(15, storage.get("banana"));
    }

    @Test
    void process_multipleSupplyOperation_ok() {
        initService(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation())
        );

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 2),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 1)
        );

        shopService.process(transactions);

        assertEquals(18, storage.get("banana"));
    }

    @Test
    void process_multipleSupplyOperationForDifferentFruits_ok() {
        initService(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation())
        );

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple",20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 15),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "orange", 2)
        );

        shopService.process(transactions);

        Map<String, Integer> expected = Map.of(
                "banana", 20,
                "apple", 25,
                "orange", 17
        );

        assertEquals(expected, storage);
    }

    @Test
    void process_returnOperation_ok() {
        initService(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation())
        );

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 5)
        );

        shopService.process(transactions);

        assertEquals(15, storage.get("banana"));
    }

    @Test
    void process_multipleReturnOperation_ok() {
        initService(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation())
        );

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 2),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 1)
        );

        shopService.process(transactions);

        assertEquals(18, storage.get("banana"));
    }

    @Test
    void process_multipleReturnOperationForDifferentFruits_ok() {
        initService(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation())
        );

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple",20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 15),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 5),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 2)
        );

        shopService.process(transactions);

        Map<String, Integer> expected = Map.of(
                "banana", 20,
                "apple", 25,
                "orange", 17
        );

        assertEquals(expected, storage);
    }

}
