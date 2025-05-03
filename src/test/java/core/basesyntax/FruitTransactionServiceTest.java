package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopDao;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.impl.FruitShopDaoImpl;
import core.basesyntax.service.impl.FruitTransactionServiceImpl;
import core.basesyntax.strategy.FruitStrategy;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceHandlerImpl;
import core.basesyntax.strategy.impl.FruitStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseHandlerImpl;
import core.basesyntax.strategy.impl.ReturnHandlerImpl;
import core.basesyntax.strategy.impl.SupplyHandlerImpl;
import core.basesyntax.utility.ListService;
import core.basesyntax.utility.impl.ListServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitTransactionServiceTest {
    private static final FruitTransaction ORANGE_FRUIT_BALANCE =
            new FruitTransaction(FruitTransaction.Operation.BALANCE,
            "orange", 50);
    private static final FruitTransaction ORANGE_FRUIT_SUPPLY =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY,
            "orange", 150);
    private static final FruitTransaction ORANGE_FRUIT_PURCHASE =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                    "orange", 20);
    private static final FruitTransaction ORANGE_FRUIT_RETURN =
            new FruitTransaction(FruitTransaction.Operation.RETURN,
                    "orange", 10);
    private static final FruitTransaction BANANA_FRUIT_BALANCE =
            new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    "banana", 250);
    private static final FruitTransaction BANANA_FRUIT_SUPPLY =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                    "banana", 350);
    private static final FruitTransaction BANANA_FRUIT_PURCHASE =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                    "banana", 100);
    private static final FruitTransaction BANANA_FRUIT_RETURN =
            new FruitTransaction(FruitTransaction.Operation.RETURN,
                    "banana", 50);
    private static final int BANANA_QUANTITY_AFTER_PROCESSING = 550;
    private static final int ORANGE_QUANTITY_AFTER_PROCESSING = 190;
    private static final String BANANA = "banana";
    private static final String ORANGE = "orange";
    private static FruitTransactionService fruitTransactionService;
    private static FruitShopDao fruitShopDao;
    private static FruitStrategy fruitStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    @BeforeAll
    static void initialize_variables() {
        operationHandlers = new HashMap<>();
        ListService listService = new ListServiceImpl();
        operationHandlers.put(FruitTransaction.Operation.BALANCE,
                new BalanceHandlerImpl(listService));
        operationHandlers.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandlerImpl(listService));
        operationHandlers.put(FruitTransaction.Operation.RETURN,
                new ReturnHandlerImpl(listService));
        operationHandlers.put(FruitTransaction.Operation.SUPPLY,
                new SupplyHandlerImpl(listService));
        fruitShopDao = new FruitShopDaoImpl();
        fruitStrategy = new FruitStrategyImpl(operationHandlers);
        fruitTransactionService = new FruitTransactionServiceImpl(fruitShopDao, fruitStrategy);
    }

    @Test
    void fruitTransactionService_processTransaction_nullNotOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> fruitTransactionService.processTransactions(null));
    }

    @Test
    void fruitTransactionService_processTransaction_passFruitListOk() {
        List<FruitTransaction> fruitsToProcess = new ArrayList<>();
        fruitsToProcess.add(ORANGE_FRUIT_BALANCE);
        fruitsToProcess.add(ORANGE_FRUIT_SUPPLY);
        fruitsToProcess.add(ORANGE_FRUIT_PURCHASE);
        fruitsToProcess.add(ORANGE_FRUIT_RETURN);
        fruitsToProcess.add(BANANA_FRUIT_BALANCE);
        fruitsToProcess.add(BANANA_FRUIT_SUPPLY);
        fruitsToProcess.add(BANANA_FRUIT_PURCHASE);
        fruitsToProcess.add(BANANA_FRUIT_RETURN);
        Map<String, Integer> fruitsAfterProcessing =
                fruitTransactionService.processTransactions(fruitsToProcess);
        Assertions.assertEquals(BANANA_QUANTITY_AFTER_PROCESSING,
                fruitsAfterProcessing.get(BANANA));
        Assertions.assertEquals(ORANGE_QUANTITY_AFTER_PROCESSING,
                fruitsAfterProcessing.get(ORANGE));
    }
}
