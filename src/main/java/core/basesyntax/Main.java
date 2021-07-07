package core.basesyntax;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.LineValidator;
import core.basesyntax.service.Parser;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.LineValidatorImpl;
import core.basesyntax.service.impl.MapCreatorImpl;
import core.basesyntax.service.impl.ParserImpl;
import core.basesyntax.strategy.OperationHandler;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE = "src/main/resources/inputFile.cvs";
    private static final String OUTPUT_FILE = "src/main/resources/outputFile.txt";

    public static void main(String[] args) {
        Map<String, OperationHandler> handlers = new MapCreatorImpl().createMap();

        FileReader fileReader = new FileReaderImpl();
        List<String> linesFromFile = fileReader.readFromFile(INPUT_FILE);

        LineValidator lineValidator = new LineValidatorImpl();
        Parser parser = new ParserImpl(lineValidator);

        linesFromFile.stream()
                .skip(1)
                .map(parser::parseLine)
                .forEach(t -> handlers.get(t.getOperation()).apply(t));

        FruitService fruitService = new FruitServiceImpl();
        String report = fruitService.getReport();
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.writeToFile(report, OUTPUT_FILE);
    }
}
