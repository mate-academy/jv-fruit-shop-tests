package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.handlerimpl.BalanceOperation;
import core.basesyntax.strategy.handlerimpl.PurchaseOperation;
import core.basesyntax.strategy.handlerimpl.ReturnOperation;
import core.basesyntax.strategy.handlerimpl.SupplyOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitShopServiceImplTest {
    private FruitShopService fruitShopService;
    private List<FruitTransaction> fruitTransactions;

    @BeforeEach
    public void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation()
        );
        fruitShopService = new FruitShopServiceImpl(new OperationStrategyImpl(operationHandlerMap));
        fruitTransactions = new ArrayList<>();
    }

    @Test
    public void process_validTransactions_ok() {
        Map<String,Integer> expectedFruitStorage = Map.of(
                "banana", 152,
                "apple", 160
        );
        fruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50)
        );
        fruitShopService.process(fruitTransactions);
        assertEquals(expectedFruitStorage, Storage.fruitStorage);
    }

    @Test
    public void process_nullTransactions_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitShopService.process(null);
        });
    }

    @Test
    public void process_emptyData_ok() {
        Map<String,Integer> expectedFruitStorage = Map.of();
        List<FruitTransaction> emptyFruitTransactions = new ArrayList<>();
        fruitShopService.process(emptyFruitTransactions);
        assertEquals(expectedFruitStorage, Storage.fruitStorage);
    }

    @Test
    public void process_incorrectTransactionData_notOk() {
        fruitTransactions.add(new FruitTransaction(null, "banana", 100));
        assertThrows(RuntimeException.class, () -> {
            fruitShopService.process(fruitTransactions);
        });
    }

    @Test
    public void process_negativeAmount_notOk() {
        fruitTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", -200));
        assertThrows(RuntimeException.class, () -> {
            fruitShopService.process(fruitTransactions);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }
}
