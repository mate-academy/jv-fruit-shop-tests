package basesyntax.service;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import core.basesyntax.service.impl.TransactionServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationHandlerStrategy;
import core.basesyntax.strategy.impl.OperationHandlerBalanceImpl;
import core.basesyntax.strategy.impl.OperationHandlerPurchaseImpl;
import core.basesyntax.strategy.impl.OperationHandlerReturnImpl;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.impl.OperationHandlerSupplyImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static core.basesyntax.model.Operation.BALANCE;
import static core.basesyntax.model.Operation.PURCHASE;
import static core.basesyntax.model.Operation.RETURN;
import static core.basesyntax.model.Operation.SUPPLY;

public class TransactionServiceImplTest {
    private static List<FruitTransaction> testTransactionData;
    private static Map<String, OperationHandler> testDateOperation;
    private static OperationHandlerStrategy testStrategy;
    private static TransactionService testTransaction;

    @BeforeClass
    public static void setUp(){
        testTransactionData = new ArrayList<>();
        testTransactionData.add(new FruitTransaction("b", new Fruit("banana"), 100));
        testTransactionData.add(new FruitTransaction("p", new Fruit("banana"), 50));
        testTransactionData.add(new FruitTransaction("r", new Fruit("banana"), 250));
        testTransactionData.add(new FruitTransaction("s", new Fruit("apple"), 100));
        testDateOperation = new HashMap<>();
        testDateOperation.put(BALANCE.chooseOperation(), new OperationHandlerBalanceImpl());
        testDateOperation.put(PURCHASE.chooseOperation(), new OperationHandlerPurchaseImpl());
        testDateOperation.put(RETURN.chooseOperation(), new OperationHandlerReturnImpl());
        testDateOperation.put(SUPPLY.chooseOperation(), new OperationHandlerSupplyImpl());
        testStrategy = new OperationHandlerStrategyImpl(testDateOperation);
        testTransaction = new TransactionServiceImpl(testStrategy);
        testTransaction.addTransferToStorage(testTransactionData);

    }
    @Test
    public void setTestTransaction_ok(){
        int expected = 300;
        int actual = FruitStorage.fruitStorage.get(new Fruit("banana"));
        Assert.assertEquals(expected,actual);
    }
}
