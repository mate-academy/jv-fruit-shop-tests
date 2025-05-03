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
import strategy.BalanceStrategy;
import strategy.PurchaseStrategy;
import strategy.ReturnStrategy;
import strategy.SupplyStrategy;
import strategy.TransactionStrategy;

public class FileWriterTest {
    private static final String REPORT_FILE_NAME = "src/test/resources/reportFileName";

    private static final List<String> EXPECTED_RESULT =
            new ArrayList<>(Arrays.asList("banana,152", "apple,90"));

    private static final Map<FruitTransaction.Operation, TransactionStrategy> strategyMap = Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceStrategy(),
            FruitTransaction.Operation.SUPPLY, new SupplyStrategy(),
            FruitTransaction.Operation.PURCHASE, new PurchaseStrategy(),
            FruitTransaction.Operation.RETURN, new ReturnStrategy());

    private FileWriter fileWriter = new FileWriterImpl();
    private FileReader fileReader = new FileReaderImpl();

    @Test
    public void writeAndGetOk() {
        FruitStorage fruitStorage = new FruitStorage();
        fruitStorage.addQuantity("banana", 152);
        fruitStorage.addQuantity("apple", 90);

        fileWriter.write(fruitStorage, REPORT_FILE_NAME);

        List<String> strings = fileReader.read(REPORT_FILE_NAME);
        Assert.assertEquals(EXPECTED_RESULT, strings);
    }
}
