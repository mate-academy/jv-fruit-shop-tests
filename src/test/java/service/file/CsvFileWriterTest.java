package service.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {
    private final FileWriter fileWriter = new CsvFileWriter();

    @Test
    void validCase_csvType() {
        String fileName = "some.csv";
        Path path = Path.of(fileName);
        List<String> data = List.of("banana,120", "apple,174", "orange,0");
        fileWriter.write(data, fileName);
        assertTrue(Files.exists(path));
        try {
            List<String> dataInFile = Files.readAllLines(path);
            assertEquals(data, dataInFile);
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file - " + fileName);
        }
    }

    @Test
    void validCase_txtType() {
        String fileName = "some.txt";
        Path path = Path.of(fileName);
        List<String> data = List.of("banana,120", "apple,174", "orange,0");
        fileWriter.write(data, fileName);
        assertTrue(Files.exists(path));
        try {
            List<String> dataInFile = Files.readAllLines(path);
            assertEquals(data, dataInFile);
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file - " + fileName);
        }
    }

    @Test
    void validCase_emptyData() {
        String fileName = "some.csv";
        Path path = Path.of(fileName);
        List<String> data = List.of();
        fileWriter.write(data, fileName);
        assertTrue(Files.exists(path));
        try {
            List<String> dataInFile = Files.readAllLines(path);
            assertEquals(data, dataInFile);
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file - " + fileName);
        }
    }

    @Test
    void invalidCase_emptyPath() {
        String fileName = "";
        List<String> data = List.of("banana,120", "apple,174", "orange,0");
        assertThrows(RuntimeException.class, () -> fileWriter.write(data, fileName),
                "File cannot bе written - ");
    }

    @Test
    void invalidCase_nullData() {
        String fileName = "some.csv";
        assertThrows(RuntimeException.class, () -> fileWriter.write(null, fileName),
                "File cannot bе written - ");
    }

    @Test
    void invalidCase_nullPath() {
        List<String> data = List.of("banana,120", "apple,174", "orange,0");
        assertThrows(NullPointerException.class, () -> fileWriter.write(data, null));
    }
}
