package shop.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import shop.dao.FruitDao;
import shop.dao.FruitDaoImpl;
import shop.model.ActionType;
import shop.service.UpdateDbService;
import shop.service.action.ActionHandler;
import shop.service.action.DecreaseActionHandler;
import shop.service.action.IncreaseActionHandler;

public class UpdateDbServiceImplTest {
    private static UpdateDbService updateDbService;
    private static FruitDao fruitDao;
    private static final int ACTION_INDEX = 0;
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int COUNT_INDEX = 2;

    @BeforeClass
    public static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        HashMap<String, ActionHandler> actionMap = new HashMap<>();
        actionMap.put(ActionType.BALANCE.getAlias(), new IncreaseActionHandler(fruitDao));
        actionMap.put(ActionType.SUPPLY.getAlias(), new IncreaseActionHandler(fruitDao));
        actionMap.put(ActionType.RETURN.getAlias(), new IncreaseActionHandler(fruitDao));
        actionMap.put(ActionType.PURCHASE.getAlias(), new DecreaseActionHandler(fruitDao));
        updateDbService = new UpdateDbServiceImpl(actionMap,
                ACTION_INDEX, FRUIT_NAME_INDEX, COUNT_INDEX);
    }

    @Test
    public void updateDB_storage_ok() {
        List<String> update = new ArrayList<>();
        update.add("r,apple,10");
        update.add("s,apple,15");
        update.add("s,banana,15");
        Assert.assertTrue(updateDbService.updateStorage(update));
    }

    @Test
    public void updateDB_storage_changed_ok() {
        List<String> update = new ArrayList<>();
        update.add("s,mates,10");
        updateDbService.updateStorage(update);
        Assert.assertNotNull(fruitDao.get("mates"));
    }
}
