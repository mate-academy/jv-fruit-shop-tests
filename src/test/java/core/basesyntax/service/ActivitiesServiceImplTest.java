package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.activities.BalanceStorageServiceImpl;
import core.basesyntax.service.activities.StorageService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class ActivitiesServiceImplTest {
    private StorageService storageService = new BalanceStorageServiceImpl(new FruitDaoImpl());
    private ActivitiesService activitiesService = new ActivitiesServiceImpl();

    @After
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void checkActivities_OK() {
        Map<String, StorageService> map = new HashMap<>();
        map.put("b", storageService);
        List<String> list = new ArrayList<>();
        list.add("type,fruit,quantity");
        list.add("b,banana,20");
        activitiesService.doingOperations(list, map);
        assertEquals("banana", Storage.fruits.get(0).getName());

    }
}
