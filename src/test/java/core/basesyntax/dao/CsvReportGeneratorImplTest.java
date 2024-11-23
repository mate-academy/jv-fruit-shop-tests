package core.basesyntax.dao;

import static core.basesyntax.storage.Storage.storageOfFruits;
import static junit.framework.TestCase.assertEquals;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class CsvReportGeneratorImplTest {
    private static final String HEADER = "fruit,quantity";
    private static final String FILE_PATH_FOR_DATABASE =
            "src/test/resourcesTest/dataTest.csv";
    private static final String INFO_FOR_DATABASE = HEADER + System.lineSeparator()
            + "b,banana,20\n"
            + "b,apple,100\n"
            + "s,banana,100\n"
            + "p,banana,13\n"
            + "r,apple,10\n"
            + "p,apple,20\n"
            + "p,banana,5\n"
            + "s,banana,50";

    private CsvReportGenerator csvReportGenerator = new CsvReportGeneratorImpl();

    @Test
    void generateReport_Ok() throws IOException {
        File directory = new File("src/test/resourcesTest");
        directory.mkdir();
        File file = new File(FILE_PATH_FOR_DATABASE);
        file.createNewFile();
        storageOfFruits.put("banana", 152);
        storageOfFruits.put("apple", 90);

        String actual = csvReportGenerator.generateReport(FILE_PATH_FOR_DATABASE);
        String expected = HEADER + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        assertEquals(expected, actual);
    }
}
