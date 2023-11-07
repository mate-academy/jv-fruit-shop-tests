package core.basesyntax.io;

import static org.junit.Assert.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

public class CsvFileWriterTest {
    private static final String INVALID_PATH = "src20/test/resources/report.csv";
    private WriterToFile writer = new CsvFileWriter();

    @Test
    void invalidFilePath_notOk() {
        List<String> report = List.of(
                "fruit,quantity",
                "b,apple,140",
                "s,banana,100"
        );
        assertThrows(RuntimeException.class, () -> writer.writeFile(report, INVALID_PATH));
    }
}
