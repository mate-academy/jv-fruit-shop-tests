package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.exception.WritingException;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private FileWriterServiceImpl fileWriterService = new FileWriterServiceImpl();

    @Test
    void write_nullData_NotOk() {
        String fileName = "testFile";
        assertThrows(WritingException.class, () -> fileWriterService.write(fileName, null));
    }

    @Test
    void write_okData_Ok() throws Exception {
        String fileName = "src/test/resources/testFile";
        String expectedString = "onetwo";
        Path path = Paths.get(fileName);
        List<String> stringList = Files.readAllLines(path);
        StringBuilder stringBuilder = new StringBuilder();
        stringList.forEach(stringBuilder::append);
        String actualString = stringBuilder.toString();
        assertEquals(expectedString, actualString);
    }
}
