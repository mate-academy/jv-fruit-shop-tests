package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CsvFileReaderServiceTest {
    private FileReaderService fileReaderService;

    @Before
    public void setUp() throws Exception {
        fileReaderService = new CsvFileReaderService();
    }

    @Test
    public void read_filePathEmptyString_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReaderService.read("");
        });
    }

    @Test
    public void read_fileNotExist_notOk() {
        String filePath = "./src/main/resources/not-exist-file.csv";
        assertThrows(RuntimeException.class, () -> {
            fileReaderService.read(filePath);
        });
    }

    @Test
    public void read_emptyFile_notOk() {
        String filePath = "./src/test/resources/empty-file.csv";
        assertThrows(RuntimeException.class, () -> {
            fileReaderService.read(filePath);
        });
    }

    @Test
    public void read_filePathNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReaderService.read(null);
        });
    }

    @Test
    public void read_correctFilePath_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit ,quantity");
        expected.add("b   ,banana,20");
        expected.add("b   ,apple ,100");
        expected.add("s   ,banana,100");
        expected.add("p   ,banana,13");
        expected.add("r   ,apple ,10");
        expected.add("p   ,apple ,20");
        expected.add("p   ,banana,5");
        expected.add("s   ,banana,50");
        String filePath = "./src/test/resources/input-file.csv";
        List<String> actual = fileReaderService.read(filePath);
        assertEquals(expected, actual);
    }
}
