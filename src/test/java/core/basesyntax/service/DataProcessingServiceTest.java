package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.CsvFormatDataProcessingServiceImpl;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataProcessingServiceTest {
    private static DataProcessingService processingService;
    private static List<String> dataList;
    private static Fruit existedFruit;
    private static Fruit notExistedFruit;

    @BeforeClass
    public static void setUp() {
        processingService = new CsvFormatDataProcessingServiceImpl(
                InitialisationService.getFruitDaoStrategyMap());
        dataList = InitialisationService.getDataList();
        existedFruit = new Fruit("banana");
        notExistedFruit = new Fruit("eggplant");
    }

    @Test
    public void processingData_addToStorage_ok() {
        processingService.processingData(dataList);
        Integer expectedAmount = InitialisationService.getExpectedBananaAmountMap()
                .get(InitialisationService.TOTAL);
        Assert.assertEquals(expectedAmount, Storage.storage.get(existedFruit));
        Assert.assertNull(Storage.storage.get(notExistedFruit));
    }

    @After
    public void clearStorage() {
        InitialisationService.clearStorage();
    }
}
