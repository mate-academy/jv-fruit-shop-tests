package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderCsvImplTest {
    public static final String INPUT_FILE = "src/test/java/resources/TestInput.csv";
    private FruitFileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderCsvImpl();
    }

    @Test
    void testFileReaderNotCsv_NotOk() {
        assertThrows(IllegalArgumentException.class,() -> fileReader.read("Test.txt"));
    }

    @Test
    void testFileReaderThrowsException_NotOk() {
        assertThrows(RuntimeException.class,() -> fileReader.read(""));
    }

    @Test
    void testFileReader_Ok() throws IOException {
        Path inputFile = Path.of(INPUT_FILE);

        Files.write(inputFile, List.of(
                "type,fruit,quantity",
                "b,apple,100",
                "s,apple,50",
                "p,apple,30",
                "r,apple,10",
                "b,banana,20",
                "p,banana,5"
        ));

        List<String> fileInputImpl = fileReader.read(INPUT_FILE);
        List<String> fileInputFiles = Files.readAllLines(inputFile);
        assertEquals(fileInputImpl, fileInputFiles);
    }
}
