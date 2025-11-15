package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private final FileReader fileReader = new FileReaderImpl();

    @Test
    public void read_fileNameNull_notOk() {
        String expected = "Invalid file name";
        assertEquals(expected, assertThrows(IllegalArgumentException.class,
                () -> fileReader.read(null)).getMessage());
    }

    @Test
    public void read_fileNameEmpty_notOk() {
        String expected = "Invalid file name";
        assertEquals(expected, assertThrows(IllegalArgumentException.class,
                () -> fileReader.read("")).getMessage());
    }

    @Test
    public void read_fileDoesNotExist_notOk() {
        String expected = "Can't read the file";
        assertEquals(expected, assertThrows(RuntimeException.class,
                () -> fileReader.read("fileName")).getMessage());
    }

    @Test
    public void read_readingFile_Ok() {
        String filePath = "src/test/resources/reportToReadTest.csv";
        List<String> expected = new ArrayList<>();
        List<String> actual = new ArrayList<>();
        try {
            expected = Files.readAllLines(Path.of(filePath));
            actual = fileReader.read(filePath);
        } catch (IOException e) {
            fail();
        }
        assertEquals(expected,actual);
    }
}
