package core.basesyntax.strategy;

import core.basesyntax.converter.DataConverterImpl;
import core.basesyntax.filereader.FileReaderImpl;
import core.basesyntax.filewriter.FileWriterImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.report.ReportGeneratorImpl;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.service.BalanceOperation;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.PurchaseOperation;
import core.basesyntax.service.ReturnOperation;
import core.basesyntax.service.SupplyOperation;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class MainTest {

    @Test
    public void testMainProcess() throws IOException {
        // 1. Створення тимчасового файлу з вхідними даними
        File inputFile = File.createTempFile("inputReport", ".csv");
        FileWriter writer = new FileWriter(inputFile);
        writer.write("b,apple,100\n"
                + "s,apple,50\n"
                + "p,apple,30\n"
                + "r,apple,20\n");
        writer.close();

        // 2. Зчитування даних із вхідного файлу
        FileReaderImpl fileReader = new FileReaderImpl();
        List<String> inputReport = fileReader.read(inputFile.getAbsolutePath());

        // 3. Перетворення даних у список FruitTransaction
        DataConverterImpl dataConverter = new DataConverterImpl();
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);

        // 4. Налаштування мапи обробників операцій
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());

        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(operationHandlers);

        // 5. Обробка транзакцій
        ShopServiceImpl shopService = new ShopServiceImpl(operationStrategy);
        shopService.process(transactions);

        // 6. Генерація звіту
        ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl();
        String resultingReport = reportGenerator.getReport(shopService.getInventory());

        // 7. Перевірка результату
        String expectedReport = "fruit,quantity\n"
                + "apple,140\n";
        Assert.assertEquals(expectedReport, resultingReport);

        // 8. Створення тимчасового файлу для вихідного звіту
        File outputFile = File.createTempFile("finalReport", ".csv");
        FileWriterImpl fileWriter = new FileWriterImpl();
        fileWriter.write(resultingReport, outputFile.getAbsolutePath());

        // 9. Перевірка, чи звіт успішно записаний
        List<String> writtenReport = fileReader.read(outputFile.getAbsolutePath());
        Assert.assertEquals(expectedReport, String.join("\n", writtenReport));

        // Видалення тимчасових файлів
        inputFile.delete();
        outputFile.delete();
    }
}
