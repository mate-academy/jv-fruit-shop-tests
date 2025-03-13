package core.basesyntax;

import core.basesyntax.db.FileWriter;
import core.basesyntax.db.FileWriterImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    @Test
    void write_newReport_ok() {
        String fileName = "src/main/resources/report.csv";
        FileWriter fileWriter = new FileWriterImpl();
        String data = "Some data to test";
        Assertions.assertDoesNotThrow(() -> fileWriter.write(data, fileName));
    }

    @Test
    void write_wrongDir_notOk() {
        String fileName = "src/main/resources/somedir/report.csv";
        FileWriter fileWriter = new FileWriterImpl();
        String data = "Some data to test";
        Assertions.assertThrows(RuntimeException.class, () -> fileWriter.write(data, fileName));
    }
}
