package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static final String EXPECTED = "fruit,quantity"
            + System.lineSeparator() + "banana,20"
            + System.lineSeparator() + "apple,16"
            + System.lineSeparator() + "pineapple,89"
            + System.lineSeparator();
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @Test(expected = NullPointerException.class)
    public void generate_nullInputValue_isNotOk() {
        reportGenerator.generate(null);
    }

    @Test
    public void generate_validData_isOk() {
        Storage.FRUITS.put("banana", 20);
        Storage.FRUITS.put("apple", 16);
        Storage.FRUITS.put("pineapple", 89);
        String actual = reportGenerator.generate(Storage.FRUITS);
        assertEquals(EXPECTED, actual);
    }
}
