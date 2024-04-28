package core.basesyntax.dao.reader;

import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReaderCsvTest {
    private final ReaderCsv reader = new ReaderCsvImpl();
    private final Path pathFile = Path.of("src/test/resources/database.csv");
    private final List<String> lines = List.of(
            "type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");

    @Test
    void read_Ok() {
        List<String> actual = reader.read(pathFile);
        Assertions.assertEquals(lines, actual);
    }

    @Test
    void read_Null_NotOk() {
        try {
            reader.read(null);
        } catch (RuntimeException e) {
            return;
        }
        Assertions.fail("RunTimeException should be thrown if an empty value is passed");
    }
}
