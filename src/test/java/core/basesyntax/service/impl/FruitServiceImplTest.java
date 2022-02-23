package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FruitService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static FruitService fruitService;

    @BeforeClass
    public static void beforeClass() {
        fruitService = new FruitServiceImpl();
    }

    @Test
    public void createReport_Ok() {
        List<String> actual;
        fruitService.createReportFile("src/test/java/resources/FileWithData.csv",
                "src/test/java/resources/report.csv");
        List<String> expected = List.of("fruit,quantity", "orange,50", "banana,245", "apple,90");
        try {
            actual = Files.readAllLines(Path.of("src/test/java/resources/report.csv"));
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file");
        }
        assertEquals(expected, actual);
        File file = new File("src/test/java/resources/report.csv");
        file.delete();
    }
}
