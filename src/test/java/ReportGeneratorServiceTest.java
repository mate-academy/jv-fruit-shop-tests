import static org.junit.Assert.assertEquals;

import fruitshop.model.FruitReport;
import fruitshop.service.impl.ReportGeneratorServiceImpl;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorServiceTest {
    private static Map<String, List<FruitReport>> generatorExpectedResults;
    private static final Pattern COMPILE_NEW_LINE = Pattern.compile("\\n");
    private static final String TEST_MESSAGE =
            "Expected generated result to be equals given string result";
    private static ReportGeneratorServiceImpl generatorService;

    @BeforeClass
    public static void beforeClass() {
        generatorExpectedResults =
                Map.of(
                        "banana,3832\napple,10020\npineapple,-652559\npeach,0",
                        List.of(
                                new FruitReport("banana",3832),
                                new FruitReport("apple",10020),
                                new FruitReport("pineapple",-652559),
                                new FruitReport("peach",0)
                        ),
                        "banana,152\napple,90",
                        List.of(
                                new FruitReport("banana", 152),
                                new FruitReport("apple", 90)
                        )
                );
        generatorService = new ReportGeneratorServiceImpl();
    }

    @Test
    public void report_testCases_ok() {
        generatorExpectedResults.forEach((s, reportList) -> {
            String expected = COMPILE_NEW_LINE.matcher(s).replaceAll(System.lineSeparator());
            String actual = generatorService.generate(reportList);
            assertEquals(TEST_MESSAGE, expected, actual);
        });
    }
}
