package core.basesyntax.utils.filereader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderTest {
    private static IFileReader fileReader;
    private Path tempFile;

    @BeforeAll
    public static void beforeAll() {
        fileReader = new FileReader();
    }

    @BeforeEach
    public void setUp() throws IOException {
        tempFile = Files.createTempFile("tempFile", ".csv");
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    public void readCsvFile_shouldThrowRuntimeException_ifFailedReadFile() {
        Path invalidPath = Path.of("/invalid/path/file.csv");

        Assertions.assertThrows(
                RuntimeException.class,
                () -> fileReader.readCsvFile(invalidPath),
                "Should throw RuntimeException if failed read the file"
        );
    }

    @Test
    public void readCsvFile_shouldReturnEmptyList_ifFileContainsOnlyHeaders() throws IOException {
        List<String> fileContent = List.of("type,fruit,quantity");

        Files.write(tempFile, fileContent);

        List<String> result = fileReader.readCsvFile(tempFile);

        Assertions.assertTrue(result.isEmpty(), "List must be empty if file contains only headers");
    }

    @Test
    public void readCsvFile_shouldReturnDataLinesWithoutHeaders() throws IOException {
        String firstRecord = "b,banana,20";
        String secondRecord = "b,apple,50";

        List<String> fileContent = List.of("type,fruit,quantity", firstRecord, secondRecord);

        Files.write(tempFile, fileContent);

        List<String> result = fileReader.readCsvFile(tempFile);

        Assertions.assertEquals(
                firstRecord,
                result.get(0),
                "First record from file must be equal to first record in result"
        );

        Assertions.assertEquals(
                secondRecord,
                result.get(1),
                "Second record from file must be equal to second record in result"
        );
    }
}
