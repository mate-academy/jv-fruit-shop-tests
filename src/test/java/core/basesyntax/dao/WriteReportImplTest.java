package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.exception.ValidationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WriteReportImplTest {
    private static final String FILE_NAME = "src/test/resources/reportFileTest.csv";
    private static WriteReport writeReport;
    private static final String REPORT = "fruit,quantity" + System.lineSeparator() + "banana,152"
            + System.lineSeparator() + "apple,90" + System.lineSeparator();

    @Before
    public void setUp() {
        writeReport = new WriteReportImpl();
    }

    @Test
    public void write_Ok() {
        writeReport.write(REPORT, FILE_NAME);
        List<String> actual = null;
        try {
            actual = Files.readAllLines(Path.of(String.valueOf(new File(FILE_NAME))));
        } catch (IOException e) {
            fail("Can't get data from file");
        }
        String expectedString = "fruit,quantity";
        assertEquals("Test failed: incorrect data", actual.get(0),expectedString);
        int expectedSize = 3;
        assertEquals("Test failed: incorrect number elements from report",
                actual.size(),expectedSize);
    }

    @Test(expected = ValidationException.class)
    public void writeDataToMissingFile_NotOk() {
        writeReport.write(REPORT,"/");
    }

    @Test(expected = ValidationException.class)
    public void getDataFromNullFile_NotOk() {
        writeReport.write(REPORT,null);
    }

    @After
    public void afterEachTest() {
        new File(FILE_NAME).delete();
    }
}
