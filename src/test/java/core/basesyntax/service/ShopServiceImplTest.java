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
import org.junit.Before;
import org.junit.Test;

public class ShopServiceImplTest {
    private final Map<Operation, OperationHandler> operationOperationHandlerMap = new HashMap<>();

    @Before
    public void before() {
        FruitDao fruitDao = new FruitDaoImpl();
        BalanceHandler balanceService = new BalanceHandler(fruitDao);
        PurchaseService purchaseService = new PurchaseService(fruitDao);
        SupplyService supplyService = new SupplyService(fruitDao);
        ReturnService returnService = new ReturnService(fruitDao);

        operationOperationHandlerMap.put(Operation.BALANCE, balanceService);
        operationOperationHandlerMap.put(Operation.PURCHASE, purchaseService);
        operationOperationHandlerMap.put(Operation.SUPPLY, supplyService);
        operationOperationHandlerMap.put(Operation.RETURN, returnService);
    }

    @Test
    public void transaction_correctOperations_Ok() {
        ShopService shopService = new ShopServiceImpl(operationOperationHandlerMap);
        FruitDao fruitDao = new FruitDaoImpl();

        FruitTransaction fruitTransactionBalance =
                new FruitTransaction(Operation.BALANCE,"orange,",70);
        OperationHandler expectedBalanceHandler = new BalanceHandler(fruitDao);
        assertEquals(expectedBalanceHandler.getClass(),
                shopService.transaction(fruitTransactionBalance).getClass());

        FruitTransaction fruitTransactionPurchase =
                new FruitTransaction(Operation.PURCHASE,"orange,",70);
        OperationHandler expectedPurchaseHandler = new PurchaseService(fruitDao);
        assertEquals(expectedPurchaseHandler.getClass(),
                shopService.transaction(fruitTransactionPurchase).getClass());

        FruitTransaction fruitTransactionSupply =
                new FruitTransaction(Operation.SUPPLY,"orange,",70);
        OperationHandler expectedSupplyHandler = new SupplyService(fruitDao);
        assertEquals(expectedSupplyHandler.getClass(),
                shopService.transaction(fruitTransactionSupply).getClass());

        FruitTransaction fruitTransactionReturn =
                new FruitTransaction(Operation.RETURN,"orange,",70);
        OperationHandler expectedReturnHandler = new ReturnService(fruitDao);
        assertEquals(expectedReturnHandler.getClass(),
                shopService.transaction(fruitTransactionReturn).getClass());
    }
}
