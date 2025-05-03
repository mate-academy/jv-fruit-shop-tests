package core.basesyntax.service;

import core.basesyntax.service.file.FileService;
import core.basesyntax.service.parse.ParseInput;
import core.basesyntax.service.report.GenerateFruitReport;
import java.util.List;

public class FruitShop {
    private static final String PATH_TO_WRITE = "src/main/resources/report.csv";
    private static final String PATH_TO_READ = "src/main/resources/activities.csv";

    public static void main(String[] args) {
        FileService fileService = new FileService();
        List<String> rawData = fileService.read(PATH_TO_READ);
        ParseInput parseInput = new ParseInput();
        List<String[]> parsedData = parseInput.parse(rawData);
        ProcessInputData processInputData = new ProcessInputData();
        processInputData.processInput(parsedData);
        GenerateFruitReport generateFruitReport = new GenerateFruitReport();
        String report = generateFruitReport.getReport();
        fileService.write(report, PATH_TO_WRITE);
    }
}
