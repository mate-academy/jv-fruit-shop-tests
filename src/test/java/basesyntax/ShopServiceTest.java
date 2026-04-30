package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.impl.BalanceOperation;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.PurchaseOperation;
import core.basesyntax.service.impl.ReturnOperation;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.service.impl.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceTest {

    private static final String BANANA = "banana";
    private static final String APPLE = "apple";

    private ShopServiceImpl service;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        service = new ShopServiceImpl(strategy);
    }

    @Test
    void process_balanceAndPurchase_shouldDecreaseStock() {
        FruitTransaction balance = create(FruitTransaction.Operation.BALANCE, BANANA, 100);
        FruitTransaction purchase = create(FruitTransaction.Operation.PURCHASE, BANANA, 30);

        service.process(List.of(balance, purchase));

        assertEquals(70, service.getStorage().get(BANANA));
    }

    @Test
    void process_multipleOperations_shouldHandleDifferentFruits() {
        service.process(List.of(
                create(FruitTransaction.Operation.BALANCE, BANANA, 50),
                create(FruitTransaction.Operation.SUPPLY, BANANA, 20),
                create(FruitTransaction.Operation.BALANCE, APPLE, 40)
        ));

        assertEquals(70, service.getStorage().get(BANANA));
        assertEquals(40, service.getStorage().get(APPLE));
    }

    @Test
    void process_return_shouldIncreaseStock() {
        service.process(List.of(
                create(FruitTransaction.Operation.BALANCE, BANANA, 50),
                create(FruitTransaction.Operation.RETURN, BANANA, 10)
        ));

        assertEquals(60, service.getStorage().get(BANANA));
    }

    @Test
    void process_supplyWithoutBalance_shouldAddFruit() {
        service.process(List.of(
                create(FruitTransaction.Operation.SUPPLY, APPLE, 30)
        ));

        assertEquals(30, service.getStorage().get(APPLE));
    }

    @Test
    void process_purchaseWithoutBalance_shouldThrowException() {
        assertThrows(RuntimeException.class,
                () -> service.process(List.of(
                        create(FruitTransaction.Operation.PURCHASE, BANANA, 10)
                )));
    }

    @Test
    void process_emptyList_shouldDoNothing() {
        Map<String, Integer> before = new HashMap<>(service.getStorage());
        service.process(List.of());
        assertEquals(before, service.getStorage());
    }

    private FruitTransaction create(FruitTransaction.Operation operation,
                                    String fruit, int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(operation);
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }
}
