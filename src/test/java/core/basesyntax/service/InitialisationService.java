package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImplBalance;
import core.basesyntax.dao.FruitDaoImplPurchase;
import core.basesyntax.dao.FruitDaoImplReturn;
import core.basesyntax.dao.FruitDaoImplSupply;
import core.basesyntax.db.Storage;
import java.util.List;
import java.util.Map;

public class InitialisationService {
    public static final String BALANCE = "b";
    public static final String SUPPLY = "s";
    public static final String PURCHASE = "p";
    public static final String RETURN = "r";
    public static final String TOTAL = "t";

    public static List<String> getDataList() {
        return List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,banana,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
    }

    public static List<String> getReportList() {
        return List.of(
                "fruit,amount",
                "banana,162",
                "apple,80"
        );
    }

    public static Map<String, FruitDao> getFruitDaoStrategyMap() {
        return Map.of(
                BALANCE, new FruitDaoImplBalance(),
                SUPPLY, new FruitDaoImplSupply(),
                PURCHASE, new FruitDaoImplPurchase(),
                RETURN, new FruitDaoImplReturn()
        );
    }

    public static void clearStorage() {
        Storage.storage.clear();
    }

    public static Map<String, Integer> getExpectedBananaAmountMap() {
        return Map.of(
                BALANCE, 20,
                SUPPLY, 150,
                PURCHASE, 2,
                RETURN, 10,
                TOTAL, 162
        );
    }
}
