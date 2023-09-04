package core.basesyntax.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FileReaderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static FileReaderService fileReaderService;

    @BeforeAll
    static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    void read_validFile_ok() {
        String newFile = "src/test/resources/testData.csv";
        String fileLines = fileReaderService.read(newFile);
        int numberOfLines = fileLines.split(System.lineSeparator()).length;
        int actualNumberOfLines = 9;
        assertEquals(numberOfLines,actualNumberOfLines);
    }

    @Test
    void read_nullFile_notOk() {
        assertThrows(RuntimeException.class, () -> fileReaderService.read(null));
    }

    @AfterEach
    public void afterEachTest() {
        Storage.STORAGE.clear();
    }
}
