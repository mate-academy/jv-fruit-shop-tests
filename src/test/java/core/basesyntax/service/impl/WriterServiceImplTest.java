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
    private static final String EXPECTED_REPORT = "src/test/resources/reports.csv";
    private static final String REPORT_FROM_TEST = "src/test/resources/reports_test.csv";
    private static final String INVALID_FILE_PATH = "/wrong/path";

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private WriterService fileService = new WriterServiceImpl();

    @After
    public void deleteReportFile() {
        deleteFile(REPORT_FROM_TEST);
    }

    @Test
    public void writeToFile_ok() {
        String content = "fruit;quantity";
        fileService.writeToFile(REPORT_FROM_TEST, content);
        String fileReportContent = getReportFromFile(EXPECTED_REPORT);
        Assert.assertEquals(fileReportContent, content);
    }

    @Test
    public void writeToFile_wrongPath_notOk() {
        String content = "fruit;quantity";
        exception.expect(RuntimeException.class);
        exception.expectMessage("Can't write to file " + INVALID_FILE_PATH);
        fileService.writeToFile(INVALID_FILE_PATH, content);
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
