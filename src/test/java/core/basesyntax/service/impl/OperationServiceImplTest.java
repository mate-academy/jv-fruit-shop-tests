package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.OperationService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseHandlerImpl;
import core.basesyntax.strategy.impl.ReturnHandlerImpl;
import core.basesyntax.strategy.impl.SupplyHandlerImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationServiceImplTest {
    private static final FruitDao fruitDao = new FruitDaoImpl();
    private static OperationService operationService;

    @BeforeClass
    public static void beforeClass() {
        Map<Transaction.Operation, OperationHandler> mapStrategy = new HashMap<>();
        mapStrategy.put(Transaction.Operation.BALANCE, new BalanceHandlerImpl(fruitDao));
        mapStrategy.put(Transaction.Operation.SUPPLY, new SupplyHandlerImpl(fruitDao));
        mapStrategy.put(Transaction.Operation.PURCHASE, new PurchaseHandlerImpl(fruitDao));
        mapStrategy.put(Transaction.Operation.RETURN, new ReturnHandlerImpl(fruitDao));
        operationService =
                new OperationServiceImpl(mapStrategy);
    }

    @Test
    public void getBalanceOperation_ok() {
        assertEquals(BalanceHandlerImpl.class,
                operationService.getHandler(Transaction.Operation.BALANCE).getClass());
    }

    @Test
    public void getSupplyOperation_ok() {
        assertEquals(SupplyHandlerImpl.class,
                operationService.getHandler(Transaction.Operation.SUPPLY).getClass());
    }

    @Test
    public void getPurchaseOperation_ok() {
        assertEquals(PurchaseHandlerImpl.class,
                operationService.getHandler(Transaction.Operation.PURCHASE).getClass());
    }

    @Test
    public void getReturnOperation_ok() {
        assertEquals(ReturnHandlerImpl.class,
                operationService.getHandler(Transaction.Operation.RETURN).getClass());
    }
}
