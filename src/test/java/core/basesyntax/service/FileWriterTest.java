package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.impl.FileWriterImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterTest {
    private static final String dayReportTestFilePath = "src/main/resources/dayreport.csv";
    private static final String FILE_HEADER = "fruit,quantity";
    private static final long FILE_REPORT_SIZE = 46;
    private static List<String> testList = new ArrayList<>();

    @BeforeClass
    public static void beforeClass() throws Exception {
        testList.add(FILE_HEADER);
        testList.add(System.lineSeparator());
        testList.add("banana,30");
        testList.add(System.lineSeparator());
        testList.add("pineapple,22");
        testList.add(System.lineSeparator());
        testList.add("apple,50");
        File file = new File(dayReportTestFilePath);
        file.delete();
    }

    @Test
    public void writeToFile_createReportFile_OK() {
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.writeToFile(dayReportTestFilePath, testList);
        File reportFile = new File(dayReportTestFilePath);
        assertTrue(reportFile.exists());
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullList_NotOK() {
        List<String> testList2 = null;
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.writeToFile(dayReportTestFilePath, testList2);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_emptyList_NotOK() {
        List<String> testList2 = new ArrayList<>();
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.writeToFile(dayReportTestFilePath, testList2);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_testListContains_null_NotOK() {
        List<String> testList1 = new ArrayList<>();
        testList1.add(null);
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.writeToFile(dayReportTestFilePath, testList1);
    }

    @Test
    public void writeToFile_reportFileSize_OK() {
        File file = new File(dayReportTestFilePath);
        assertTrue(file.length() == FILE_REPORT_SIZE);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_filePath_null_NotOK() {
        String wrongDayReportTestFilePath = null;
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.writeToFile(wrongDayReportTestFilePath, testList);
    }
}
