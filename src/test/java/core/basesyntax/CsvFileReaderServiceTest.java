package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.FileReaderException;
import core.basesyntax.servise.ReaderService;
import core.basesyntax.servise.impl.CsvFileReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvFileReaderServiceTest {
    private static ReaderService readerService;
    private static List<String> incorrectPath;
    private String pathFile;

    @BeforeAll
    public static void setUp() {
        readerService = new CsvFileReaderServiceImpl();
        incorrectPath = List.of("invalid path",
                "src/test/resources/",
                "src/test/resources/.csv",
                "src/test/resources/file");
    }

    @BeforeEach
    public void beforeTest() {
        pathFile = "src/test/resources/file.csv";
    }

    @Test
    public void readerService_EmptyFile_notOk() {
        pathFile = "src/test/resources/empty.csv";

        assertThrows(FileReaderException.class, () -> readerService.readFromFile(pathFile));
    }

    @Test
    public void readerService_fileMissing_notOk() {
        pathFile = "src/test/resources/missing.csv";

        assertThrows(RuntimeException.class, () -> readerService.readFromFile(pathFile));
    }

    @Test
    public void readerService_fileNameNull_notOk() {
        pathFile = null;

        assertThrows(RuntimeException.class, () -> readerService.readFromFile(pathFile));
    }

    @Test
    public void readerService_invalidPathFile_notOk() {
        incorrectPath.forEach(pathFile -> assertThrows(RuntimeException.class,
                    () -> readerService.readFromFile(pathFile)));
    }

    @Test
    public void readerService_invalidFileFormat_notOk() {
        pathFile = "src/test/resources/invalid_format.csv";

        assertThrows(FileReaderException.class, () -> readerService.readFromFile(pathFile));
    }

    @Test
    public void readerService_readFromFile_Ok() {
        List<String> actual = readerService.readFromFile(pathFile);

        assertAll("Test failed! List shouldn't be empty after read file",
                () -> assertNotNull(actual,"Test failed! List not be null "
                    + "after read file"),
                () -> assertFalse(actual.isEmpty(),"Test failed! List shouldn't be empty "
                    + "after read file")
        );
    }
}
