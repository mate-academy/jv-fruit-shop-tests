package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operations.BalanceHandler;
import core.basesyntax.service.operations.Operation;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseService;
import core.basesyntax.service.operations.ReturnService;
import core.basesyntax.service.operations.SupplyService;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShopServiceImplTest {
    private static Map<Operation, OperationHandler> operationOperationHandlerMap;
    private static ShopService shopService;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        operationOperationHandlerMap = new HashMap<>();

        BalanceHandler balanceService = new BalanceHandler(fruitDao);
        operationOperationHandlerMap.put(Operation.BALANCE, balanceService);

        PurchaseService purchaseService = new PurchaseService(fruitDao);
        operationOperationHandlerMap.put(Operation.PURCHASE, purchaseService);

        SupplyService supplyService = new SupplyService(fruitDao);
        operationOperationHandlerMap.put(Operation.SUPPLY, supplyService);

        ReturnService returnService = new ReturnService(fruitDao);
        operationOperationHandlerMap.put(Operation.RETURN, returnService);

        shopService = new ShopServiceImpl(operationOperationHandlerMap);
    }

    @Test
    public void transaction_correctBalanceOperation_ok() {
        FruitTransaction fruitTransactionBalance =
                new FruitTransaction(Operation.BALANCE,"orange,",70);
        OperationHandler expectedBalanceHandler = new BalanceHandler(fruitDao);
        assertEquals(expectedBalanceHandler.getClass(),
                shopService.transaction(fruitTransactionBalance).getClass());
    }

    @Test
    public void transaction_correctPurchaseOperation_ok() {
        FruitTransaction fruitTransactionPurchase =
                new FruitTransaction(Operation.PURCHASE,"orange,",70);
        OperationHandler expectedPurchaseHandler = new PurchaseService(fruitDao);
        assertEquals(expectedPurchaseHandler.getClass(),
                shopService.transaction(fruitTransactionPurchase).getClass());
    }

    @Test
    public void transaction_correctSupplyOperation_ok() {
        FruitTransaction fruitTransactionSupply =
                new FruitTransaction(Operation.SUPPLY,"orange,",70);
        OperationHandler expectedSupplyHandler = new SupplyService(fruitDao);
        assertEquals(expectedSupplyHandler.getClass(),
                shopService.transaction(fruitTransactionSupply).getClass());
    }

    @Test
    public void transaction_correctReturnOperation_ok() {
        FruitTransaction fruitTransactionReturn =
                new FruitTransaction(Operation.RETURN,"orange,",70);
        OperationHandler expectedReturnHandler = new ReturnService(fruitDao);
        assertEquals(expectedReturnHandler.getClass(),
                shopService.transaction(fruitTransactionReturn).getClass());
    }
}
