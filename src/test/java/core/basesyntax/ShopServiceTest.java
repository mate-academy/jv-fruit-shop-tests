package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import core.basesyntax.strategy.imlp.OperationStrategyImpl;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceTest {

    private Map<FruitTransaction.Operation, OperationHandler> operationHandlers;
    private OperationStrategy operationStrategy;
    private ShopServiceImpl shopService;

    @BeforeEach
    public void setUp() {
        operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation()
        );
        operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    public void afterEachTest() {
        shopService.getStorage().clear();
    }

    @Test
    void process_validValue_ok() {
        List<FruitTransaction> actual = List.of(
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10),
            new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 5)
        );
        Map<String, Integer> expected = Map.of(
                "banana", 45
        );
        shopService.process(actual);
        Assertions.assertEquals(shopService.getStorage(), expected);
    }

    @Test
    void process_emptyValue_Ok() {
        List<FruitTransaction> emptyList = Collections.emptyList();
        shopService.process(emptyList);
        Assertions.assertTrue(shopService.getStorage().isEmpty());
    }

    @Test
    void process_negativeQuantity_notOk() {
        List<FruitTransaction> negativeQuantity = List.of(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 15)
        );
        Assertions.assertThrows(RuntimeException.class, () -> {
            shopService.process(negativeQuantity);
        });
    }

    @Test
    void process_nullValue_notOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(null, null, 5)
        );
        Assertions.assertThrows(RuntimeException.class, () -> {
            shopService.process(transactions);
        });
    }
}
