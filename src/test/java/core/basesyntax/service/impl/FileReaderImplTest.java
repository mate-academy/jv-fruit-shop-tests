package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFromNullPath_NotOk() {
        String randomPath = null;
        fileReader.readFromFile(randomPath);
    }

    @Test
    public void readFromIncorrectPath_Ok() {
        String randomPath = "smth_input.csv";
        List<String> expected = new ArrayList<>();
        assertEquals(expected, fileReader.readFromFile(randomPath));
    }

    @Test
    public void readFromEmptyFile_Ok() {
        String randomPath = "src/test/java/testresources/empty_input.csv";
        List<String> expected = new ArrayList<>();
        assertEquals(expected, fileReader.readFromFile(randomPath));
    }

    @Test
    public void readFromFile_Ok() {
        String randomPath = "src/test/java/testresources/test_input_report.csv";
        List<String> expected = new ArrayList<>();
        try {
            expected = Files.lines(Path.of(randomPath)).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Can`t read from file: " + randomPath);
        }
        assertEquals(expected, fileReader.readFromFile(randomPath));
    }
}
