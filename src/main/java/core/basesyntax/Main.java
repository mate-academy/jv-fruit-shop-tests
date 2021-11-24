package core.basesyntax;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.Parser;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.ParserImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.ValidatorImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.service.strategy.AddHandler;
import core.basesyntax.service.strategy.OptionHandler;
import core.basesyntax.service.strategy.PurchaseHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ReaderService reader = new ReaderServiceImpl();
        List<String> lines = reader.readFromFile("src/main/resources/input.csv");
        Parser parser = new ParserImpl(new ValidatorImpl());
        FruitDao dao = new FruitDaoImpl();

        Map<String, OptionHandler> options = new HashMap<>();
        options.put("b", new AddHandler(dao));
        options.put("s", new AddHandler(dao));
        options.put("p", new PurchaseHandler(dao));
        options.put("r", new AddHandler(dao));

        List<Transaction> transactions = parser.parse(lines);
        for (Transaction transaction : transactions) {
            String operation = transaction.getOperation();
            OptionHandler handler = options.get(operation);
            handler.apply(transaction);
        }

        ReportService reportService = new ReportServiceImpl(dao);
        new WriterServiceImpl().writeToFile(reportService.createReport(),
                "src/main/resources/output.csv");
    }
}
