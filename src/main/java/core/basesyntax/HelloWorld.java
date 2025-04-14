package core.basesyntax;

import db.Storage;
import java.util.List;
import model.FruitTransaction;
import report.impl.ReportFormatter;
import service.ParseService;
import service.ReaderService;
import service.ShopService;
import service.WriterService;
import service.impl.ParseServiceImpl;
import service.impl.ReaderServiceImpl;
import service.impl.ShopServiceImpl;
import service.impl.WriterServiceImpl;

public class HelloWorld {
    private static final int INPUT_INDEX = 0;
    private static final int OUTPUT_INDEX = 1;
    private static final ReaderService read = new ReaderServiceImpl();
    private static final ParseService parse = new ParseServiceImpl();
    private static final ShopService shopservice = new ShopServiceImpl();
    private static final ReportFormatter formatter = new ReportFormatter();
    private static final WriterService write = new WriterServiceImpl();

    public static void main(String[] args) {
        read.fileValidation(args);
        String inputFilePath = args[INPUT_INDEX];
        String outputFilePath = args[OUTPUT_INDEX];
        List<FruitTransaction> transactions = parse.parseList(read.readFromFile(inputFilePath));
        shopservice.processTransactions(transactions);
        write.writeReport(formatter.format(Storage.storage), outputFilePath);
    }
}
