package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceTest {
    private static final String TEST_RESOURCES_PATH = "src/test/resources/";
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readLines_fromValidFile_ok() {
        List<String> expectedListOfLines = List.of(
                "b,banana,20", "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10", "p,apple,20",
                "p,banana,5", "s,banana,50");
        List<String> currentListOfLines = readerService
                .readFile(TEST_RESOURCES_PATH + "valid_file.csv");
        assertTrue(expectedListOfLines.containsAll(currentListOfLines)
                && expectedListOfLines.size() == currentListOfLines.size());
    }

    @Test
    void readLines_fromEmptyFile_ok() {
        List<String> currentListOfLines = readerService
                .readFile(TEST_RESOURCES_PATH + "empty_file.csv");
        assertTrue(currentListOfLines.isEmpty(),
                String.format("Expected empty list, but was list of %d line(s)",
                        currentListOfLines.size() - 1));
    }

    @Test
    void readLines_fromFileWithOneLine_ok() {
        List<String> currentListOfLines = readerService
                .readFile(TEST_RESOURCES_PATH + "one_line_file.csv");
        assertTrue(currentListOfLines.isEmpty(),
                String.format("Expected empty list, but was list of %d line(s)",
                        currentListOfLines.size() - 1));
    }

    @Test
    void readLines_fromNonExistentFIle_notOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFile(TEST_RESOURCES_PATH + "non_existent_file.csv"));
    }
}
