package core.basesyntax.data;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ShopServiceTest {

    private static final String TEST_DIRECTORY = "src/main/resources/test";
    private static final String FINAL_REPORT_PATH = TEST_DIRECTORY + "/testFinalReport.csv";

    private static List<FruitTransaction> fruitTransactions = null;
    private static ShopService shopService = null;

    @BeforeAll
    static void initMap() {
        fruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 120),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 24)
        );
        Map<FruitTransaction.Operation, OperationHandler> operationHandler
                = new HashMap<>();
        operationHandler.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandler.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandler.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandler.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategy(operationHandler);
        shopService = new ShopService(operationStrategy, FINAL_REPORT_PATH);
    }

    @AfterEach
    void removeAllTestsFiles() {
        try (Stream<Path> files = Files.walk(Paths.get(TEST_DIRECTORY))) {
            files.filter(Files::isRegularFile)
                    .filter(file -> !file.getFileName().toString().equals("testReportToRead.csv"))
                    .forEach(file -> {
                        try {
                            Files.delete(file);
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to delete test files", e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Failed to clean test files", e);
        }
    }

    @Test
    void processOperationWithCorrectData_OK() {
        shopService.process(fruitTransactions);
        try {
            String actualContent = Files.readString(Paths.get(FINAL_REPORT_PATH));
            String expectedContent1 = "fruit,quantity";
            String expectedContent2 = "banana,168";
            String expectedContent3 = "apple,140";
            Assertions.assertTrue(actualContent.trim().contains(expectedContent1));
            Assertions.assertTrue(actualContent.trim().contains(expectedContent2));
            Assertions.assertTrue(actualContent.trim().contains(expectedContent3));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read final report", e);
        }
    }

    @Test
    void processOperationWithIncorrectData_NotOK() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> shopService.process(List.of(
                        new FruitTransaction(
                                FruitTransaction.Operation.BALANCE, "banana", -1))));
    }
}
