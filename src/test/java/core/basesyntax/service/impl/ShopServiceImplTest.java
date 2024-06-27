package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.errors.ErrorMessages;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operation.BalanceOperation;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperation;
import core.basesyntax.strategy.operation.ReturnOperation;
import core.basesyntax.strategy.operation.SupplyOperation;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testing ShopServiceImpl")
class ShopServiceImplTest {
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceOperation(),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
            FruitTransaction.Operation.RETURN, new ReturnOperation(),
            FruitTransaction.Operation.SUPPLY, new SupplyOperation()
    );
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        FruitDao fruitDao = new FruitDaoImpl();
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(fruitDao, operationStrategy);
    }

    @Test
    void process_successful_ok() {
        List<FruitTransaction> transactions = getTransactions();
        Map<String, Integer> expectedQuantities = Map.of("apple", 90,"banana", 152);
        shopService.process(transactions);
        List<Fruit> actual = Storage.getFruits();
        actual.forEach(
                fruit -> assertEquals(expectedQuantities.get(fruit.getName()), fruit.getQuantity(),
                            "Method by key: " + fruit.getName() + " should return "
                                    + expectedQuantities.get(fruit.getName()))
        );
    }

    @Test
    void process_emptyTransactionsList_ok() {
        List<FruitTransaction> transactions = Collections.emptyList();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                shopService.process(transactions));
        String expectedMessage = ErrorMessages.TRANSACTION_LIST_CANNOT_BE_NULL_OR_EMPTY;
        assertEquals(expectedMessage, exception.getMessage(),
                getInfoMessage(expectedMessage, exception.getMessage())
        );
    }

    @Test
    void process_nullTransactionsList_ok() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                shopService.process(null));
        String expectedMessage = ErrorMessages.TRANSACTION_LIST_CANNOT_BE_NULL_OR_EMPTY;
        assertEquals(expectedMessage, exception.getMessage(),
                getInfoMessage(expectedMessage, exception.getMessage())
        );
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    private List<FruitTransaction> getTransactions() {
        return List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50)
        );
    }

    private String getInfoMessage(String expectedMessage, String actualMessage) {
        return "Test failed, expected: " + expectedMessage + " but actual " + actualMessage;
    }
}
