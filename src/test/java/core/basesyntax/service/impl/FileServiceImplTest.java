package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileServiceImplTest {
    private static final String CORRECT_FILE_PATH = "src/test/resources/testInput.csv";
    private static final String NOT_EXISTING_FILE_PATH = "src/test/resources/notExisting.csv";
    private static final String INCORRECT_FILE_PATH = "src/test/reso/testOutput.csv";
    private static final String TEST_REPORT = "Some report data.";
    private static final String TEST_REPORT_CORRECT_PATH = "src/test/resources/testOutput.csv";
    private static FileService fileService;

    @BeforeAll
    static void beforeAll() {
        fileService = new FileServiceImpl();
    }

    @Test
    void readFile_NotExistingFile_notOk() {
        assertThrows(RuntimeException.class, () -> fileService.readFile(NOT_EXISTING_FILE_PATH));
    }

    @Test
    void readFile_CorrectFile_Ok() {
        List<String> lines = fileService.readFile(CORRECT_FILE_PATH);
        assertEquals(9, lines.size());
        assertEquals("s,banana,50", lines.get(8));
    }

    @Test
    void writeFile_IncorrectPath_notOk() {
        assertThrows(RuntimeException.class, ()
                -> fileService.writeFile(INCORRECT_FILE_PATH,TEST_REPORT));
    }

    @Test
    void writeFile_CorrectPath_Ok() throws IOException {
        fileService.writeFile(TEST_REPORT_CORRECT_PATH,TEST_REPORT);
        String data = new String(Files.readAllBytes(Paths.get(TEST_REPORT_CORRECT_PATH)));
        assertEquals(TEST_REPORT, data);
    }
}
