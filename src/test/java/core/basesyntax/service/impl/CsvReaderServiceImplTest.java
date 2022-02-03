package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.ReaderService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvReaderServiceImplTest {
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new CsvReaderServiceImpl();
    }

    @Test
    public void readFile_Ok() {
        String inputFilePath = "src/main/resources/input.csv";
        List<String> expected = List.of("fruit,quantity","apple,100","banana,50");
        createFile(inputFilePath);
        List<String> actual = readerService.readFile(inputFilePath);
        deleteFile(inputFilePath);
        assertEquals(expected, actual);
    }

    @Test
    public void readFile_notOk() {
        String inputFilePath = "";
        try {
            readerService.readFile(inputFilePath);
        } catch (RuntimeException e) {
            return;
        }
        fail("Need to throw RuntimeException with empty path!");
    }

    private void createFile(String filePath) {
        String data = "fruit,quantity"
                + System.lineSeparator()
                + "apple,100" + System.lineSeparator()
                + "banana,50";
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Can't create file", e);
            }
        }
        try {
            Files.write(file.toPath(),
                    data.getBytes(),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file", e);
        }
    }

    private void deleteFile(String inputFilePath) {
        try {
            Files.delete(Path.of(inputFilePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file", e);
        }
    }
}
