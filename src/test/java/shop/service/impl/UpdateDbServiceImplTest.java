package shop.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import shop.dao.FruitDao;
import shop.dao.FruitDaoImpl;
import shop.db.DataStorage;
import shop.model.ActionType;
import shop.service.UpdateDbService;
import shop.service.action.ActionHandler;
import shop.service.action.ActionStrategyHandler;
import shop.service.action.DecreaseActionHandler;
import shop.service.action.IncreaseActionHandler;

public class UpdateDbServiceImplTest {
    private static UpdateDbService updateDbService;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        HashMap<String, ActionHandler> actionMap = new HashMap<>();
        actionMap.put(ActionType.BALANCE.getAlias(), new IncreaseActionHandler(fruitDao));
        actionMap.put(ActionType.SUPPLY.getAlias(), new IncreaseActionHandler(fruitDao));
        actionMap.put(ActionType.RETURN.getAlias(), new IncreaseActionHandler(fruitDao));
        actionMap.put(ActionType.PURCHASE.getAlias(), new DecreaseActionHandler(fruitDao));
        ActionStrategyHandler actionStrategyHandler = new ActionStrategyHandlerImpl(actionMap);
        updateDbService = new UpdateDbServiceImpl(actionStrategyHandler);
    }

    @Test
    public void updateDB_storage_ok() {
        List<String> update = new ArrayList<>();
        update.add("r,apple,10");
        update.add("s,apple,15");
        update.add("s,banana,15");
        int size = DataStorage.storage.size();
        Assert.assertTrue(updateDbService.updateStorage(update));
        int after = DataStorage.storage.size();
        Assert.assertTrue(size != after);
    }

    @Test
    public void updateDB_storage_changed_ok() {
        List<String> update = new ArrayList<>();
        update.add("s,mates,10");
        updateDbService.updateStorage(update);
        Assert.assertNotNull(DataStorage.storage.stream()
                .filter(e -> e.getName().equals("mates")).findFirst());
    }
}
