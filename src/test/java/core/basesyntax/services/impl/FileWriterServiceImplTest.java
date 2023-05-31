package core.basesyntax.services.impl;

import core.basesyntax.services.FileWriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;
    private static final String FILE_PATH
            = "src/test/java/core/basesyntax/resources/file_writer.csv";
    private static final String EMPTY_FILE_PATH
            = "src/test/java/core/basesyntax/resources/empty_file_writer.csv";
    private static final String REPORT_DATA = "Data from report" + System.lineSeparator()
            + "fruit 1, quantity" + System.lineSeparator()
            + "fruit 2, quantity" + System.lineSeparator();

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_nullFilePath_notOk() {
        fileWriterService.writeToFile(null, REPORT_DATA);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_emptyFilePath_notOk() {
        fileWriterService.writeToFile("", REPORT_DATA);
    }

    @Test
    public void writeToFile_fileExist_emptyReportData_Ok() {
        fileWriterService.writeToFile(EMPTY_FILE_PATH, "");
        Assert.assertTrue("File file_writer not created", new File(EMPTY_FILE_PATH).exists());
        List<String> expectedData = new ArrayList<>();
        List<String> actual = dataFromFile(EMPTY_FILE_PATH);
        Assert.assertEquals("Expected empty file", expectedData, actual);
    }

    @Test
    public void writeToFile_fileExist_validData_Ok() {
        fileWriterService.writeToFile(FILE_PATH, REPORT_DATA);
        Assert.assertTrue("File file_writer not created", new File(FILE_PATH).exists());
        List<String> expectedData = new ArrayList<>();
        expectedData.add("Data from report");
        expectedData.add("fruit 1, quantity");
        expectedData.add("fruit 2, quantity");
        List<String> actual = dataFromFile(FILE_PATH);
        Assert.assertEquals("Data from report not written correctly", expectedData, actual);
    }

    private List<String> dataFromFile(String filePath) {
        List<String> rows;
        try {
            rows = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + filePath, e);
        }
        return rows;
    }
}
