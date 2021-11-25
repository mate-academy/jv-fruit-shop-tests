package shop.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import shop.model.ActionType;
import shop.service.UpdateDbService;
import shop.service.action.ActionHandler;
import shop.service.action.DecreaseActionHandler;
import shop.service.action.IncreaseActionHandler;

public class UpdateDbServiceImplTest {
    private static UpdateDbService updateDbService;

    @BeforeClass
    public static void beforeAll() {
        HashMap<String, ActionHandler> actionMap = new HashMap<>();
        actionMap.put(ActionType.BALANCE.getAlias(), new IncreaseActionHandler());
        actionMap.put(ActionType.SUPPLY.getAlias(), new IncreaseActionHandler());
        actionMap.put(ActionType.RETURN.getAlias(), new IncreaseActionHandler());
        actionMap.put(ActionType.PURCHASE.getAlias(), new DecreaseActionHandler());
        updateDbService = new UpdateDbServiceImpl(actionMap);
    }

    @Test
    public void updateDB_storage_ok() {
        List<String> update = new ArrayList<>();
        update.add("r,apple,10");
        update.add("s,apple,15");
        update.add("s,banana,15");
        Assert.assertTrue(updateDbService.updateStorage(update));
    }
}
