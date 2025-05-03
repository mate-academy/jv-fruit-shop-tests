package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.FruitStorageImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AllOperationsHandlerTest {
    private final FruitStorage fruitStorage = new FruitStorageImpl();
    private final OperationHandler balanceHandler = new BalanceOperationHandler(fruitStorage);
    private final OperationHandler supplyHandler = new SupplyOperationHandler(fruitStorage);
    private final OperationHandler returnHandler = new ReturnOperationHandler(fruitStorage);
    private final OperationHandler purchaseHandler = new PurchaseOperationHandler(fruitStorage);

    private final Map<FruitTransaction.Operation, OperationHandler> handlers = Map.of(
            FruitTransaction.Operation.BALANCE,
            balanceHandler,
            FruitTransaction.Operation.SUPPLY,
            supplyHandler,
            FruitTransaction.Operation.PURCHASE,
            purchaseHandler,
            FruitTransaction.Operation.RETURN,
            returnHandler);

    private final OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);
    private final ShopService shopService = new ShopServiceImpl(operationStrategy, fruitStorage);

    @BeforeEach
    void setUp() {
        fruitStorage.addFruit("Banana", 100);
    }

    @Test
    void purchaseOperation_ok() {
        List<FruitTransaction> transactionList =
                List.of(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "Banana", 50));
        shopService.processTransactions(transactionList);
        int expected = 50;
        int actual = fruitStorage.getFruitQuantity("Banana");
        assertEquals(expected, actual);
    }

    @Test
    void balanceOperation_ok() {
        int expected = 200;
        List<FruitTransaction> transactionsList =
                List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "Banana", expected));
        shopService.processTransactions(transactionsList);
        int actual = fruitStorage.getFruitQuantity("Banana");
        assertEquals(expected, actual);
    }

    @Test
    void supplyOperation_ok() {
        List<FruitTransaction> transactionsList =
                List.of(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "Banana", 100));
        shopService.processTransactions(transactionsList);
        int expected = 200;
        int actual = fruitStorage.getFruitQuantity("Banana");
        assertEquals(expected, actual);
    }

    @Test
    void returnOperation_ok() {
        List<FruitTransaction> transactionsList =
                List.of(new FruitTransaction(FruitTransaction.Operation.RETURN, "Banana", 100));
        shopService.processTransactions(transactionsList);
        int expected = 200;
        int actual = fruitStorage.getFruitQuantity("Banana");
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        fruitStorage.setFruitBalance("Banana", 0);
    }
}
