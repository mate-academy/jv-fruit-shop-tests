package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriterServiceImplTest {
    private static final String REPORT_FILE_NAME = "src/test/resources/reports.csv";
    private static final String REPORT_FILE_NAME_TEST = "src/test/resources/reports_test.csv";
    private static final String PATH_TO_FILE = "/wrong/path";

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private WriterService fileService = new WriterServiceImpl();

    @After
    public void deleteReportFile() {
        deleteFile(REPORT_FILE_NAME_TEST);
    }

    @Test
    public void writeToFile_ok() {
        String content = "fruit;quantity";
        fileService.writeToFile(REPORT_FILE_NAME_TEST, content);
        String fileReportContent = getReportFromFile(REPORT_FILE_NAME);
        Assert.assertEquals(fileReportContent, content);
    }

    @Test
    public void reader_throw_RuntimeException() {
        String content = "fruit;quantity";
        exception.expect(RuntimeException.class);
        exception.expectMessage("Can't write to file " + PATH_TO_FILE);
        fileService.writeToFile(PATH_TO_FILE, content);
    }

    private String getReportFromFile(String reportFileName) {
        StringBuilder stringBuilder = new StringBuilder();
        Path filePath = Paths.get(reportFileName);
        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath)) {
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + filePath, e);
        }
        return stringBuilder.toString();
    }

    private void deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
