package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import service.ReaderService;
import service.impl.ParseServiceImpl;
import service.impl.ReaderServiceImpl;

public class ReadFileTest {
    private static ReaderService readerService;

    @TempDir
    private Path tempDir;

    @BeforeEach
    void create() throws IOException {
        Path inputFile = tempDir.resolve("reportToRead.csv");
        List<String> list = List.of("operation,fruit,quantity", "b,banana,100", "s,banana,60",
                "p,banana,10", "r,banana,2", "b,apple,80", "s,apple,20", "p,apple,10");
        Files.write(inputFile, list);
        ParseServiceImpl parseService = new ParseServiceImpl();
        readerService = new ReaderServiceImpl(parseService);

    }

    @Test
    void readFromFile_withEmptyFile_throwsException() throws IOException {
        List<String> result = List.of();
        Path testFile = tempDir.resolve("testFile.csv");
        Files.write(testFile, result);
        assertThrows(IllegalArgumentException.class, () -> readerService
                .readFromFile(testFile.toString()));
    }

    @Test
    void nameIsEmpty() throws IOException {
        List<String> list = List.of("operation,fruit,quantity", "b,banana,100", "s,banana,60",
                "p,banana,10", "r,banana,2", "b,apple,80", "s,apple,20", "p,apple,10");
        Path testFile = tempDir.resolve("testFile.csv");
        Files.write(testFile, list);
        assertThrows(RuntimeException.class, () -> readerService.readFromFile(""));
    }

    @Test
    void readFromFile_withEmptyLine_throwsException() throws IOException {
        List<String> list = List.of("operation,fruit,quantity", "", "b,banana,100", "s,banana,60",
                "p,banana,10", "r,banana,2", "b,apple,80", "s,apple,20", "p,apple,10");
        Path testFile = tempDir.resolve("testFile.csv");
        Files.write(testFile, list);
        assertThrows(IllegalArgumentException.class, () -> readerService
                .readFromFile(testFile.toString()));
    }

    @Test
    void readFromFile_withInvalidFieldCount_throwsException() throws IOException {
        List<String> list = List.of("operation,fruit,quantity", "b,banana", "s,banana,60",
                "p,banana,10", "r,banana,2", "b,apple,80", "s,apple,20", "p,apple,10");
        Path testFile = tempDir.resolve("testFile.csv");
        Files.write(testFile, list);
        assertThrows(IllegalArgumentException.class, () -> readerService
                .readFromFile(testFile.toString()));
    }

    @Test
    void readFromFile_withInvalidOperationCode_throwsException() throws IOException {
        List<String> list = List.of("operation,fruit,quantity", "b,banana,100", "h,banana,60",
                "p,banana,10", "r,banana,2", "b,apple,80", "s,apple,20", "p,apple,10");
        Path testFile = tempDir.resolve("testFile.csv");
        Files.write(testFile, list);
        assertThrows(IllegalArgumentException.class, () -> readerService
                .readFromFile(testFile.toString()));
    }

    @Test
    void readFromFile_withEmptyFruit_throwsException() throws IOException {
        List<String> list = List.of("operation,fruit,quantity", "b, ,100", "s,banana,60",
                "p,banana,10", "r,banana,2", "b,apple,80", "s,apple,20", "p,apple,10");
        Path testFile = tempDir.resolve("testFile.csv");
        Files.write(testFile, list);
        assertThrows(IllegalArgumentException.class, () -> readerService
                .readFromFile(testFile.toString()));
    }

    @Test
    void readFromFile_withNegativeQuantity_throwsException() throws IOException {
        List<String> list = List.of("operation,fruit,quantity", "b,banana,-200", "s,banana,60",
                "p,banana,10", "r,banana,2", "b,apple,80", "s,apple,20", "p,apple,10");
        Path testFile = tempDir.resolve("testFile.csv");
        Files.write(testFile, list);
        assertThrows(IllegalArgumentException.class, () -> readerService
                .readFromFile(testFile.toString()));
    }

    @Test
    void readFromFile_withZeroQuantity_throwsException() throws IOException {
        List<String> list = List.of("operation,fruit,quantity", "b,banana,0", "s,banana,60",
                "p,banana,10", "r,banana,2", "b,apple,80", "s,apple,20", "p,apple,10");
        Path testFile = tempDir.resolve("testFile.csv");
        Files.write(testFile, list);
        assertThrows(IllegalArgumentException.class, () -> readerService
                .readFromFile(testFile.toString()));
    }
}
