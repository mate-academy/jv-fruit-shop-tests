package core.basesyntax.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static String OUTPUT_FILE;
    private static WriterServiceImpl writerService;
    private static ReaderServiceImpl readerService;

    @BeforeAll
    public static void setUp() {
        writerService = new WriterServiceImpl();
        readerService = new ReaderServiceImpl();
        OUTPUT_FILE = "result.csv";
    }

    @Test
    public void writeRightFile_Ok() {
        String expectedText = "banana,154"
                + System.lineSeparator()
                + "apple,90";
        writerService.writeToFile(OUTPUT_FILE, "banana,154"
                + System.lineSeparator()
                + "apple,90");
        String actualText = String.join(System.lineSeparator(), readFromFile(OUTPUT_FILE));
        Assertions.assertEquals(actualText, expectedText);
    }

    public List<String> readFromFile(String filePath) {
        try {
            return Files.readAllLines(Path.of(OUTPUT_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can not read data, please check file: " + OUTPUT_FILE, e);
        }
    }

    @Test
    public void writeToFileEmptyPath_notOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                writerService.writeToFile("", "banana,154"
                        + System.lineSeparator()
                        + "apple,90"));
    }
}
