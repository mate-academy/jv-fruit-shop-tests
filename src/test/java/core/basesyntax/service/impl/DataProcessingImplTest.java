package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataProcessing;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operation.BalanceHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseHandler;
import core.basesyntax.strategy.operation.ReturnHandler;
import core.basesyntax.strategy.operation.SupplyHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class DataProcessingImplTest {
    private final FruitDao fruitDao = new FruitDaoImpl();

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void convertDataIntoTransaction_Ok() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler(fruitDao));

        List<String> data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("b,banana,20");
        data.add("b,apple,100");
        data.add("s,banana,100");
        data.add("p,banana,13");
        data.add("r,apple,10");
        data.add("p,apple,20");
        data.add("p,banana,5");
        data.add("s,banana,50");

        // transaction code: "transaction" + operationFirstLetter + fruitFirstLetter + amount
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
        transactionRA10.setQuantity(10);

        FruitTransaction transactionPA20 = new FruitTransaction();
        transactionPA20.setOperation(FruitTransaction.Operation.PURCHASE);
        transactionPA20.setFruit("apple");
        transactionPA20.setQuantity(20);

        FruitTransaction transactionPB5 = new FruitTransaction();
        transactionPB5.setOperation(FruitTransaction.Operation.PURCHASE);
        transactionPB5.setFruit("banana");
        transactionPB5.setQuantity(5);

        FruitTransaction transactionSB50 = new FruitTransaction();
        transactionSB50.setOperation(FruitTransaction.Operation.SUPPLY);
        transactionSB50.setFruit("banana");
        transactionSB50.setQuantity(50);

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(transactionBB20);
        expected.add(transactionBA100);
        expected.add(transactionSB100);
        expected.add(transactionPB13);
        expected.add(transactionRA10);
        expected.add(transactionPA20);
        expected.add(transactionPB5);
        expected.add(transactionSB50);

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        DataProcessing dataProcessing = new DataProcessingImpl(operationStrategy);
        List<FruitTransaction> actual = dataProcessing.convertDataIntoTransaction(data);

        assertEquals(expected, actual);
    }
}
