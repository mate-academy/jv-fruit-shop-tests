package core.basesyntax.model;

import static junit.framework.TestCase.assertEquals;

import core.basesyntax.dao.CsvFileReader;
import core.basesyntax.dao.CsvFileReaderImpl;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FruitTransactionParserTest {
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
    private final CsvFileReader fileReader = new CsvFileReaderImpl();

    @Test
    void parseFruitTransaction_Ok() throws IOException {
        try (BufferedWriter writeDatabase =
                     new BufferedWriter(new FileWriter(FILE_PATH_FOR_DATABASE))) {
            writeDatabase.write(INFO_FOR_DATABASE);
        }

        List<String> expected = new ArrayList<>();
        expected.add(HEADER);
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        StringBuilder expectedTextBuilder = new StringBuilder();
        for (int i = 0; i < expected.size(); i++) {
            expectedTextBuilder.append(expected.get(i));
        }
        List<String> actual = fileReader.read(FILE_PATH_FOR_DATABASE);
        StringBuilder actualTextBuilder = new StringBuilder();
        for (int i = 0; i < actual.size(); i++) {
            actualTextBuilder.append(actual.get(i));
        }
        assertEquals(expectedTextBuilder.toString(), actualTextBuilder.toString());
    }
}
