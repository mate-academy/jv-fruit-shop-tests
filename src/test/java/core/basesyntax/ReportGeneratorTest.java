package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.strorage.FruitStorage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorTest {
    private static ReportGenerator reportGenerator;

    @BeforeClass
    public static void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    public void generate_isEmpty_ok() {
        String expected = ReportGeneratorImpl.HEADER;
        String actual = reportGenerator.generate();
        assertEquals("Expected header", expected, actual);
    }

    @Test
    public void generate_withOneLine_ok() {
        FruitStorage.fruits.put("banana", 20);
        String expected = ReportGeneratorImpl.HEADER + System.lineSeparator() + "banana,20";
        String actual = reportGenerator.generate();
        assertEquals("Method must generate valid report", expected, actual);
    }

    @Test
    public void generate_withTwoLines_ok() {
        FruitStorage.fruits.put("banana", 20);
        FruitStorage.fruits.put("apple", 10);
        StringBuilder expected = new StringBuilder(ReportGeneratorImpl.HEADER)
                .append(System.lineSeparator())
                .append("banana,20")
                .append(System.lineSeparator())
                .append("apple,10");
        String actual = reportGenerator.generate();
        assertEquals("Method must generate valid report", expected.toString(), actual);
    }

    @After
    public void clearStorage() {
        FruitStorage.fruits.clear();
    }
}
