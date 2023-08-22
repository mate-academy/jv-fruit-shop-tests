package core.basesyntax.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {
    private static FileReader fileReader;

    @BeforeEach
    void init() {
        fileReader = new CsvFileReader();
    }

    @Test
    void readFileLines_regularCsvFileOutput_Ok() {
        String fileName = "src/test/resourcesTest/test-file.txt";
        String content = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,20" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator()
                + "s,banana,100" + System.lineSeparator();
        File file = new File(fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<String> lines = fileReader.readFileLines(fileName);

        Assertions.assertEquals(3, lines.size());
        Assertions.assertEquals("b,banana,20" + System.lineSeparator(), lines.get(0));
        Assertions.assertEquals("b,apple,100" + System.lineSeparator(), lines.get(1));
        Assertions.assertEquals("s,banana,100" + System.lineSeparator(), lines.get(2));
    }

    @Test
    void readFileLines_wrongPath_NotOk() {
        String nonExistentFile = "non-existent-file.txt";

        Assertions.assertThrows(RuntimeException.class,
                () -> fileReader.readFileLines(nonExistentFile));
    }
}
