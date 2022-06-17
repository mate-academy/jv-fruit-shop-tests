package core.basesyntax.service.imp;

import core.basesyntax.service.CsvValidator;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvValidatorImpTest {
    private static final String HEADER = "row 1, row 2, row3";
    private static final String SOME_CSV_LINE = "somedata, somedata, somedata";
    private static CsvValidator csvValidator;
    private static List<String> csvData;

    @BeforeClass
    public static void beforeClass() {
        csvValidator = new CsvValidatorImp(HEADER);
        csvData = new ArrayList<>();
    }

    @Test
    public void validate_validCsv_ok() {
        csvData.add(0, HEADER);
        csvData.add(SOME_CSV_LINE);
        Assert.assertTrue(csvData.containsAll(csvValidator.validate(csvData)));
    }

    @Test(expected = RuntimeException.class)
    public void validate_invalidCsv_notOk() {
        csvData.add(SOME_CSV_LINE);
        csvValidator.validate(csvData);
    }

    @After
    public void tearDown() {
        csvData.clear();
    }
}
