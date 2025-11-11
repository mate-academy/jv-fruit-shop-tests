package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    @BeforeEach
    void clearStorage() {
        Storage.clear();
    }

    @Test
    void process_balanceOperation_updatesStorage() {
        OperationHandler balanceHandler = new BalanceOperation();
        OperationStrategy strategy = new OperationStrategyImpl(
                Map.of(Operation.BALANCE, balanceHandler)
        );
        ShopService shopService = new ShopServiceImpl(strategy);
        FruitTransaction transaction =
                new FruitTransaction(Operation.BALANCE, "banana", 10);
        shopService.process(List.of(transaction));
        assertEquals(10, Storage.getQuantity("banana"));
    }

    @Test
    void process_supplyOperation_updatesStorage() {
        OperationStrategy strategy = new OperationStrategyImpl(
                Map.of(Operation.SUPPLY, new SupplyOperation())
        );
        ShopService shopService = new ShopServiceImpl(strategy);

        shopService.process(List.of(
                new FruitTransaction(Operation.SUPPLY, "apple", 10)
        ));

        assertEquals(10, Storage.getQuantity("apple"));
    }

    @Test
    void process_returnOperation_updatesStorage() {
        Storage.setQuantity("banana", 5);
        OperationStrategy strategy = new OperationStrategyImpl(
                Map.of(Operation.RETURN, new ReturnOperation())
        );
        ShopService shopService = new ShopServiceImpl(strategy);

        shopService.process(List.of(
                new FruitTransaction(Operation.RETURN, "banana", 3)
        ));

        assertEquals(8, Storage.getQuantity("banana"));
    }

    @Test
    void process_purchaseOperation_updatesStorage() {
        Storage.setQuantity("kiwi", 12);
        OperationStrategy strategy = new OperationStrategyImpl(
                Map.of(Operation.PURCHASE, new PurchaseOperation())
        );
        ShopService shopService = new ShopServiceImpl(strategy);

        shopService.process(List.of(
                new FruitTransaction(Operation.PURCHASE, "kiwi", 5)
        ));

        assertEquals(7, Storage.getQuantity("kiwi"));
    }

    @Test
    void process_purchaseNotEnough_throwsException() {
        Storage.setQuantity("orange", 2);
        OperationStrategy strategy = new OperationStrategyImpl(
                Map.of(Operation.PURCHASE, new PurchaseOperation())
        );
        ShopService shopService = new ShopServiceImpl(strategy);

        assertThrows(RuntimeException.class, () -> shopService.process(List.of(
                new FruitTransaction(Operation.PURCHASE, "orange", 5)
        )));
    }
}
