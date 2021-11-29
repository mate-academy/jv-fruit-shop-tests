package core.basesyntax;

import core.basesyntax.bd.Storage;
import core.basesyntax.service.FileReaderImpl;
import core.basesyntax.service.FileWriterImpl;
import core.basesyntax.service.FruitCounterImpl;
import core.basesyntax.service.InputDataValidatorImpl;
import core.basesyntax.service.LineParserImpl;
import core.basesyntax.service.ReportServiceImpl;
import service.FileReader;
import service.FileWriter;
import service.FruitCounter;
import service.LineParser;
import service.ReportService;
import strategy.implement.OperationStrategyImpl;

public class Main {
    private static final String PATH_TO_INPUT_FILE = "src\\main\\resources\\input.csv";
    private static final String PATH_TO_OUTPUT_FILE = "src\\main\\resources\\output.csv";

    public static void main(String[] args) {
        FileReader reader = new FileReaderImpl();
        LineParser parser = new LineParserImpl(new InputDataValidatorImpl());
        ReportService parse = new ReportServiceImpl();
        FruitCounter counter = new FruitCounterImpl(new OperationStrategyImpl());
        FileWriter writer = new FileWriterImpl();
        counter.fruitCounter(parser.lineParcer(reader.read(PATH_TO_INPUT_FILE)));
        writer.write(parse.createReport(Storage.storage), PATH_TO_OUTPUT_FILE);
    }
}
