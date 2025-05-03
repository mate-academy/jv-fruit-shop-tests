package core.basesyntax.service;

import core.basesyntax.dataprocessor.DataConverter;
import core.basesyntax.dataprocessor.DataProcessor;
import core.basesyntax.fileprocessor.FileReader;
import core.basesyntax.fileprocessor.FileWriter;
import java.util.List;

public class FruitShop {
    private final FileReader fileReader;
    private final DataConverter dataConverter;
    private final DataProcessor dataProcessor;
    private final ReportGenerator reportGenerator;
    private final FileWriter fileWriter;

    public FruitShop(FileReader fileReader, DataConverter dataConverter,
                     DataProcessor dataProcessor, ReportGenerator reportGenerator,
                     FileWriter fileWriter) {
        this.fileReader = fileReader;
        this.dataConverter = dataConverter;
        this.dataProcessor = dataProcessor;
        this.reportGenerator = reportGenerator;
        this.fileWriter = fileWriter;
    }

    public void run(String inputFilePath, String outputFilePath) {
        try {
            List<String> rawData = fileReader.read(inputFilePath);
            List<FruitTransaction> transactions = dataConverter.convert(rawData);
            dataProcessor.process(transactions);
            List<String> report = reportGenerator.generateReport();
            fileWriter.write(report, outputFilePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
