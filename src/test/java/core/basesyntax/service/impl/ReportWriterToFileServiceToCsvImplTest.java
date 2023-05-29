package core.basesyntax.service.impl;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exeptions.WrongExtensionFile;
import core.basesyntax.service.ReportWriterToFileService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("WriterToCsvImpl Test")
class ReportWriterToFileServiceToCsvImplTest {
    private static ReportWriterToFileService WRITER_TO_FILE;

    @BeforeAll
    static void beforeAll() {
        WRITER_TO_FILE = new ReportWriterToFileServiceToCsvImpl();
    }

    @AfterEach
    void tearDown() {
        new File("src/test/resources/output/reportFile.csv").delete();
    }

    @DisplayName("Check writing to file in correct path")
    @Test
    void writeToFile_correctPath_ok() {
        List<String> expected = List.of("fruit,quantity",
                "banana,20",
                "apple,10");
        WRITER_TO_FILE.writeToFile(expected, "src/test/resources/output/reportFile.csv");
        List<String> actual;
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader("src/test/resources/output/reportFile.csv"))) {
            actual = bufferedReader.lines().collect(toList());
        } catch (IOException e) {
            throw new RuntimeException("Can't read the file: "
                    + "src/test/resources/output/reportFile.csv", e);
        }
        assertEquals(expected, actual);
    }

    @DisplayName("Check writing to file in incorrect path")
    @Test
    void writeToFile_incorrectPath_notOk() {
        assertThrows(RuntimeException.class, () ->
                WRITER_TO_FILE.writeToFile(List.of(),
                        "src/test/resources/incorrectPath/reportFile.csv"));
    }

    @DisplayName("Check writing to file with incorrect extension")
    @Test
    void writeToFile_incorrectExtension_notOk() {
        assertThrows(WrongExtensionFile.class, () ->
                WRITER_TO_FILE.writeToFile(List.of(),
                        "src/test/resources/reportFile.txt"));
    }
}
