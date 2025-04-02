package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class MainTest {

    private static String[] args;

    @TempDir
    private Path tempDir;
    private Path inputFile;
    private Path outputFile;

    @BeforeEach
    void create() throws IOException {
        inputFile = tempDir.resolve("reportToRead.csv");
        outputFile = tempDir.resolve("fileReport.csv");
        List<String> list = List.of("operation,fruit,quantity", "b,banana,100", "s,banana,60",
                "p,banana,10", "r,banana,2", "b,apple,80", "s,apple,20", "p,apple,10");
        args = new String[]{inputFile.toString(), outputFile.toString()};
        Files.write(inputFile, list);
    }

    @Test
    void main_withValidArgs_createsCorrectReport() throws IOException {
        HelloWorld.main(args);
        List<String> actual = Files.readAllLines(outputFile);
        List<String> result = List.of("fruit,quantity", "banana,152", "apple,90");
        assertEquals(result, actual);
    }

    @Test
    void main_withInvalidArgsLength_throwsException() {
        String[] args = new String[]{"someFile.csv"};
        assertThrows(IllegalArgumentException.class, () -> HelloWorld.main(args));
    }

    @Test
    void main_withEmptyInputFile_throwsException() {
        String[] args = new String[]{"", outputFile.toString()};
        assertThrows(IllegalArgumentException.class, () -> HelloWorld.main(args));
    }

    @Test
    void main_withWhitespaceInputFile_throwsException() {
        String[] args = new String[]{"    ", outputFile.toString()};
        assertThrows(IllegalArgumentException.class, () -> HelloWorld.main(args));
    }

    @Test
    void main_withWhitespaceOutputFile_throwsException() {
        String[] args = new String[]{inputFile.toString(), "        "};
        assertThrows(IllegalArgumentException.class, () -> HelloWorld.main(args));
    }

    @Test
    void main_withEmptyOutputFile_throwsException() {
        String[] args = new String[]{inputFile.toString(), ""};
        assertThrows(IllegalArgumentException.class, () -> HelloWorld.main(args));
    }

    @Test
    void main_withEmptyInputAndOutputFiles_throwsException() {
        String[] args = new String[]{"", ""};
        assertThrows(IllegalArgumentException.class, () -> HelloWorld.main(args));
    }

    @Test
    void main_withNullInputFile_throwsException() {
        String[] args = new String[]{null, outputFile.toString()};
        assertThrows(IllegalArgumentException.class, () -> HelloWorld.main(args));
    }

    @Test
    void main_withNullOutputFile_throwsException() {
        String[] args = new String[]{inputFile.toString(), null};
        assertThrows(IllegalArgumentException.class, () -> HelloWorld.main(args));
    }

    @Test
    void main_withTooManyArgs_throwsException() {
        String[] args = new String[]{"someFiles", "fileResult", "Result"};
        assertThrows(IllegalArgumentException.class, () -> HelloWorld.main(args));
    }

    @Test
    void main_withNonExistentInputFile_throwsException() {
        String[] args = new String[]{"nonexistent.csv", outputFile.toString()};
        assertThrows(IllegalArgumentException.class, () -> HelloWorld.main(args));
    }
}
