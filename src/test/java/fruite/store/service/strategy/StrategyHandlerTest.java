package fruite.store.service.strategy;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrategyHandlerTest {
    StrategyHandler strategyHandler = new StrategyHandler();

    @BeforeClass
    public static void beforeClass() throws Exception {
        StrategyHandler.operationTypeStrategy.put("b", new BalanceOperationHandler());
        StrategyHandler.operationTypeStrategy.put("s", new SupplyOperationHandler());
        StrategyHandler.operationTypeStrategy.put("p", new PurchaseOperationHandler());
        StrategyHandler.operationTypeStrategy.put("r", new ReturnOperationHandler());
    }

    @Test(expected = RuntimeException.class)
    public void doSpecialOperationOnFruits_invalidOperationType_notOk() {
        strategyHandler.doSpecialOperationOnFruits("b1", "banana", "100");
    }

    @Test(expected = RuntimeException.class)
    public void doSpecialOperationOnFruits_invalidFruitName_notOk() {
        strategyHandler.doSpecialOperationOnFruits("b", "ba123nana!", "100");
    }

    @Test(expected = RuntimeException.class)
    public void doSpecialOperationOnFruits_invalidQuantity_notOk() {
        strategyHandler.doSpecialOperationOnFruits("b", "banana", "12ff");
    }

    @Test(expected = RuntimeException.class)
    public void doSpecialOperationOnFruits_unknownOperationType_notOk() {
        strategyHandler.doSpecialOperationOnFruits("q", "banana", "50");
    }

    @Test
    public void doSpecialOperationOnFruits_validData_ok() {
        boolean actualBalanceType = strategyHandler.doSpecialOperationOnFruits("b", "banana", "100");
        boolean actualSupplyType = strategyHandler.doSpecialOperationOnFruits("s", "banana", "100");
        boolean actualPurchaseType = strategyHandler.doSpecialOperationOnFruits("p", "banana", "50");
        boolean actualReturnType = strategyHandler.doSpecialOperationOnFruits("r", "banana", "10");
        Assert.assertTrue(actualBalanceType);
        Assert.assertTrue(actualSupplyType);
        Assert.assertTrue(actualPurchaseType);
        Assert.assertTrue(actualReturnType);
    }
}