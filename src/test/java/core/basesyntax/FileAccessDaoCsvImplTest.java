package core.basesyntax;

import core.basesyntax.dao.FileAccessDaoCsv;
import core.basesyntax.dao.FileAccessDaoCsvImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class FileAccessDaoCsvImplTest {
    private final FileAccessDaoCsv fileAccessDaoCsv = new FileAccessDaoCsvImpl();
    private static final String OK_READ_FILE_PATH = "src/test/resources/FileReadDaoTest.csv";
    private static final String OK_WRITE_FILE_PATH = "src/test/resources/FileWriteDaoTest.csv";
    private static final String NOT_OK_FILE_PATH = "src/test/no such path";
    private static final String STRING_TO_WRITE = "apple, banana";

    @Test
    public void readFromFile_Ok() {
        fileAccessDaoCsv.readFromFile(OK_READ_FILE_PATH);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_NotOk_FilePatch() {
        fileAccessDaoCsv.readFromFile(NOT_OK_FILE_PATH);
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
    public void writeToFile_NotOk_FilePath() {
        fileAccessDaoCsv.writeToFile(STRING_TO_WRITE, NOT_OK_FILE_PATH);
        File report = new File(OK_WRITE_FILE_PATH);
        Assert.assertFalse(report.exists());
    }

    @After
    public void tearDown() {
        File reportOkPath = new File(OK_WRITE_FILE_PATH);
        File reportNotOkPath = new File(NOT_OK_FILE_PATH);

        if (reportOkPath.exists()) {
            reportOkPath.delete();
        }

        if (reportNotOkPath.exists()) {
            reportNotOkPath.delete();
        }
    }
}