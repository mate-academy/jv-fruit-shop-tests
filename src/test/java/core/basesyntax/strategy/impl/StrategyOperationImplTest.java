//package core.basesyntax.strategy.impl;
//
//import core.basesyntax.model.FruitTransaction;
//import core.basesyntax.strategy.OperationHandler;
//import junit.framework.TestCase;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class StrategyOperationImplTest extends TestCase {
//    Map<FruitTransaction.Operation, OperationHandler> operationOperationHandlerMap;
//
//    @Override
//    public void setUp() {
//        operationOperationHandlerMap = new HashMap<>();
//    }
//
//    public void balance_Null_notOk() {
//        OperationHandler balanceStrategyOperation = new BalanceStrategyOperationImpl();
//        balanceStrategyOperation.handle(null);
//    }
//}