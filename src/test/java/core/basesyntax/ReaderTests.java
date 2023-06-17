package core.basesyntax;

import core.basesyntax.db.FruitShopStorage;
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReaderTests {
    private String pathInputFile;
    private String pathInputFileWrong;
    private String valueInputFile;
    private ParserReaderService parserReaderService;
    private List<FruitTransaction> listFruitTransaction;
    private FruitShopStorage fruitShopStorageDefault;
    private List<String> valueFileExpected;

    @BeforeEach
    void setUp() {
        pathInputFile = "src/test/resources/inputTest.csv";
        pathInputFileWrong = "src/test/resources/input.csv";
        valueInputFile = "type,fruit,quantityb,banana,20b,apple,100";
        valueFileExpected = Arrays.asList(
                "test",
                "test file",
                "test test file.");
        parserReaderService = new ParserReaderServiceImpl();
        setUpListFruitTransaction();
        setUpFruitShopStorageDefault();
    }

    private void setUpListFruitTransaction() {
        listFruitTransaction = new ArrayList<>();
        listFruitTransaction.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        listFruitTransaction.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
    }

    private void setUpFruitShopStorageDefault() {
        fruitShopStorageDefault = new FruitShopStorage();
        fruitShopStorageDefault.put("banana", 100);
        fruitShopStorageDefault.put("apple", 100);
    }

    @Test
    void readFile_fileExist_ok() throws IOException {
        List<String> allStrings = Files.readAllLines(Path.of(pathInputFile));
        String actual = allStrings.stream().collect(Collectors.joining());
        Assertions.assertEquals(valueInputFile, actual);
    }

    @Test
    void readFile_fileNotExist_notOk() {
        Assertions.assertThrows(NoSuchFileException.class, () -> {
            Files.readAllLines(Path.of(pathInputFileWrong));
        });
    }

    @Test
    void readFile_fileDoesNotExist_ok() {
        ReaderService readerService = new ReaderServiceImpl();
        Assertions.assertThrows(RuntimeException.class, () -> {
            readerService.readFile(pathInputFileWrong);
        });
    }

    @Test
    void parse_parseAllStrings_ok() throws IOException {
        List<String> allStrings = Files.readAllLines(Path.of(pathInputFile));
        List<FruitTransaction> actual = parserReaderService.parse(allStrings);
        Assertions.assertEquals(listFruitTransaction, actual);
    }

    @AfterEach
    public void afterEachTest() {
        fruitShopStorageDefault.clear();
    }
}
