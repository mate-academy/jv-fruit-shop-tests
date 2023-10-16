package core.basesyntax.service.impl;

import core.basesyntax.service.Reader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderImplTest {
    private static final String FILE_NAME = "src/test/resources/fruitshop.csv";
    private static final Path PATH = Path.of("src/test/resources/fruitshop.csv");
    private static final String expected = "type,fruit,quantity";
    private static Reader reader;

    @BeforeAll
    static void beforeAll() {
        reader = new ReaderImpl();
    }

    @Test
    void readFromFile_fileExists_notOk() {
        try {
            Files.deleteIfExists(PATH);
        } catch (IOException e) {
            throw new RuntimeException("Can`t delete file " + FILE_NAME,e);
        }
        Assertions.assertThrows(RuntimeException.class,
                () -> reader.readFromFile(FILE_NAME),
                "If from File can`t read should be Exception");

    }

    @Test
    void readFromFile_fileExists_Ok() {
        try {
            Files.writeString(PATH,expected);
        } catch (IOException e) {
            throw new RuntimeException("Can`t write string to File",e);
        }
        List<String> data = reader.readFromFile(FILE_NAME);
        Assertions.assertFalse(data.isEmpty(),
                "Content from File is Empty");
        Assertions.assertEquals(expected,data.get(0));
    }
}
