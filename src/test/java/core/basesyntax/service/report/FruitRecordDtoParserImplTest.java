package core.basesyntax.service.report;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Storage;
import core.basesyntax.model.TypeOperations;
import core.basesyntax.service.oparation.BalanceHandler;
import core.basesyntax.service.oparation.OperationHandler;
import core.basesyntax.service.oparation.PurchaseHandler;
import core.basesyntax.service.oparation.ReturnHandler;
import core.basesyntax.service.oparation.SupplyHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordDtoParserImplTest {
    private static FruitRecordDtoParser fruitRecordDtoParser;
    private static Map<String, OperationHandler> operationHandlersMap;
    private static List<String> list;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(TypeOperations.BALANCE.get(), new BalanceHandler());
        operationHandlersMap.put(TypeOperations.SUPPLY.get(), new SupplyHandler());
        operationHandlersMap.put(TypeOperations.PURCHASE.get(), new PurchaseHandler());
        operationHandlersMap.put(TypeOperations.RETURN.get(), new ReturnHandler());
        fruitRecordDtoParser = new FruitRecordDtoParserImpl(operationHandlersMap);
        list = new ArrayList<>();
        list.add("b,banana,20");
        list.add("b,apple,100");
        list.add("s,banana,100");
        list.add("p,banana,13");
        list.add("r,apple,10");
        list.add("p,apple,20");
        list.add("p,banana,5");
        list.add("s,banana,50");
        Storage.getFruitStorageMap().put(new Fruit("banana"), 0);
        Storage.getFruitStorageMap().put(new Fruit("apple"), 0);
    }

    @Test
    public void fruitRecordDtoParserImplTest_Ok() {
        fruitRecordDtoParser.createFruitRecordDto(list);
        int actual = Storage.getFruitStorageMap().get(new Fruit("banana"));
        int expected = 152;
        assertEquals(actual, expected);
    }

    @Test
    public void fruitRecordDtoParserImplTest_notOk() {
        fruitRecordDtoParser.createFruitRecordDto(list);
        int actual = Storage.getFruitStorageMap().get(new Fruit("apple"));
        int expected = 152;
        assertNotEquals(actual, expected);
    }
}
