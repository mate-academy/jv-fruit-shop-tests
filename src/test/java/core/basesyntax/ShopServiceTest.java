package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.FruitStorage;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceTest {

    private OperationStrategy operationStrategy;
    private List<FruitTransaction> fruitTransactions = new ArrayList<>();
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());

        operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);

        fruitTransactions.add(0, new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 100));
        fruitTransactions.add(1, new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100));
        fruitTransactions.add(2, new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 50));
        fruitTransactions.add(3, new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 50));
        fruitTransactions.add(4, new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 50));
        fruitTransactions.add(5, new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 50));
        fruitTransactions.add(6, new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 100));
        fruitTransactions.add(7, new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 100));
    }

    @Test
    void shopService_shouldCalculateCorrectFruitCondition() {

        List<FruitTransaction> input = fruitTransactions;
        List<FruitStorage> expected = List.of(
                new FruitStorage("banana", 100),
                new FruitStorage("apple", 100));

        List<FruitStorage> result = shopService.process(input);
        assertEquals(expected, result);
    }

    @Test
    void shopService_shouldCreateEmptyListWithoutBalanceOperation() {

        List<FruitTransaction> input = fruitTransactions.subList(2, 8);
        List<FruitStorage> expected = List.of();

        List<FruitStorage> result = shopService.process(input);
        assertEquals(expected, result);
    }

    @Test
    void shopService_shouldThrowsNullPointerException() {

        List<FruitTransaction> input = new ArrayList<>();
        input.add(null);
        assertThrows(NullPointerException.class, () -> shopService.process(input));
    }
}
