package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static final String WROTE_DATA = "wrote_data";
    private static final String VALID_FILE_PATH =
            "src/test/java/core/basesyntax/resources/report.csv";
    private static final String INVALID_FILE_PATH =
            "srv/test/java/core/basesyntax/resources/unknown.csv";

    private static FileWriterService fileWriterService;

    @BeforeAll
    static void beforeAll() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    void writeFile_writeToValidFilePath_Ok() throws IOException {
        fileWriterService.writeFile(VALID_FILE_PATH, WROTE_DATA);
        List<String> fileData = Files.readAllLines(Path.of(VALID_FILE_PATH));
        String actual = fileData.stream()
                .map(Object::toString)
                .collect(Collectors.joining());
        assertEquals(WROTE_DATA, actual);
    }

    @Test
    void writeFile_writeToInvalidFilePath_NotOk() {
        assertThrows(RuntimeException.class, () ->
                fileWriterService.writeFile(INVALID_FILE_PATH, WROTE_DATA));
    }
}
