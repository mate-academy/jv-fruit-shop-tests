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
import org.junit.Test;

public class RecordsDaoImplTest {
    private static final String TESTS_FILES_FOLDER = "src/test/java/core/basesyntax/resources/";
    private static final String GOOD_FILE = TESTS_FILES_FOLDER + "goodInput.csv";
    private static final int GOOD_FILE_RECORDS_NUM = 9;
    private static final String EMPTY_FILE = TESTS_FILES_FOLDER + "emptyFile.csv";
    private static final String NO_FILE = TESTS_FILES_FOLDER + "noFile.csv";
    private static final String TO_FILE = TESTS_FILES_FOLDER + "testReport.csv";
    //private static final String UNRECORDABLE_FILE = TESTS_FILES_FOLDER + "unrecordableFile.csv";
    private static final String CORRECT_REPORT = TESTS_FILES_FOLDER + "correctReport.csv";
    private static final String HEADER = "type,fruit,quantity";
    private static final ReportsDao DAO = new ReportsDaoImpl();
    private static final Map<String, Integer> MAP = new HashMap<>();

    @Test
    public void getRawRecords_goodFile_ok() {
        assertEquals(DAO.getRawRecords(GOOD_FILE).size(), GOOD_FILE_RECORDS_NUM);
    }

    @Test
    public void getRawRecords_emptyFile_ok() {
        assertEquals(DAO.getRawRecords(EMPTY_FILE).size(), 0);
    }

    @Test(expected = RuntimeException.class)
    public void getRawRecords_noFile_notOk() {
        assertEquals(DAO.getRawRecords(NO_FILE).size(), 0);
    }

    @Test
    public void saveReport_goodMap_ok() {
        MAP.clear();
        MAP.put("banana", 152);
        MAP.put("apple", 140);
        try {
            Files.delete(new File(TO_FILE).toPath());
        } catch (NoSuchFileException e) {
            System.out.println("Previous report file wasn't deleted as it's absent but it's ok");
        } catch (IOException e) {
            System.out.println("Report file wasn't deleted before test, reason: " + e);
            fail();
        }
        DAO.saveReport(MAP, TO_FILE);
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
        MAP.clear();
        try {
            Files.delete(new File(TO_FILE).toPath());
        } catch (NoSuchFileException e) {
            System.out.println("Previous report file wasn't deleted as it's absent but it's ok");
        } catch (IOException e) {
            System.out.println("Report file wasn't deleted before test, reason: " + e);
            fail();
        }
        DAO.saveReport(MAP, TO_FILE);
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
        DAO.saveReport(null, TO_FILE);
    }

    /* Method below commented out as file read-only access is now working on Travis side
    *
    * @Test(expected = RuntimeException.class)
    * public void saveReport_impossibleWritingFile_notOk() {
    *    MAP.clear();
    *    MAP.put("banana", 152);
    *    MAP.put("apple", 140);
    *    DAO.saveReport(MAP, UNRECORDABLE_FILE);
    *}
    */
}
