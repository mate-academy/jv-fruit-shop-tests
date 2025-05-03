package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;
    private static FileReaderService fileReaderService;
    private static String fileWithInvalidPath;
    private static String toFile;

    @BeforeAll
    static void beforeAll() {
        toFile = "src/test/resources/resultTestFileWithCorrectReport.csv";
        fileWithInvalidPath = "src/resources/resultTestFileWithoutExtension";
        fileWriterService = new FileWriterServiceImpl();
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    void writeToFile_writeFileWithReport_Ok() {
        fileWriterService.writeReportToFile(toFile, getCorrectReport());
        List<String> expected = List.of(
                "fruit,quantity",
                 "banana,10",
                 "apple,30"
        );
        List<String> actual = fileReaderService.readFromFile(toFile);
        assertEquals(expected, actual);
    }

    @Test
    void writeToFile_writeFileWithNullPath_NotOk() {
        assertThrows(RuntimeException.class, () ->
                fileWriterService.writeReportToFile(null, getCorrectReport()));
    }

    @Test
    void writeToFile_writeFileWithNullReport_NotOk() {
        assertThrows(RuntimeException.class, () ->
                fileWriterService.writeReportToFile(toFile, null));
    }

    @Test
    void writeToFile_writeToFileWithInvalidPath_NotOk() {
        assertThrows(RuntimeException.class, () ->
                fileWriterService.writeReportToFile(fileWithInvalidPath, getCorrectReport()));
    }

    private static String getCorrectReport() {
        return "fruit,quantity" + System.lineSeparator()
                + "banana,10" + System.lineSeparator()
                + "apple,30";
    }
}
