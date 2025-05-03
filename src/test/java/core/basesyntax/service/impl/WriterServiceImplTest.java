package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class WriterServiceImplTest {

    private static final String OUTPUT_FILE_PATH_ACTUAL = "src/main/resources/fileBalance.csv";
    private WriterService writerService = new WriterServiceImpl();

    @Test
    public void writeBalanceOfFruitToFile_Ok() {
        try {
            String balance = "fruit,quantity" + System.lineSeparator()
                    + "banana," + 50 + System.lineSeparator()
                    + "apple," + 35 + System.lineSeparator();
            writerService.writeBalanceOfFruitToFile(balance, OUTPUT_FILE_PATH_ACTUAL);
            File file = new File(OUTPUT_FILE_PATH_ACTUAL);
            List<String> actual = Files.readAllLines(Path.of(OUTPUT_FILE_PATH_ACTUAL));
            List<String> expected = List.of("fruit,quantity", "banana,50", "apple,35");
            Assert.assertEquals(actual, expected);
        } catch (IOException e) {
            throw new RuntimeException("Can't write balance to file ", e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void writeBalanceOfFruitToFile_EmptyLine() {
        String emptyLine = "";
        new WriterServiceImpl().writeBalanceOfFruitToFile(emptyLine, OUTPUT_FILE_PATH_ACTUAL);
    }
}
