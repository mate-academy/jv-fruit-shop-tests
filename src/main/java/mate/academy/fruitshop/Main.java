package mate.academy.fruitshop;

import mate.academy.fruitshop.io.CsvFileReader;
import mate.academy.fruitshop.io.CsvFileReaderImpl;
import mate.academy.fruitshop.io.FileWriter;
import mate.academy.fruitshop.io.FileWriterImpl;
import mate.academy.fruitshop.model.FruitTransaction;
import mate.academy.fruitshop.service.DataConverter;
import mate.academy.fruitshop.service.OperationHandler;
import mate.academy.fruitshop.service.OperationStrategy;
import mate.academy.fruitshop.service.ReportGenerator;
import mate.academy.fruitshop.service.ShopService;
import mate.academy.fruitshop.service.impl.BalanceOperation;
import mate.academy.fruitshop.service.impl.DataConverterImpl;
import mate.academy.fruitshop.service.impl.OperationStrategyImpl;
import mate.academy.fruitshop.service.impl.PurchaseOperation;
import mate.academy.fruitshop.service.impl.ReportGeneratorImpl;
import mate.academy.fruitshop.service.impl.ReturnOperation;
import mate.academy.fruitshop.service.impl.ShopServiceImpl;
import mate.academy.fruitshop.service.impl.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static final String INPUT_FILE = "src/main/resources/reportToRead.csv";
    private static final String OUTPUT_FILE = "src/main/resources/finalReport.csv";

    public static void main(String[] args) {

        CsvFileReader reader = new CsvFileReaderImpl();
        List<String> input = reader.read(INPUT_FILE);

        DataConverter converter = new DataConverterImpl();
        List<FruitTransaction> transactions = converter.convertToTransaction(input);

        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

        OperationStrategy strategy = new OperationStrategyImpl(handlers);

        ShopService shopService = new ShopServiceImpl(strategy);
        shopService.process(transactions);

        ReportGenerator reportGenerator =
                new ReportGeneratorImpl(shopService.getStorage());

        String report = reportGenerator.getReport();

        FileWriter writer = new FileWriterImpl();
        writer.write(report, OUTPUT_FILE);
    }
}
