package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.model.Transaction;
import core.basesyntax.repository.FruitDB;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.TransactionStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import core.basesyntax.strategy.impl.TransactionStrategyImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FruitServiceImplTest extends Assert {
    private static FruitService service;
    private static List<Transaction> transactions;
    private static final String FRUIT = "orange";
    private static final Integer AMOUNT = 33;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<Transaction.Operation, OperationHandler> operationsMap = Map.of(
                Transaction.Operation.RETURN, new ReturnOperationHandler(),
                Transaction.Operation.BALANCE, new BalanceOperationHandler(),
                Transaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                Transaction.Operation.SUPPLY, new SupplyOperationHandler());
        TransactionStrategy transactionStrategy = new TransactionStrategyImpl(operationsMap);
        service = new FruitServiceImpl(fruitDao, transactionStrategy);
    }

    @Before
    public void setUp() {
        transactions = new ArrayList<>();
        transactions.add(new Transaction(Transaction.Operation.SUPPLY, FRUIT, AMOUNT));
    }

    @After
    public void tearDown() {
        transactions.clear();
    }

    @Test
    public void updateAll_purchaseMoreThenAvailable_notOk() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Client wants to buy more products than are available");
        transactions.add(new Transaction(Transaction.Operation.PURCHASE, FRUIT, Integer.MIN_VALUE));
        service.updateAll(transactions);
    }

    @Test
    public void updateAll_regularCaseUpdate_Ok() {
        FruitDB.fruitsOnStock.clear();
        service.updateAll(transactions);
        assertEquals(AMOUNT, FruitDB.fruitsOnStock.get(FRUIT));
    }
}

