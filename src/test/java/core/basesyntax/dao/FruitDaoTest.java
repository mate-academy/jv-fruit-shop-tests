package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.DataProcessingService;
import core.basesyntax.service.InitialisationService;
import core.basesyntax.service.impl.CsvFormatDataProcessingServiceImpl;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoTest {
    private static final String NOT_EXISTED_FRUIT_PURCHASE = "p,pea,5";
    private static final String TO_MANY_BANANA = "p,banana,1000";
    private static List<String> dataList;
    private static Map<String, Integer> expectedBananaAmountMap;
    private static DataProcessingService processingService;
    private static Fruit banana;

    @BeforeClass
    public static void setUp() {
        banana = new Fruit("banana");
        dataList = InitialisationService.getDataList();
        expectedBananaAmountMap = InitialisationService.getExpectedBananaAmountMap();
        processingService = new CsvFormatDataProcessingServiceImpl(
                InitialisationService.getFruitDaoStrategyMap());
    }

    @Test
    public void purchaseFruits_ok() {
        processingService.processingData(getFilteredByOperationList(InitialisationService.BALANCE));
        processingService.processingData(
                getFilteredByOperationList(InitialisationService.PURCHASE));
        Assert.assertEquals(expectedBananaAmountMap.get(InitialisationService.PURCHASE),
                Storage.storage.get(banana));
    }

    @Test(expected = RuntimeException.class)
    public void purchaseFruits_notEnough_notOk() {
        processingService.processingData(getFilteredByOperationList(InitialisationService.BALANCE));
        processingService.processingData(List.of(TO_MANY_BANANA));
    }

    @Test(expected = RuntimeException.class)
    public void purchaseFruits_notExistedFruit_notOk() {
        processingService.processingData(List.of(NOT_EXISTED_FRUIT_PURCHASE));
    }

    @Test
    public void balanceFruits_ok() {
        processingService.processingData(getFilteredByOperationList(InitialisationService.BALANCE));
        Assert.assertEquals(expectedBananaAmountMap.get(InitialisationService.BALANCE),
                Storage.storage.get(banana));
    }

    @Test
    public void supplyFruits_ok() {
        processingService.processingData(getFilteredByOperationList(InitialisationService.SUPPLY));
        Assert.assertEquals(expectedBananaAmountMap.get(InitialisationService.SUPPLY),
                Storage.storage.get(banana));
    }

    @Test
    public void returnFruits_ok() {
        processingService.processingData(getFilteredByOperationList(InitialisationService.RETURN));
        Assert.assertEquals(expectedBananaAmountMap.get(InitialisationService.RETURN),
                Storage.storage.get(banana));
    }

    private List<String> getFilteredByOperationList(String operation) {
        return dataList.stream()
                .skip(1)
                .filter(s -> ("" + s.charAt(0)).equals(operation))
                .collect(Collectors.toList());
    }

    @After
    public void clearStorage() {
        InitialisationService.clearStorage();
    }
}
