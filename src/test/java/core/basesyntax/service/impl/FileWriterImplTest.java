package core.basesyntax.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {

    private static final String OUTPUT_FILE_PATH
            = "src/test/resources/finalReport.csv";
    private static final String DATA = "fruit,quantity" + System.lineSeparator()
            + "banana,50" + System.lineSeparator()
            + "apple,120" + System.lineSeparator();
    private FileWriterImpl writerService = new FileWriterImpl();

    @BeforeEach
    void setUp() {
        writerService = new FileWriterImpl();
    }

    @Test
    void writeAndRead_Ok() {
        writerService.write(DATA, OUTPUT_FILE_PATH);
        String actual;
        try {
            actual = Files.readString(Path.of(OUTPUT_FILE_PATH));
        } catch (IOException exception) {
            throw new RuntimeException("Can`t read this file" + OUTPUT_FILE_PATH, exception);
        }
        Assertions.assertEquals(DATA, actual);
    }
}
