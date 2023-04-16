package core.basesyntax.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String OUTPUT_FILE_PATH
            = "src/main/java/core/basesyntax/resources/outputFile.csv";
    private static String date;
    private WriterServiceImpl writerService;

    @BeforeEach
    void setUp() {
        writerService = new WriterServiceImpl();
        date = "fruit,quantity" + System.lineSeparator()
                + "banana,5" + System.lineSeparator()
                + "apple,100" + System.lineSeparator();
    }

    @Test
    void writeAndRead_Ok() {
        String expected = date;
        writerService.writeToFile(date);
        String actual;
        try {
            actual = Files.readString(Path.of(OUTPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can`t read this file" + OUTPUT_FILE_PATH, e);
        }
        Assertions.assertEquals(expected, actual);
    }
}

