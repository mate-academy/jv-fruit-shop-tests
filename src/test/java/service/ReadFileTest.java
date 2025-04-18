package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import service.impl.ReaderServiceImpl;

public class ReadFileTest {
    private static ReaderService readerService;

    @TempDir
    private Path tempDir;
    private Path inputFile;
    private Path outputFile;

    @BeforeEach
    void create() {
        inputFile = tempDir.resolve("reportToRead.csv");
        outputFile = tempDir.resolve("fileReport.csv");
        readerService = new ReaderServiceImpl();
    }

    @Test
    void fileValidation_withValidArgs_doesNotThrowException() {
        List<String> result = readerService.readFromFile("src/main/resources/reportToRead.csv");
        List<String> expected = List.of(
                "operation,fruit,quantity",
                "b,banana,100",
                "s,banana,60",
                "p,banana,10",
                "r,banana,2",
                "b,apple,80",
                "s,apple,20",
                "p,apple,10"
        );
        assertEquals(expected, result);
    }

    @Test
    void fileValidation_withInvalidArgsLength_throwsException() {
        String[] args = new String[]{"someFile.csv"};
        IllegalArgumentException illegalArgumentException =
                assertThrows(IllegalArgumentException.class, () ->
                        readerService.fileValidation(args));
        assertEquals("Please provide input and output file names "
                + "as arguments: <inputFile> <outputFile>",
                illegalArgumentException.getMessage());
    }

    @Test
    void fileValidation_withEmptyInputFile_throwsException() {
        String[] args = new String[]{"", outputFile.toString()};
        IllegalArgumentException illegalArgumentException =
                assertThrows(IllegalArgumentException.class, () ->
                        readerService.fileValidation(args));
        assertEquals("Input file names cannot be empty",
                illegalArgumentException.getMessage());
    }

    @Test
    void fileValidation_withWhitespaceInputFile_throwsException() {
        String[] args = new String[]{"    ", outputFile.toString()};
        IllegalArgumentException illegalArgumentException =
                assertThrows(IllegalArgumentException.class, () ->
                        readerService.fileValidation(args));
        assertEquals("Input file names cannot be empty",
                illegalArgumentException.getMessage());
    }

    @Test
    void fileValidation_withWhitespaceOutputFile_throwsException() {
        String[] args = new String[]{inputFile.toString(), "        "};
        IllegalArgumentException illegalArgumentException =
                assertThrows(IllegalArgumentException.class, () ->
                        readerService.fileValidation(args));
        assertEquals("Output file names cannot be empty",
                illegalArgumentException.getMessage());
    }

    @Test
    void fileValidation_withEmptyOutputFile_throwsException() {
        String[] args = new String[]{inputFile.toString(), ""};
        IllegalArgumentException illegalArgumentException =
                assertThrows(IllegalArgumentException.class, () ->
                        readerService.fileValidation(args));
        assertEquals("Output file names cannot be empty",
                illegalArgumentException.getMessage());
    }

    @Test
    void fileValidation_withEmptyInputAndOutputFiles_throwsException() {
        String[] args = new String[]{"", ""};
        IllegalArgumentException illegalArgumentException =
                assertThrows(IllegalArgumentException.class, () ->
                        readerService.fileValidation(args));
        assertEquals("Input and output file names cannot be empty",
                illegalArgumentException.getMessage());
    }

    @Test
    void fileValidation_withNullInputFile_throwsException() {
        String[] args = new String[]{null, outputFile.toString()};
        NullPointerException nullPointerException =
                assertThrows(NullPointerException.class, () ->
                        readerService.fileValidation(args));
        assertEquals("Input file names cannot be null",
                nullPointerException.getMessage());
    }

    @Test
    void fileValidation_withNullOutputFile_throwsException() {
        String[] args = new String[]{inputFile.toString(), null};
        NullPointerException nullPointerException =
                assertThrows(NullPointerException.class, () ->
                        readerService.fileValidation(args));
        assertEquals("Output file names cannot be null",
                nullPointerException.getMessage());
    }

    @Test
    void fileValidation_withNullOutputFileAndInputFile_throwsException() {
        String[] args = new String[]{null, null};
        NullPointerException nullPointerException =
                assertThrows(NullPointerException.class, () ->
                        readerService.fileValidation(args));
        assertEquals("Input and output file names cannot be null",
                nullPointerException.getMessage());
    }

    @Test
    void fileValidation_withTooManyArgs_throwsException() {
        String[] args = new String[]{"someFiles", "fileResult", "Result"};
        IllegalArgumentException illegalArgumentException =
                assertThrows(IllegalArgumentException.class, () ->
                        readerService.fileValidation(args));
        assertEquals("Please provide input and output file "
                + "names as arguments: <inputFile> <outputFile>",
                illegalArgumentException.getMessage());
    }

    @Test
    void fileValidation_withNonExistentInputFile_throwsException() {
        String file = "nonexistent.csv";
        String[] args = new String[]{file, outputFile.toString()};
        IllegalArgumentException illegalArgumentException =
                assertThrows(IllegalArgumentException.class, () ->
                        readerService.fileValidation(args));
        assertEquals("Input file does not exist: " + file,
                illegalArgumentException.getMessage());
    }

    @Test
    void readFromFile_throwsException_whenFileNotFound() {
        String filePath = "example.csv";
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                readerService.readFromFile(filePath));
        assertEquals("Error reading file: " + filePath, exception.getMessage());
    }

    @Test
    void readFromFile_withEmptyFile_throwsException() {
        String filePath = getResourcePath("serviceResource/emptyFile.csv");
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException
                .class, () -> readerService.readFromFile(filePath));
        assertEquals("File must contain at least a header", illegalArgumentException.getMessage());
    }

    public String getResourcePath(String fileName) {
        try {
            return Paths.get(this.getClass().getClassLoader()
                .getResource(fileName)
                .toURI())
                .toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
