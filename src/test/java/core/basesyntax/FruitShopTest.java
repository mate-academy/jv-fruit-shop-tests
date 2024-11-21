package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class FruitShopTest {

    @Test
    void testRun_EmptyInventoryReport() {
        FileReader fileReader = new CsvFileReader();
        DataConverter dataConverter = new DataConverter();
        FruitDB fruitDB = new FruitDB();
        DataProcessor dataProcessor = new DataProcessor(
                fruitDB, new DefaultDataOperationStrategy()
        );
        ReportGenerator reportGenerator = new ReportGenerator(fruitDB);
        FileWriter fileWriter = new FileWriter();
        FruitShop fruitShop = new FruitShop(
                fileReader, dataConverter, dataProcessor, reportGenerator, fileWriter
        );

        String inputFilePath = "emptyInventory.csv";
        String outputFilePath = "emptyReport.csv";

        try {
            Files.write(Path.of(inputFilePath), List.of());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        fruitShop.run(inputFilePath, outputFilePath);

        List<String> report = null;
        try {
            report = Files.readAllLines(Path.of(outputFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0, report.size());
    }
}
