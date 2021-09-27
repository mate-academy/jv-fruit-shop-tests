package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.fruitrecord.FruitRecord;
import core.basesyntax.service.activities.ActivityHandler;
import core.basesyntax.service.activities.BalanceHandler;
import core.basesyntax.service.activities.PurchaseHandler;
import core.basesyntax.service.activities.ReturnHandler;
import core.basesyntax.service.activities.SupplyHandler;
import core.basesyntax.service.activities.TypeOfActivities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class FruitServiceImplTest {
    private static ActivitiesStrategy strategy;
    private static Map<TypeOfActivities, ActivityHandler> typeOfActivitiesMap;
    private static FruitService fruitService;
    private static List<FruitRecord> records;
    private Object expected;

    @Before
    public void setUp() {
        Storage.FRUITS_QUANTITY.clear();
        typeOfActivitiesMap = new HashMap<>();
        typeOfActivitiesMap.put(TypeOfActivities.BALANCE, new BalanceHandler());
        typeOfActivitiesMap.put(TypeOfActivities.SUPPLY, new SupplyHandler());
        typeOfActivitiesMap.put(TypeOfActivities.PURCHASE, new PurchaseHandler());
        typeOfActivitiesMap.put(TypeOfActivities.RETURN, new ReturnHandler());
        strategy = new ActivitiesStrategyImpl(typeOfActivitiesMap);
        records = new ArrayList<>();
        FruitRecord recordOrangeBalance = new FruitRecord(new String[]{"b", "orange", "100"});
        FruitRecord recordOrangeReturn = new FruitRecord(new String[]{"r", "orange", "8"});
        FruitRecord recordOrangeSupply = new FruitRecord(new String[]{"s", "orange", "10"});
        FruitRecord recordOrangePurchase = new FruitRecord(new String[]{"p", "orange", "100"});
        FruitRecord recordCherryBalance = new FruitRecord(new String[]{"b", "cherry", "8"});
        FruitRecord recordCherryReturn = new FruitRecord(new String[]{"r", "cherry", "26"});
        FruitRecord recordCherrySupply = new FruitRecord(new String[]{"s", "cherry", "1011"});
        FruitRecord recordCherryPurchase = new FruitRecord(new String[]{"p", "cherry", "1000"});
        records.add(recordOrangeBalance);
        records.add(recordOrangeReturn);
        records.add(recordOrangeSupply);
        records.add(recordOrangePurchase);
        records.add(recordCherryBalance);
        records.add(recordCherryReturn);
        records.add(recordCherrySupply);
        records.add(recordCherryPurchase);
    }

    @Test
    public void fruitService_normalData_Ok() {
        fruitService = new FruitServiceImpl(strategy);
        fruitService.recordToMap(records);
        assertTrue("Missing key 'orange'",
                Storage.FRUITS_QUANTITY.containsKey("orange")
                && Storage.FRUITS_QUANTITY.get("orange") == 18);
        assertTrue("missing key 'cherry'",Storage.FRUITS_QUANTITY.containsKey("cherry")
                && Storage.FRUITS_QUANTITY.get("cherry") == 45);
        assertEquals("Wrong size, " + 2,2, Storage.FRUITS_QUANTITY.size());
    }
}
