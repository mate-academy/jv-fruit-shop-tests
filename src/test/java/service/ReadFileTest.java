package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.HelloWorld;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import service.impl.ParseServiceImpl;
import service.impl.ReaderServiceImpl;

public class ReadFileTest {
    private static String[] args;
    private static ReaderService readerService;
    private static ParseService parseService;

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
        readerService = new ReaderServiceImpl();
        parseService = new ParseServiceImpl();
    }

    @Test
    void fileValidation_withValidArgs_createsCorrectReport() throws IOException {
        HelloWorld.main(args);
        List<String> actual = Files.readAllLines(outputFile);
        List<String> result = List.of("fruit,quantity", "banana,152", "apple,90");
        assertEquals(result, actual);
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
                parseService.parseList(readerService.readFromFile(filePath)));
        assertEquals("Error reading file: " + filePath, exception.getMessage());
    }

    @Test
    void readFromFile_withEmptyFile_throwsException() {
        String filePath = getResourcePath("serviceResource/emptyFile.csv");
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException
                .class, () -> parseService.parseList(readerService.readFromFile(filePath)));
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
