package core.basesyntax;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.services.OperationStrategy;
import core.basesyntax.services.OperationStrategyImpl;
import core.basesyntax.services.operation.BalanceOperation;
import core.basesyntax.services.operation.OperationHandler;
import core.basesyntax.services.operation.PurchaseOperation;
import core.basesyntax.services.operation.ReturnOperation;
import core.basesyntax.services.operation.SupplyOperation;
import core.basesyntax.services.transaction.model.ProductTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTests {
    private static OperationStrategy strategy;
    private static ProductDao dao;

    @BeforeClass
    public static void setStrategy() {
        dao = new ProductDaoImpl();
        Map<ProductTransaction.Operation, OperationHandler> operationMap = new HashMap<>();
        operationMap.put(ProductTransaction.Operation.BALANCE, new BalanceOperation(dao));
        operationMap.put(ProductTransaction.Operation.PURCHASE, new PurchaseOperation(dao));
        operationMap.put(ProductTransaction.Operation.SUPPLY, new SupplyOperation(dao));
        operationMap.put(ProductTransaction.Operation.RETURN, new ReturnOperation(dao));

        strategy = new OperationStrategyImpl(operationMap);
    }

    @Test
    public void getAllOperation_Ok() {
        Assert.assertEquals(BalanceOperation.class,
                strategy.get(ProductTransaction.Operation.BALANCE).getClass());

        Assert.assertEquals(PurchaseOperation.class,
                strategy.get(ProductTransaction.Operation.PURCHASE).getClass());

        Assert.assertEquals(SupplyOperation.class,
                strategy.get(ProductTransaction.Operation.SUPPLY).getClass());

        Assert.assertEquals(ReturnOperation.class,
                strategy.get(ProductTransaction.Operation.RETURN).getClass());
    }
}
