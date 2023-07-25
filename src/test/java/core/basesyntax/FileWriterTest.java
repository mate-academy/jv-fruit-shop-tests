package core.basesyntax;

import core.basesyntax.service.CustomFileWriter;
import core.basesyntax.service.impl.CsvFileWriter;
import org.junit.Test;

public class FileWriterTest {
    @Test
    public void write_ValidData_Ok() {
        CustomFileWriter fileWriter = new CsvFileWriter();
        String report = "fruit,quantity\napple,5\nbanana,10\n";
        fileWriter.write(report, "src/test/resources/test_output.csv");
    }
}
