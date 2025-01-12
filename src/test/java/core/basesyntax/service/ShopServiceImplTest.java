package core.basesyntax.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import core.basesyntax.model.FruitTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShopServiceImplTest {
    private ShopService shopService;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
        //перевіряє, чи правильно обробляються валідні транзакції.
    void process_validTransactions_ok() {
        // Створюється об'єкт транзакції з операцією BALANCE,  apple - 50
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 50);
        // Реалізація обробника операції BALANCE
        OperationHandler balanceHandler = new BalanceOperation();
        // Ініціалізація стратегії операцій з реальною мапою
        OperationStrategy operationStrategy = new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.BALANCE, balanceHandler));
        // Ініціалізація ShopService з реальною стратегією
        ShopService shopService = new ShopServiceImpl(operationStrategy);
        // Виклик методу process з переданим списком транзакцій
        shopService.process(List.of(transaction));
        // Перевірка, чи обробник правильно обробив операцію
        assertEquals(50,
                ((ShopServiceImpl) shopService).getInventory().get("apple"));
    }

    @Test
        //перевіряє, чи сервіс коректно обробляє порожній список транзакцій
    void process_emptyTransactions_noHandlersCalled() {
        // Ініціалізуємо початковий стан (порожній інвентар)
        List<FruitTransaction> emptyTransactions = Collections.emptyList();
        // Викликаємо метод process із порожнім списком транзакцій
        shopService.process(emptyTransactions);
        // Перевіряємо, що інвентар магазину залишився порожнім після обробки
        Map<String, Integer> inventory = ((ShopServiceImpl) shopService).getInventory();
        assertTrue(inventory.isEmpty(),
                "Inventory should remain empty "
                        + "when processing an empty transaction list");
    }
}
