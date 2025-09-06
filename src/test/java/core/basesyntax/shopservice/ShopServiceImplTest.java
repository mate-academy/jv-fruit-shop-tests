package core.basesyntax.shopservice;

import core.basesyntax.db.Storage;
import core.basesyntax.fruittransactionhandler.BalanceHandler;
import core.basesyntax.fruittransactionhandler.OperationHandler;
import core.basesyntax.fruittransactionhandler.PurchaseHandler;
import core.basesyntax.fruittransactionhandler.ReturnHandler;
import core.basesyntax.fruittransactionhandler.SupplyHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.TypeOfTransaction;
import core.basesyntax.operationstrategy.OperationStrategyImpl;
import core.basesyntax.operationstrategy.OperationStrategyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ShopServiceImplTest {
    private static final Map<TypeOfTransaction, OperationHandler>
            operationHandlerMap = new HashMap<>();
    private static final OperationStrategyService operationStrategyService
            = new OperationStrategyImpl(operationHandlerMap);
    private static final ShopService shopService =
            new ShopServiceImpl(operationStrategyService);
    private static final FruitTransaction fruitTransactionNo1 = new FruitTransaction();
    private static final FruitTransaction fruitTransactionNo2 = new FruitTransaction();
    private static final FruitTransaction fruitTransactionNo3 = new FruitTransaction();
    private static final FruitTransaction fruitTransactionNo4 = new FruitTransaction();
    private static final List<FruitTransaction> fruitTransactions = new ArrayList<>();
    private static final Storage storageService = new Storage();

    @Test
    void getHandler_InputListIsNull_notOk() {

        Assertions.assertThrows(RuntimeException.class, () -> shopService.process(null));
    }

    @Test
    void getHandler_ok() {
        fruitTransactionNo1.setTypeOfTransaction(TypeOfTransaction.R);
        fruitTransactionNo1.setFruit("banana");
        fruitTransactionNo1.setQuantity(10);
        fruitTransactionNo2.setTypeOfTransaction(TypeOfTransaction.B);
        fruitTransactionNo2.setFruit("apple");
        fruitTransactionNo2.setQuantity(40);
        fruitTransactionNo3.setFruit("grapefruit");
        fruitTransactionNo3.setTypeOfTransaction(TypeOfTransaction.S);
        fruitTransactionNo3.setQuantity(10);
        fruitTransactionNo4.setTypeOfTransaction(TypeOfTransaction.P);
        fruitTransactionNo4.setFruit("apple");
        fruitTransactionNo4.setQuantity(10);
        fruitTransactions.add(fruitTransactionNo1);
        fruitTransactions.add(fruitTransactionNo2);
        fruitTransactions.add(fruitTransactionNo3);
        operationHandlerMap.put(TypeOfTransaction.B, new BalanceHandler(storageService));
        operationHandlerMap.put(TypeOfTransaction.P, new PurchaseHandler(storageService));
        operationHandlerMap.put(TypeOfTransaction.R, new ReturnHandler(storageService));
        operationHandlerMap.put(TypeOfTransaction.S, new SupplyHandler(storageService));
        shopService.process(fruitTransactions);
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("banana",10);
        expectedMap.put("apple",40);
        expectedMap.put("grapefruit",10);
        Assertions.assertEquals(expectedMap,storageService.getDataStorageMap());
    }
}
