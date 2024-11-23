package core.basesyntax;

import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;
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
        List<String> rawData = null;
        try {
            rawData = fileReader.read(inputFilePath);
        } catch (NoSuchFileException e) {
            throw new RuntimeException(e);
        }
        List<FruitTransaction> transactions = dataConverter.convert(rawData);
        dataProcessor.process(transactions);
        List<String> report = reportGenerator.generate();
        try {
            fileWriter.write(report, outputFilePath);
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }
}

