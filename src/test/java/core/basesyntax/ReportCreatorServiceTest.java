package core.basesyntax;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreatorService;
import core.basesyntax.service.impl.ReportCreatorServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceTest {
    private static ReportCreatorService reportCreatorService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reportCreatorService = new ReportCreatorServiceImpl();
    }

    @Test
    public void createReport_initFruitsList_ok() {
        List<Fruit> initFruitsList = initFruitList();
        List<String> actualList = reportCreatorService.createReport(initFruitsList);
        List<String> expectedList = List.of("fruit,quantity", "apple,20", "banana,30", "orange,55");
        Assert.assertEquals(
                String.format("Incorrect result of ReportList for the input:\n%s.", initFruitsList),
                expectedList, actualList);
        Assert.assertEquals("Incorrect size of ReportList.Size should be ",
                expectedList.size(), actualList.size());
    }

    @Test
    public void createReport_withoutHeadline_notOk() {
        List<Fruit> initFruitsList = initFruitList();
        List<String> actualList = reportCreatorService.createReport(initFruitsList);
        List<String> expectedList = List.of("fruit,quantity", "apple,20", "banana,30", "orange,55");
        Assert.assertEquals("Your report should start with a headline: \"fruit,quantity\" ",
                expectedList.get(0), actualList.get(0));
    }

    @Test
    public void createReport_withNullOrEmptyInput_notOk() {
        List<Fruit> emptyList = Collections.emptyList();
        List<String> expectedList = List.of(
                "Given list of fruits in the shop is empty. Check input data");
        Assert.assertEquals("List of fruits is empty",
                expectedList, reportCreatorService.createReport(emptyList));
        Assert.assertEquals("List of fruits can't be null",
                expectedList, reportCreatorService.createReport(null));
    }

    private List<Fruit> initFruitList() {
        List<Fruit> initFruitsList = new ArrayList<>();
        Fruit apple = new Fruit("apple", 20);
        initFruitsList.add(apple);
        Fruit banana = new Fruit("banana", 30);
        initFruitsList.add(banana);
        Fruit orange = new Fruit("orange", 55);
        initFruitsList.add(orange);
        return initFruitsList;
    }
}
