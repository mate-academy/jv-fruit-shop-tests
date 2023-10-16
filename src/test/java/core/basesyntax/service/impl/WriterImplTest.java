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
        if (Files.exists(PATH)) {
            try {
                Files.delete(PATH);
            } catch (IOException e) {
                throw new RuntimeException("Can`t delete File",e);
            }
        }
    }

    @Test
    void writeToFile_stringToFileWrite_notOk() {
        try {
            Files.createFile(PATH);
            Files.setAttribute(PATH,"dos:readonly", true);
        } catch (IOException e) {
            throw new RuntimeException("Can`t create File oder change Attribute",e);
        }
        Assertions.assertThrows(RuntimeException.class,
                () -> writer.writeToFile(FILE_NAME,expected),
                "If in File can`t write should be Exception");
        try {
            Files.setAttribute(PATH,"dos:readonly", false);
            Files.deleteIfExists(PATH);
        } catch (IOException e) {
            throw new RuntimeException("Can`t delete file " + FILE_NAME,e);
        }
    }

    @Test
    void writeToFile_stringToFileWrite_Ok() {
        writer.writeToFile(FILE_NAME,expected);
        Assertions.assertTrue(Files.exists(PATH));
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
