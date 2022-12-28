package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.FileWriterException;
import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String CREATED_PATH =
            "src/test/resources/created-files/created-file.csv";
    private static final String EXAMPLE_TEXT = "This file has only one line";
    private static final String NULL_TEXT = null;
    private static final String NULL_PATH = null;
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeFileWithOneLine_ok() {
        fileWriterService.writeToFile(EXAMPLE_TEXT, CREATED_PATH);
        List<String> expected = new ArrayList<>();
        expected.add(EXAMPLE_TEXT);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(CREATED_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = FileWriterException.class)
    public void writeFileNullText_NotOk() {
        fileWriterService.writeToFile(NULL_TEXT, CREATED_PATH);
    }

    @Test(expected = FileWriterException.class)
    public void writeFileWithNullPath() {
        fileWriterService.writeToFile(EXAMPLE_TEXT, NULL_PATH);
    }
}
