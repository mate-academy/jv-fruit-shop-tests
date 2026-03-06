package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private FruitTransaction firstTransaction;
    private FruitTransaction secondTransaction;
    private List<FruitTransaction> transactionList;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlers;
    private OperationStrategy operationStrategy;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Storage.clear();

        firstTransaction = new FruitTransaction();
        secondTransaction = new FruitTransaction();
        transactionList = new ArrayList<>();
        operationHandlers = new HashMap<>();

        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());

        operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_validBalanceAndSupply_updateStorage_ok() {

        firstTransaction.setOperation("b");
        firstTransaction.setFruit("banana");
        firstTransaction.setQuantity(100);

        secondTransaction.setOperation("s");
        secondTransaction.setFruit("banana");
        secondTransaction.setQuantity(20);

        transactionList.add(firstTransaction);
        transactionList.add(secondTransaction);

        shopService.process(transactionList);

        int expected = 120;

        assertEquals(expected, Storage.getFruitStorage().get("banana"));
    }

    @Test
    void process_purchaseShouldDecreaseStorage_ok() {

        firstTransaction.setOperation("b");
        firstTransaction.setFruit("banana");
        firstTransaction.setQuantity(100);

        secondTransaction.setOperation("p");
        secondTransaction.setFruit("banana");
        secondTransaction.setQuantity(20);

        transactionList.add(firstTransaction);
        transactionList.add(secondTransaction);

        shopService.process(transactionList);

        int expected = 80;

        assertEquals(expected, Storage.getFruitStorage().get("banana"));
    }

    @Test
    void process_whenOperationHandlerMissing_shouldThrowException() {
        operationHandlers.remove(FruitTransaction.Operation.BALANCE);
        operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);

        firstTransaction.setOperation("b");
        firstTransaction.setFruit("banana");
        firstTransaction.setQuantity(100);

        transactionList.add(firstTransaction);

        assertThrows(NullPointerException.class, () -> shopService.process(transactionList));
    }

}
