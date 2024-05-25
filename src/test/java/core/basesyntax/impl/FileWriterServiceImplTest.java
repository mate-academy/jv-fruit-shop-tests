package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.exception.WritingException;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static final FileWriterServiceImpl fileWriterService = new FileWriterServiceImpl();

    @Test
    void write_nullData_notOk() {
        String fileName = "testFile";
        assertThrows(WritingException.class, () -> fileWriterService.write(fileName, null));
    }

    @Test
    void write_okData_ok() throws Exception {
        Path path = Path.of("src/test/resources/testFile");
        List<String> stringList = Files.readAllLines(path);
        String actualString = String.join("", stringList);
        String expectedString = "onetwo";
        assertEquals(expectedString, actualString);
    }
}
