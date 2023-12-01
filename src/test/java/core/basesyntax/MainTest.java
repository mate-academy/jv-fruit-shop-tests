package core.basesyntax;

import db.FruitStorage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.Assert;
import org.junit.Test;
import service.FileReader;
import service.FileReaderImpl;
import service.FileWriter;
import service.FileWriterImpl;
import service.FruitStoreService;
import service.FruitStoreServiceImpl;
import service.FruitTransactionService;
import service.FruitTransactionServiceImpl;
import strategy.BalanceStrategy;
import strategy.PurchaseStrategy;
import strategy.ReturnStrategy;
import strategy.SupplyStrategy;
import strategy.TransactionStrategy;

public class MainTest {
    private static final String INPUT_FILE_NAME = "src/test/resources/testStorage";
    private static final String REPORT_FILE_NAME = "src/test/resources/reportFileName";

    private static final List<String> expectedResult =
            new ArrayList<>(Arrays.asList("banana,152", "apple,90"));

    private static final Map<FruitTransaction.Operation, TransactionStrategy> strategyMap = Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceStrategy(),
            FruitTransaction.Operation.SUPPLY, new SupplyStrategy(),
            FruitTransaction.Operation.PURCHASE, new PurchaseStrategy(),
            FruitTransaction.Operation.RETURN, new ReturnStrategy());

    @Test
    public void reportFileTest() {
        FileReader fileReader = new FileReaderImpl();
        List<String> fruitTransactionStorageStrings = fileReader.read(INPUT_FILE_NAME);

        FruitTransactionService fruitTransactionService = new FruitTransactionServiceImpl();
        List<FruitTransaction> fruitTransactionStorage = fruitTransactionService
                .parseTransactions(fruitTransactionStorageStrings);

        FruitStoreService fruitStoreService = new FruitStoreServiceImpl(strategyMap);
        FruitStorage fruitStorage = fruitStoreService.processTransactions(fruitTransactionStorage);

        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write(fruitStorage, REPORT_FILE_NAME);

        List<String> strings = fileReader.read(REPORT_FILE_NAME);
        Assert.assertEquals(expectedResult, strings);
    }
}
