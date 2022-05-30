package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.FruitTransactionParser;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.handler.BalanceHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

public class FruitTransactionParserImplTest {
    private static final FruitDao fruitDao = new FruitDaoImpl();
    private static FruitService fruitService;

    @BeforeAll
    static void beforeAll() {
        fruitService = new FruitServiceImpl(fruitDao);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void parseTransactions_ok() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(
                FruitTransaction.Operation.BALANCE, new BalanceHandler(fruitService));
        operationHandlerMap.put(
                FruitTransaction.Operation.SUPPLY, new BalanceHandler(fruitService));
        operationHandlerMap.put(
                FruitTransaction.Operation.PURCHASE, new BalanceHandler(fruitService));
        operationHandlerMap.put(
                FruitTransaction.Operation.RETURN, new BalanceHandler(fruitService));

        List<String> dataParse = new ArrayList<>();
        dataParse.add("type,fruit,quantity");
        dataParse.add("b,banana,20");
        dataParse.add("b,apple,100");
        dataParse.add("s,banana,100");
        dataParse.add("p,banana,13");
        dataParse.add("r,apple,10");
        dataParse.add("p,apple,20");
        dataParse.add("p,banana,5");
        dataParse.add("s,banana,50");

        FruitTransaction transactionBB20 = new FruitTransaction();
        transactionBB20.setOperation(FruitTransaction.Operation.BALANCE);
        transactionBB20.setFruit("banana");
        transactionBB20.setQuantity(20);

        FruitTransaction transactionBA100 = new FruitTransaction();
        transactionBA100.setOperation(FruitTransaction.Operation.BALANCE);
        transactionBA100.setFruit("apple");
        transactionBA100.setQuantity(100);

        FruitTransaction transactionSB100 = new FruitTransaction();
        transactionSB100.setOperation(FruitTransaction.Operation.SUPPLY);
        transactionSB100.setFruit("banana");
        transactionSB100.setQuantity(100);

        FruitTransaction transactionPB13 = new FruitTransaction();
        transactionPB13.setOperation(FruitTransaction.Operation.PURCHASE);
        transactionPB13.setFruit("banana");
        transactionPB13.setQuantity(13);

        FruitTransaction transactionRA10 = new FruitTransaction();
        transactionRA10.setOperation(FruitTransaction.Operation.RETURN);
        transactionRA10.setFruit("apple");
        transactionRA10.setQuantity(20);

        FruitTransaction transactionPB5 = new FruitTransaction();
        transactionPB5.setOperation(FruitTransaction.Operation.PURCHASE);
        transactionPB5.setFruit("banana");
        transactionPB5.setQuantity(5);

        FruitTransaction transactionSB50 = new FruitTransaction();
        transactionSB50.setOperation(FruitTransaction.Operation.SUPPLY);
        transactionSB50.setFruit("banana");
        transactionSB50.setQuantity(20);

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(transactionBB20);
        expected.add(transactionBA100);
        expected.add(transactionSB100);
        expected.add(transactionPB13);
        expected.add(transactionRA10);
        expected.add(transactionPB5);
        expected.add(transactionSB50);

        OperationStrategy operationStrategy =
                new OperationStrategyImpl(operationHandlerMap);
        FruitTransactionParser fruitTransactionParser =
                new FruitTransactionParserImpl(operationStrategy);
        List<FruitTransaction> actual = fruitTransactionParser.parse(dataParse);

        assertEquals(expected,actual);
    }
}
