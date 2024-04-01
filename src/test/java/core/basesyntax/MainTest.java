package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.readandwriteimpl.CsvReaderImpl;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Test;

class MainTest {
    @Test
    void main_NoInput_CreatesOutputFileWithCorrectContent_Ok() {
        Main.main(new String[]{});

        File outputFile = new File("src/main/resources/output_file.csv");
        assertTrue(outputFile.exists());

        List<String> lines = new CsvReaderImpl()
                .readDataFromFile("src/main/resources/output_file.csv");

        assertEquals("fruit,quantity", String.join("\n", lines));
    }
}
