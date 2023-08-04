package service.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {
    private static final String csvFile = "csvFile.csv";
    private static final String notCsvFile = "notCsvFile.txt";
    private final FileReader fileReader = new CsvFileReader();

    @BeforeAll
    static void beforeAll() {
        String data = "fruit,quantity" + System.lineSeparator()
                + "b,banana,100" + System.lineSeparator()
                + "b,apple,123";
        try {
            Files.write(Path.of(csvFile), data.getBytes());
            Files.write(Path.of(notCsvFile), data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can`t write some of files");
        }
    }

    @Test
    void validCase_validFileType() {
        List<String> expected = List.of("fruit,quantity", "b,banana,100", "b,apple,123");
        List<String> actual = fileReader.read(csvFile);
        assertEquals(expected, actual);
    }

    @Test
    void invalidCase_notCsv() {
        String filePath = notCsvFile;
        assertThrows(IllegalStateException.class, () -> fileReader.read(filePath),
                "Invalid file type, should be .csv but - " + filePath);
    }

    @Test
    void invalidCase_nullFilePath() {
        assertThrows(IllegalStateException.class, () -> fileReader.read(null),
                "Invalid file type, should be .csv but - " + null);
    }

    @Test
    void invalidCase_fileNotExist() {
        String filePath = "some.csv";
        assertThrows(RuntimeException.class, () -> fileReader.read(filePath),
                "Can`t reed file - " + filePath);
    }

    @AfterAll
    static void afterAll() {
        try {
            Files.delete(Path.of(csvFile));
            Files.delete(Path.of(notCsvFile));
        } catch (IOException e) {
            throw new RuntimeException("Some of files can't be deleted");
        }
    }
}
