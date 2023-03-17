package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionsEvaluateService;
import core.basesyntax.strategy.impl.ReportStrategyImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionsEvaluateServiceImplTest {
    private static final String FRUIT_NAME = "banana";
    private static final int EXPECTED_AFTER_EVALUATE = 53;
    private static final TransactionsEvaluateService transactionsEvaluateService =
            new TransactionsEvaluateServiceImpl(new ReportStrategyImpl());
    private static final FruitTransaction BALANCE_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, FRUIT_NAME, 60);
    private static final FruitTransaction RETURN_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.RETURN, FRUIT_NAME, 21);
    private static final FruitTransaction SUPPLY_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, FRUIT_NAME, 7);
    private static final FruitTransaction PURCHASE_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, FRUIT_NAME, 35);

    @Before
    public void before() {
        Storage.fruits.clear();
    }

    @Test
    public void evaluate_allOperations_ok() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(BALANCE_TRANSACTION);
        transactions.add(RETURN_TRANSACTION);
        transactions.add(SUPPLY_TRANSACTION);
        transactions.add(PURCHASE_TRANSACTION);
        transactionsEvaluateService.evaluate(transactions);
        int actual = Storage.fruits.get(FRUIT_NAME);
        assertEquals(EXPECTED_AFTER_EVALUATE + " expected, but was " + actual,
                EXPECTED_AFTER_EVALUATE, actual);
    }
}
