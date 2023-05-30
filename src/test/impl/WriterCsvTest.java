package core.basesyntax.service.file.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.file.WriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterCsvTest {
    private static WriterService writerService;
    private static String filePath;
    private static String pathToResult;
    private static final String SEPARATOR = File.separator;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterCsv();
        filePath = "src" + SEPARATOR + "test" + SEPARATOR + "resources" + SEPARATOR;
        pathToResult = filePath + "reports" + SEPARATOR + "report.csv";
    }

    @Test
    public void write_ValidData_Ok() {
        String expected = "b,banana,20" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator()
                + "s,banana,100";
        writerService.write(filePath, expected);
        try {
            String actual = Files.lines(Path.of(pathToResult))
                    .collect(Collectors.joining(System.lineSeparator()));
            assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException("Exception with file: " + filePath);
        }
    }

    @Test
    public void write_EmptyData_Ok() {
        String expected = "";
        writerService.write(filePath, expected);
        try {
            String actual = Files.lines(Path.of(pathToResult))
                    .collect(Collectors.joining(System.lineSeparator()));
            assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException("Exception with file: " + filePath);
        }
    }

    @Test(expected = RuntimeException.class)
    public void write_EmptyPath_NotOk() {
        String someData = "What am I?";
        writerService.write("", someData);
    }

    @Test(expected = RuntimeException.class)
    public void write_NullPath_NotOk() {
        String someData = "I am single line";
        writerService.write(null, someData);
    }

    @Test(expected = RuntimeException.class)
    public void write_InvalidDataForWriting() {
        writerService.write(filePath, null);
    }
}
