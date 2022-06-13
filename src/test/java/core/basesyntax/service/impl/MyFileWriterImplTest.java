package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.MyFileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Test;

public class MyFileWriterImplTest {
    private final MyFileWriter myFileWriter = new MyFileWriterImpl();

    @Test
    public void writeReportToFile_valid_ok() {
        myFileWriter.writeToFile("src" + File.separator + "main" + File.separator
                        + "resources" + File.separator + "report_test.csv",
                List.of("fruit,quantity" + System.lineSeparator() + "banana,100"
                        + System.lineSeparator() + "apple,90" + System.lineSeparator()));
        List<String> expected = List.of("fruit,quantity", "banana,100", "apple,90");
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of("src" + File.separator
                    + "main" + File.separator + "resources" + File.separator + "report_test.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, actual);
    }
}
