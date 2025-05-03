package core.basesyntax.service.impl;

import core.basesyntax.service.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterImplTest {
    private static final String FILE_NAME = "src/test/resources/balance.csv";
    private static final Path PATH = Path.of("src/test/resources/balance.csv");
    private static final String expected = "fruit,quantity";
    private static Writer writer;

    @BeforeAll
    static void beforeAll() {
        writer = new WriterImpl();
    }

    @BeforeEach
    void setUp() {
        try {
            Files.deleteIfExists(PATH);
        } catch (IOException e) {
            throw new RuntimeException("Can`t delete File",e);
        }
    }

    @Test
    void writeToFile_stringWrite_Ok() {
        writer.writeToFile(FILE_NAME,expected);
        String actual;
        try {
            actual = Files.readString(PATH);
        } catch (IOException e) {
            throw new RuntimeException("Can`t read String",e);
        }
        Assertions.assertEquals(expected,actual,
                "Content in File is not equals expected ");
    }
}
