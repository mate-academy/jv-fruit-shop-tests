package core.basesyntax;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.services.OperationStrategy;
import core.basesyntax.services.OperationStrategyImpl;
import core.basesyntax.services.operation.*;
import core.basesyntax.services.transaction.model.ProductTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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
        Class<? extends OperationHandler> actual0 =  strategy.get(ProductTransaction.Operation.BALANCE).getClass();
        Class<BalanceOperation> expected0 = BalanceOperation.class;
        Assert.assertEquals(expected0, actual0);
        Class<? extends OperationHandler> actual1 =  strategy.get(ProductTransaction.Operation.PURCHASE).getClass();
        Class<PurchaseOperation> expected1 = PurchaseOperation.class;
        Assert.assertEquals(expected1, actual1);
        Class<? extends OperationHandler> actual2 =  strategy.get(ProductTransaction.Operation.SUPPLY).getClass();
        Class<SupplyOperation> expected2 = SupplyOperation.class;
        Assert.assertEquals(expected2, actual2);
        Class<? extends OperationHandler> actual3 =  strategy.get(ProductTransaction.Operation.RETURN).getClass();
        Class<ReturnOperation> expected3 = ReturnOperation.class;
        Assert.assertEquals(expected3, actual3);
    }
}
