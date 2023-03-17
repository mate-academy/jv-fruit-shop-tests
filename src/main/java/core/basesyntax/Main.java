package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.impl.FruitShopServiceImpl;
import core.basesyntax.impl.ReaderServiceImpl;
import core.basesyntax.impl.ReportServiceImpl;
import core.basesyntax.impl.WriteServiceImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.TransactionParserService;
import core.basesyntax.service.TransactionParserServiceImpl;
import core.basesyntax.service.WriteService;
import core.basesyntax.strategy.StrategyImpl;
import java.util.List;

public class Main {
    public static final String NAME_FILE_FROM =
            "src/main/java/core/basesyntax/resources/fruits.csv";
    public static final String NAME_FILE_TO =
            "src/main/java/core/basesyntax/resources/fruits_result.csv";
    private static final ReaderService readerService =
            new ReaderServiceImpl();
    private static final TransactionParserService transactionParserService =
            new TransactionParserServiceImpl();
    private static final FruitShopServiceImpl evaluateResultImpl =
            new FruitShopServiceImpl(new StrategyImpl());
    private static final ReportService reportService = new ReportServiceImpl();
    private static final WriteService writeService = new WriteServiceImpl();
    
    public static void main(String[] args) {
        List<String[]> infoFromFIle = readerService.read(NAME_FILE_FROM);
        List<FruitTransaction> report = transactionParserService.create(infoFromFIle);
        evaluateResultImpl.realizePattern(report);
        String result = reportService.createReport(Storage.storage);
        writeService.writeToFile(result, NAME_FILE_TO);
    }
}
