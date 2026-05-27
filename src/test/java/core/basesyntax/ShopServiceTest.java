package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.handler.BalanceOperation;
import core.basesyntax.strategy.handler.TransactionStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceTest {
    private static final String CORRECT_NAME = "apple";
    private static final String NULL_NAME = null;
    private static final String EMPTY_NAME = "";

    private static final int CORRECT_QUANTITY = 10;
    private static final int NEGATIVE_QUANTITY = -10;
    private static final int ZERO_QUANTITY = 0;

    private static final FruitTransaction.Operation OPERATION = FruitTransaction.Operation.BALANCE;

    private static final Map<FruitTransaction.Operation, OperationHandler> operationHandlers
            = new HashMap<>();

    private static final FruitTransaction transaction = new FruitTransaction();
    private static final List<FruitTransaction> transactions = new ArrayList<>();

    private ShopService shopService;

    @BeforeAll
    static void strategySetUp() {
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
    }

    @BeforeEach
    void setUp() {
        Storage.getFruits().clear();
        transactions.clear();

        transaction.setOperation(OPERATION);

        TransactionStrategy operationStrategy = new TransactionStrategy(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void shopService_correctTransaction_ok() {
        transaction.setName(CORRECT_NAME);
        transaction.setQuantity(CORRECT_QUANTITY);

        transactions.add(transaction);
        shopService.process(transactions);

        int actualQuantity = Storage.getFruits().get(CORRECT_NAME);
        boolean actualIsContain = Storage.getFruits().containsKey(CORRECT_NAME);

        assertEquals(1, Storage.getFruits().size());
        assertEquals(CORRECT_QUANTITY, actualQuantity);
        assertTrue(actualIsContain);
    }

    @Test
    void shopService_negativeQuantity_notOk() {
        transaction.setName(CORRECT_NAME);
        transaction.setQuantity(NEGATIVE_QUANTITY);

        transactions.add(transaction);

        assertThrows(RuntimeException.class, ()
                -> shopService.process(transactions));
    }

    @Test
    void shopService_nullName_notOk() {
        transaction.setName(NULL_NAME);
        transaction.setQuantity(CORRECT_QUANTITY);

        transactions.add(transaction);

        assertEquals(0, Storage.getFruits().size());
        assertThrows(RuntimeException.class, ()
                -> shopService.process(transactions));
    }

    @Test
    void shopService_emptyName_notOk() {
        transaction.setName(EMPTY_NAME);
        transaction.setQuantity(CORRECT_QUANTITY);

        transactions.add(transaction);

        assertEquals(0, Storage.getFruits().size());
        assertThrows(RuntimeException.class, ()
                -> shopService.process(transactions));
    }

    @Test
    void shopService_zeroQuantity_ok() {
        transaction.setName(CORRECT_NAME);
        transaction.setQuantity(ZERO_QUANTITY);

        transactions.add(transaction);
        shopService.process(transactions);

        int actualQuantity = Storage.getFruits().get(CORRECT_NAME);

        assertEquals(1, Storage.getFruits().size());
        assertEquals(ZERO_QUANTITY, actualQuantity);
    }

}
