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
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeFileWithOneLine_ok() {
        fileWriterService.writeToFile("This file has only one line", CREATED_PATH);
        List<String> expected = new ArrayList<>();
        expected.add("This file has only one line");
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
        fileWriterService.writeToFile(null, CREATED_PATH);
    }

    @Test(expected = FileWriterException.class)
    public void writeFileWithNullPath() {
        fileWriterService.writeToFile("This file has only one line", null);
    }
}
