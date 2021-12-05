package core.basesyntax;

import core.basesyntax.dao.CsvFileService;
import core.basesyntax.dao.CsvFileServiceImpl;
import java.io.File;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class CsvFileServiceImplTest {
    private static final String OK_READ_FILE_PATH = "src/test/resources/CsvFileTestInput.csv";
    private static final String OK_WRITE_FILE_PATH = "src/test/resources/FileWriteDaoTest.csv";
    private static final String INVALID_FILE_PATH = "src/test/no such path";
    private static final String STRING_TO_WRITE = "apple, banana";
    private final CsvFileService fileAccessDaoCsv = new CsvFileServiceImpl();

    @Test
    public void readFromFile_Ok() {
        final List<String> expectedList = List.of("apple", "banana");
        List<String> actualList = fileAccessDaoCsv.readFromFile(OK_READ_FILE_PATH);
        Assert.assertEquals(expectedList, actualList);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_NotOk_FilePatch() {
        fileAccessDaoCsv.readFromFile(INVALID_FILE_PATH);
    }

    @Test (expected = NullPointerException.class)
    public void readFromFile_NotOk_Npe() {
        fileAccessDaoCsv.readFromFile(null);
    }

    @Test
    public void writeToFile_Ok() {
        fileAccessDaoCsv.writeToFile(STRING_TO_WRITE, OK_WRITE_FILE_PATH);
        File report = new File(OK_WRITE_FILE_PATH);
        Assert.assertTrue(report.exists());
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_NotOk_Npe() {
        fileAccessDaoCsv.writeToFile(null, OK_WRITE_FILE_PATH);
        fileAccessDaoCsv.writeToFile(STRING_TO_WRITE, null);
        fileAccessDaoCsv.writeToFile(null, null);
    }

    @Test
    public void writeToFile_NotOk_WrongFilePath() {
        fileAccessDaoCsv.writeToFile(STRING_TO_WRITE, INVALID_FILE_PATH);
        File report = new File(OK_WRITE_FILE_PATH);
        Assert.assertFalse(report.exists());
    }

    @After
    public void tearDown() {
        File reportOkPath = new File(OK_WRITE_FILE_PATH);
        File reportNotOkPath = new File(INVALID_FILE_PATH);

        if (reportOkPath.exists()) {
            reportOkPath.delete();
        }

        if (reportNotOkPath.exists()) {
            reportNotOkPath.delete();
        }
    }
}
