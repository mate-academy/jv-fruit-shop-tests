package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String FILE_NAME = "src/test/resources/output.csv";
    private static FileWriterService fileWriterService;

    private static String getReportForTest() {
        return "fruit,quantity,banana,15,apple,13";
    }

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_fileNameIsNull_isNotValid() {
        fileWriterService.writeToFile(null, getReportForTest());
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_reportIsNull_isNotValid() {
        fileWriterService.writeToFile(FILE_NAME, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_fileNameIsNotValid() {
        fileWriterService.writeToFile("/texkf.cvs", getReportForTest());
    }

    @Test
    public void writeToFile_writeReportToFile_isValid() {
        fileWriterService.writeToFile(FILE_NAME, getReportForTest());
        String actual = "";
        try {
            actual = Files.readAllLines(Path.of(FILE_NAME)).stream()
                    .flatMap(String::lines).collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException("Cant read this file " + FILE_NAME, e);
        }
        String expected = getReportForTest();
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_createEmptyFile_isValid() {
        fileWriterService.writeToFile(FILE_NAME, "");
        String actual = "";
        try {
            actual = Files.readAllLines(Path.of(FILE_NAME)).stream()
                    .flatMap(String::lines).collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException("Cant read this file " + FILE_NAME, e);
        }
        String expected = "";
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_writeOneWord_isValid() {
        fileWriterService.writeToFile(FILE_NAME, "fruit");
        String actual = "";
        try {
            actual = Files.readAllLines(Path.of(FILE_NAME)).stream()
                    .flatMap(String::lines).collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException("Cant read this file " + FILE_NAME, e);
        }
        String expected = "fruit";
        assertEquals(expected, actual);
    }
}
