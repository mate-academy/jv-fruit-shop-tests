package core.service.market;

import core.model.FruitRecord;
import core.model.OperationType;
import core.service.operation.BalanceOperationTypeHandler;
import core.service.operation.OperationTypeHandler;
import core.service.operation.PurchaseOperationTypeHandler;
import core.service.operation.ReturnOperationTypeHandler;
import core.service.operation.SupplyOperationHandler;
import core.service.strategy.OperationTypeStrategy;
import core.service.strategy.OperationTypeStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MarketServiceImplTest {
    private MarketService marketService;
    private OperationTypeStrategy strategy;

    @Before
    public void setUp() throws Exception {
        Map<OperationType, OperationTypeHandler> operationTypeHandlerMap = new HashMap<>();
        operationTypeHandlerMap.put(OperationType.BALANCE, new BalanceOperationTypeHandler());
        operationTypeHandlerMap.put(OperationType.PURCHASE, new PurchaseOperationTypeHandler());
        operationTypeHandlerMap.put(OperationType.SUPPLY, new SupplyOperationHandler());
        operationTypeHandlerMap.put(OperationType.RETURN, new ReturnOperationTypeHandler());
        strategy = new OperationTypeStrategyImpl(operationTypeHandlerMap);
        marketService = new MarketServiceImpl(strategy);
    }

    @Test
    public void applyOperations() {
        List<FruitRecord> fruitRecordList = new ArrayList<>();
        fruitRecordList.add(new FruitRecord("r", "apple", 10));
        fruitRecordList.add(new FruitRecord("b", "banana", 35));
        List<FruitRecord> actual = marketService.applyOperations(fruitRecordList);
        Assert.assertEquals(fruitRecordList, actual);
    }
}
