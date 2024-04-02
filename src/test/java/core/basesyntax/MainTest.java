package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.readandwriteimpl.CsvReaderImpl;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainTest {
    private String pathToFile;

    @BeforeEach
    void pathToTheFile() {
        pathToFile = "src/main/resources/output_file.csv";
    }

    @Test
    public void main_NoInput_CreatesOutputFileWithCorrectContent_Ok() {
        Main.main(new String[]{});

        File outputFile = new File(pathToFile);
        assertTrue(outputFile.exists());

        List<String> lines = new CsvReaderImpl()
                .readDataFromFile(pathToFile);

        assertEquals("fruit,quantity", String.join("\n", lines));
    }
}
