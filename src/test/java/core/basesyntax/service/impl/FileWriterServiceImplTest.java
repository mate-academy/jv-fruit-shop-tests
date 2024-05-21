package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.exception.WritingException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private FileWriterServiceImpl fileWriterService = new FileWriterServiceImpl();

    @Test
    void write_nullData_NotOk() {
        String fileName = "name";
        assertThrows(WritingException.class, () -> fileWriterService.write(fileName, null));
    }

    @Test
    void write_okData_Ok() {
        String fileName = "/Users/macbook/IdeaProjects/jv-fruit-shop-tests/src/test/resources/name";
        String data = "onetwo";
        Path path = Paths.get(fileName);
        List<String> stringList = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            stringList = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringList.forEach(stringBuilder::append);
        String resultString = stringBuilder.toString();
        assertEquals(resultString, data);
    }
}
