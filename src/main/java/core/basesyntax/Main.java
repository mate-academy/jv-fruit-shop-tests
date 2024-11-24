package core.basesyntax;

public class Main {
    public static void main(String[] args) {
        FruitDB fruitDB = new FruitDB();
        DefaultDataOperationStrategy operationsStrategy = new DefaultDataOperationStrategy(fruitDB);

        FileReader fileReader = new CsvFileReader();
        DataConverter dataConverter = new DataConverter();
        DataProcessor dataProcessor = new DataProcessor(fruitDB, operationsStrategy);
        ReportGenerator reportGenerator = new ReportGenerator(fruitDB);
        FileWriter fileWriter = new FileWriter();

        FruitShop fruitShop = new FruitShop(
                fileReader, dataConverter, dataProcessor, reportGenerator, fileWriter
        );

        fruitShop.run("input.csv", "output.csv");
    }
}
