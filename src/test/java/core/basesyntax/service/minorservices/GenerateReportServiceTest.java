package core.basesyntax.service.minorservices;

import static org.junit.Assert.assertEquals;

import core.basesyntax.database.FruitDto;
import core.basesyntax.database.FruitsStorage;
import java.util.HashSet;
import java.util.Set;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class GenerateReportServiceTest {
    private static GenerateReportService generateReportService;

    @BeforeClass
    public static void beforeClass() {
        FruitsStorage.fruitsStorage.add(new FruitDto("banana", 20));
        FruitsStorage.fruitsStorage.add(new FruitDto("apple", 100));
        generateReportService = new GenerateReportServiceImpl();
    }

    @Test
    public void generateReport_validValue_OK() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,100";
        Set<String> fruitNames = new HashSet<>();
        fruitNames.add("banana");
        fruitNames.add("apple");
        assertEquals(expected, generateReportService.generateReport((fruitNames)));
    }

    @Test
    public void generateReport_invalidValue_OK() {
        String expected = "fruit,quantity";
        Set<String> fruitNames = new HashSet<>();
        fruitNames.add("cherry");
        fruitNames.add("grape");
        assertEquals(expected, generateReportService.generateReport((fruitNames)));
    }

    @Test
    public void generateReport_emptyValue_OK() {
        String expected = "fruit,quantity";
        Set<String> fruitNames = new HashSet<>();
        assertEquals(expected, generateReportService.generateReport((fruitNames)));
    }

    @Test
    public void generateReport_nullValue_OK() {
        String expected = "fruit,quantity";
        assertEquals(expected, generateReportService.generateReport((null)));
    }

    @AfterClass
    public static void afterClass() {
        FruitsStorage.fruitsStorage.clear();
    }
}
