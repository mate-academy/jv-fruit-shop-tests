package core.basesyntax;

import core.basesyntax.impl.ParserReaderServiceImpl;
import core.basesyntax.impl.ReaderServiceImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserReaderService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.util.Operation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReaderServiceImplTests {
    private static final String PATH_INPUT_FILE = "src/test/resources/inputTest.csv";
    private static final String PATH_INPUT_FILE_WRONG = "src/test/resources/input.csv";
    private String valueInputFile;
    private List<String> valueFileExpected;

    @BeforeEach
    void setUp() {
        valueInputFile = "type,fruit,quantityb,banana,20b,apple,100";
        valueFileExpected = Arrays.asList(
                "test",
                "test file",
                "test test file.");
    }

    @Test
    void readFile_fileExist_ok() throws IOException {
        List<String> allStrings = Files.readAllLines(Path.of(PATH_INPUT_FILE));
        String actual = allStrings.stream().collect(Collectors.joining());
        Assertions.assertEquals(valueInputFile, actual);
    }

    @Test
    void readFile_fileNotExist_notOk() {
        Assertions.assertThrows(NoSuchFileException.class, () -> {
            Files.readAllLines(Path.of(PATH_INPUT_FILE_WRONG));
        });
    }

    @Test
    void readFile_fileDoesNotExist_ok() {
        ReaderService readerService = new ReaderServiceImpl();
        Assertions.assertThrows(RuntimeException.class, () -> {
            readerService.readFile(PATH_INPUT_FILE_WRONG);
        });
    }

    @Test
    void parse_parseAllStrings_ok() throws IOException {
        ParserReaderService parserReaderService = new ParserReaderServiceImpl();
        List<FruitTransaction> listFruitTransaction = new ArrayList<>();
        listFruitTransaction.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        listFruitTransaction.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
        List<String> allStrings = Files.readAllLines(Path.of(PATH_INPUT_FILE));
        List<FruitTransaction> actual = parserReaderService.parse(allStrings);
        Assertions.assertEquals(listFruitTransaction, actual);
    }
}
