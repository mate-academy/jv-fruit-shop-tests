package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import service.ShopService;

public class RunArgumentTest {

    private static String[] args;

    @TempDir
    private Path tempDir;
    private Path inputFile;
    private Path outputFile;
    private ShopService service;

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
    void processArgumentsAndRun_withValidArgs_createsCorrectReport() throws IOException {
        RunArgument.processArgumentsAndRun(args);
        List<String> actual = Files.readAllLines(outputFile);
        List<String> result = List.of("fruit,quantity", "banana,152", "apple,90");
        assertEquals(result, actual);
    }

    @Test
    void handlerNull() {
        FruitTransaction transaction = new FruitTransaction(null, "banana", 100);
        List<FruitTransaction> transactions = List.of(transaction);
        assertThrows(RuntimeException.class,() -> service.processTransactions(transactions));
    }

    @Test
    void processArgumentsAndRun_withInvalidArgsLength_throwsException() {
        String[] args = new String[]{"someFile.csv"};
        assertThrows(IllegalArgumentException.class, () -> HelloWorld.main(args));
    }

    @Test
    void processArgumentsAndRun_withEmptyInputFile_throwsException() {
        String[] args = new String[]{"", outputFile.toString()};
        assertThrows(IllegalArgumentException.class, () -> HelloWorld.main(args));
    }

    @Test
    void processArgumentsAndRun_withWhitespaceInputFile_throwsException() {
        String[] args = new String[]{"    ", outputFile.toString()};
        assertThrows(IllegalArgumentException.class, () -> HelloWorld.main(args));
    }

    @Test
    void processArgumentsAndRun_withWhitespaceOutputFile_throwsException() {
        String[] args = new String[]{inputFile.toString(), "        "};
        assertThrows(IllegalArgumentException.class, () -> HelloWorld.main(args));
    }

    @Test
    void processArgumentsAndRun_withEmptyOutputFile_throwsException() {
        String[] args = new String[]{inputFile.toString(), ""};
        assertThrows(IllegalArgumentException.class, () -> HelloWorld.main(args));
    }

    @Test
    void processArgumentsAndRun_withEmptyInputAndOutputFiles_throwsException() {
        String[] args = new String[]{"", ""};
        assertThrows(IllegalArgumentException.class, () -> HelloWorld.main(args));
    }

    @Test
    void processArgumentsAndRun_withNullInputFile_throwsException() {
        String[] args = new String[]{null, outputFile.toString()};
        assertThrows(IllegalArgumentException.class, () -> HelloWorld.main(args));
    }

    @Test
    void processArgumentsAndRun_withNullOutputFile_throwsException() {
        String[] args = new String[]{inputFile.toString(), null};
        assertThrows(IllegalArgumentException.class, () -> HelloWorld.main(args));
    }

    @Test
    void processArgumentsAndRun_withTooManyArgs_throwsException() {
        String[] args = new String[]{"someFiles", "fileResult", "Result"};
        assertThrows(IllegalArgumentException.class, () -> HelloWorld.main(args));
    }

    @Test
    void processArgumentsAndRun_withNonExistentInputFile_throwsException() {
        String[] args = new String[]{"nonexistent.csv", outputFile.toString()};
        assertThrows(IllegalArgumentException.class, () -> HelloWorld.main(args));
    }
}
