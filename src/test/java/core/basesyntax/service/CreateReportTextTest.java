package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitDto;
import core.basesyntax.storage.DataStorage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CreateReportTextTest {
    private static CreateReportText reportText;
    private static final List<FruitDto> data = new ArrayList<>();
    private static final Map<String, Integer> excepted = new HashMap<>();

    {
        excepted.put("apple", 30);
        excepted.put("banana", 40);
    }

    @BeforeClass
    public static void before() {
        reportText = new CreateReportText();
    }

    @Before
    public void setCurrentDataToList() {
        DataStorage.fruitMap.clear();
    }

    @Test(expected = RuntimeException.class)
    public void generateReport_notValidDtos_notOk() {
        data.add(0, new FruitDto("apple", "", 15));
        data.add(1, new FruitDto("apple", "s", 15));
        data.add(2, new FruitDto("", "b", 20));
        data.add(3, new FruitDto("banana", "s", -5));
        reportText.generateReport(data);
        assertEquals(excepted, DataStorage.fruitMap);
    }

    @Test
    public void creator_correctText_ok() {
        data.add(0, new FruitDto("apple", "b", 15));
        data.add(1, new FruitDto("apple", "s", 15));
        data.add(1, new FruitDto("apple", "r", 15));
        data.add(1, new FruitDto("apple", "p", 15));
        data.add(2, new FruitDto("banana", "b", 20));
        data.add(3, new FruitDto("banana", "s", 20));
        data.add(3, new FruitDto("banana", "r", 20));
        data.add(3, new FruitDto("banana", "p", 20));
        reportText.generateReport(data);
        assertEquals(excepted, DataStorage.fruitMap);
    }
}
