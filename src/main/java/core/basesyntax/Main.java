package core.basesyntax;

import core.basesyntax.fruitshop.FruitShop;
import core.basesyntax.fruitshop.FruitShopImpl;
import core.basesyntax.service.ActivitiesStrategy;
import core.basesyntax.service.ActivitiesStrategyImpl;
import core.basesyntax.service.activities.ActivityHandler;
import core.basesyntax.service.activities.BalanceHandler;
import core.basesyntax.service.activities.PurchaseHandler;
import core.basesyntax.service.activities.ReturnHandler;
import core.basesyntax.service.activities.SupplyHandler;
import core.basesyntax.service.activities.TypeOfActivities;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static final String INPUT_DATA_PATH = "src/main/resources/inputData.csv";
    public static final String REPORT_PATH = "src/main/resources/report.csv";

    public static void main(String[] args) {
        Map<TypeOfActivities, ActivityHandler> typeOfActivitiesActivitiesMap = new HashMap<>();
        typeOfActivitiesActivitiesMap.put(TypeOfActivities.BALANCE, new BalanceHandler());
        typeOfActivitiesActivitiesMap.put(TypeOfActivities.SUPPLY, new SupplyHandler());
        typeOfActivitiesActivitiesMap.put(TypeOfActivities.PURCHASE, new PurchaseHandler());
        typeOfActivitiesActivitiesMap.put(TypeOfActivities.RETURN, new ReturnHandler());
        ActivitiesStrategy strategy = new ActivitiesStrategyImpl(typeOfActivitiesActivitiesMap);
        FruitShop fruitShop = new FruitShopImpl(strategy,INPUT_DATA_PATH, REPORT_PATH);
        System.out.println(fruitShop.createNewReport());
    }
}
