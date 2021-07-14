package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.dao.ReportsDao;
import core.basesyntax.dao.ReportsDaoImpl;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RecordsDaoImplTest {
    private static final String TESTS_FILES_FOLDER = "src/test/java/core/basesyntax/resources/";
    private static final String GOOD_FILE = TESTS_FILES_FOLDER + "goodInput.csv";
    private static final int GOOD_FILE_RECORDS_NUM = 9;
    private static final String EMPTY_FILE = TESTS_FILES_FOLDER + "emptyFile.csv";
    private static final String NO_FILE = TESTS_FILES_FOLDER + "noFile.csv";
    private static final String TO_FILE = TESTS_FILES_FOLDER + "testReport.csv";
    private static final String CORRECT_REPORT = TESTS_FILES_FOLDER + "correctReport.csv";
    private static final String HEADER = "type,fruit,quantity";
    private static final ReportsDao reportsDao = new ReportsDaoImpl();
    private static final Map<String, Integer> map = new HashMap<>();

    @Before
    public void init() {
        map.clear();
    }

    @After
    public void teardown() {
        try {
            Files.delete(new File(TO_FILE).toPath());
        } catch (NoSuchFileException ignored) {
            // missing report file doesn't affect tests results,
            // the goal is not to have existing report before next test run
        } catch (IOException e) {
            fail("Report file wasn't deleted after test, reason: " + e);
        }
    }

    @Test
    public void getRawRecords_goodFile_ok() {
        assertEquals(reportsDao.getRawRecords(GOOD_FILE).size(), GOOD_FILE_RECORDS_NUM);
    }

    @Test
    public void getRawRecords_emptyFile_ok() {
        assertEquals(reportsDao.getRawRecords(EMPTY_FILE).size(), 0);
    }

    @Test(expected = RuntimeException.class)
    public void getRawRecords_noFile_notOk() {
        assertEquals(reportsDao.getRawRecords(NO_FILE).size(), 0);
    }

    @Test
    public void saveReport_goodMap_ok() {
        map.put("banana", 152);
        map.put("apple", 140);
        reportsDao.saveReport(map, TO_FILE);
        try {
            byte[] expectedBytes = Files.readAllBytes(Paths.get(CORRECT_REPORT));
            byte[] providedBytes = Files.readAllBytes(Paths.get(TO_FILE));
            String expected = new String(expectedBytes, StandardCharsets.UTF_8);
            String provided = new String(providedBytes, StandardCharsets.UTF_8);
            assertEquals("The content of files should match", expected, provided);
        } catch (IOException e) {
            System.out.println("Cannot open files for comparison, reason: " + e);
            fail();
        }
    }

    @Test
    public void saveReport_emptyMap_ok() {
        reportsDao.saveReport(map, TO_FILE);
        try {
            byte[] providedBytes = Files.readAllBytes(Paths.get(TO_FILE));
            String provided = new String(providedBytes, StandardCharsets.UTF_8);
            assertEquals("The content of files should match", HEADER, provided);
        } catch (IOException e) {
            System.out.println("Cannot open files for comparison, reason: " + e);
            fail();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveReport_nullMap_notOk() {
        reportsDao.saveReport(null, TO_FILE);
    }
}
