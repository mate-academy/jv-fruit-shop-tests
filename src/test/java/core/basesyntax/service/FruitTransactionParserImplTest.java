package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import core.basesyntax.testservice.ConfigReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FruitTransactionParserImplTest {
    private static FruitTransactionParser fruitTransactionParser;
    private static CsvFileReaderService csvFileReaderService;
    private static ConfigReader configReader;
    private static Path fileCopyPath;
    private static final String INVALID_LINE = "p,banana,invalid_quantity";
    private static final int NULL_LIST_SIZE = 0;
    private static final List<String> INVALID_QUANTITY_IN_LIST = Arrays.asList("b,banana,20",
            "b,apple,invalid_quantity", "s,banana,100");
    private static final List<FruitTransaction> expectedTransactions = Arrays.asList(
            new FruitTransaction(Operation.BALANCE, "banana", 20),
            new FruitTransaction(Operation.BALANCE, "apple", 100),
            new FruitTransaction(Operation.SUPPLY, "banana", 100),
            new FruitTransaction(Operation.PURCHASE, "banana", 13),
            new FruitTransaction(Operation.RETURN, "apple", 10),
            new FruitTransaction(Operation.PURCHASE, "apple", 20),
            new FruitTransaction(Operation.PURCHASE, "banana", 5),
            new FruitTransaction(Operation.SUPPLY, "banana", 50)
    );

    @BeforeAll
    public static void init() {
        fruitTransactionParser = new FruitTransactionParserImpl();
        csvFileReaderService = new CsvFileReaderServiceImpl();
        configReader = new ConfigReader();
    }

    @BeforeEach
    public void createFileCopy() throws IOException {
        fileCopyPath = Files.createTempDirectory("temp")
                .resolve("file_copy.csv");
        Files.copy(Path.of(configReader.getValueByKey("file.read.from")),
                fileCopyPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @AfterEach
    public void restoreFileCopy() throws IOException {
        Path filePath = Path.of(configReader.getValueByKey("file.read.from"));
        Files.copy(fileCopyPath, filePath, StandardCopyOption.REPLACE_EXISTING);
        Files.delete(fileCopyPath);
    }

    @Test
    public void parseTransactions_InvalidQuantity_isNotOk() {
        List<String> fileLines = new ArrayList<>();
        fileLines.add(INVALID_LINE);
        Assertions.assertThrows(RuntimeException.class,
                () -> fruitTransactionParser.parseTransactions(fileLines),
                "RuntimeException expected");
    }

    @Test
    public void parseTransactions_emptyList_returnsEmptyList_isOk() {
        List<String> fileLines = new ArrayList<>();
        List<FruitTransaction> fruitTransactions
                = fruitTransactionParser.parseTransactions(fileLines);
        Assertions.assertEquals(NULL_LIST_SIZE, fruitTransactions.size(),
                "The empty parsed fruitTransactions list expected");
    }

    @Test
    public void parseTransactions_invalidQuantity_throwsRuntimeException_isOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                fruitTransactionParser.parseTransactions(INVALID_QUANTITY_IN_LIST));
    }

    @Test
    public void parseTransactions_returnValidData_isOk() {
        List<FruitTransaction> fruitTransactions = fruitTransactionParser
                .parseTransactions(csvFileReaderService
                .readDataFromFile(configReader.getValueByKey("file.read.from")));

        Assertions.assertEquals(expectedTransactions.size(), fruitTransactions.size(),
                "Comparison list sizes failed");
        for (int i = 0; i < expectedTransactions.size(); i++) {
            FruitTransaction expectedTransaction = expectedTransactions.get(i);
            FruitTransaction actualTransaction = fruitTransactions.get(i);

            Assertions.assertEquals(expectedTransaction.getOperation(),
                    actualTransaction.getOperation(),
                    "Comparison operations failed");
            Assertions.assertEquals(expectedTransaction.getFruit().toLowerCase(),
                    actualTransaction.getFruit().toLowerCase(), "Comparison fruits failed");
            Assertions.assertEquals(expectedTransaction.getQuantity(),
                    actualTransaction.getQuantity(),
                    "Comparison quantities failed");
        }
    }
}
