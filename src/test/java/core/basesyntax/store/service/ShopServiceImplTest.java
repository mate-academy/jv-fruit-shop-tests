package core.basesyntax.store.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.store.Storage;
import core.basesyntax.store.handler.impl.BalanceOperation;
import core.basesyntax.store.handler.impl.PurchaseOperation;
import core.basesyntax.store.handler.impl.ReturnOperation;
import core.basesyntax.store.handler.impl.SupplyOperation;
import core.basesyntax.store.model.FruitTransaction;
import core.basesyntax.store.service.strategy.impl.OperationStrategyImpl;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {

    private ShopServiceImpl shopService;

    @BeforeEach
    void setUp() {
        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation()
        ));

        shopService = new ShopServiceImpl(operationStrategy);
        Storage.clearStorage();
    }

    @Test
    void process_shouldProcessSingleSupplyTransaction() {
        String fruit = "apple";
        int initialQuantity = 100;
        int supplyQuantity = 50;
        Storage.modifyFruitStorage(fruit, initialQuantity);

        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation
                .SUPPLY, fruit, supplyQuantity);
        List<FruitTransaction> transactions = Arrays.asList(transaction);

        shopService.process(transactions);

        assertEquals(150, Storage.getFruitQuantity(fruit));
    }

    @Test
    void process_shouldProcessMultipleTransactions() {
        String fruit = "banana";
        Storage.modifyFruitStorage(fruit, 100);

        FruitTransaction supplyTransaction = new FruitTransaction(FruitTransaction.Operation
                .SUPPLY, fruit, 30);
        FruitTransaction purchaseTransaction = new FruitTransaction(FruitTransaction.Operation
                .PURCHASE, fruit, 50);
        FruitTransaction returnTransaction = new FruitTransaction(FruitTransaction.Operation
                .RETURN, fruit, 20);

        List<FruitTransaction> transactions = Arrays
                .asList(supplyTransaction, purchaseTransaction, returnTransaction);

        shopService.process(transactions);

        assertEquals(100, Storage.getFruitQuantity(fruit));
    }

    @Test
    void process_shouldHandleEmptyTransactionList() {
        List<FruitTransaction> transactions = Arrays.asList();

        shopService.process(transactions);

        assertEquals(0, Storage.getFruitQuantity("apple"));
    }
}
