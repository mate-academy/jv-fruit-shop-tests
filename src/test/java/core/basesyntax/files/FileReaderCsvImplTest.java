package core.basesyntax.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderCsvImplTest {
    private final FileReaderCsv fileReader = new FileReaderCsvImpl();

    @Test
    public void read_Ok() throws IOException {
        File testFile = File.createTempFile("testFile", ".csv");

        try (FileWriter fileWriter = new FileWriter(testFile)) {
            fileWriter.write("type,fruit,quantity\n");
            fileWriter.write("supply,banana,4\n");
            fileWriter.write("purchase,apple,8\n");
        }

        List<String> lines = fileReader.read(testFile.getAbsolutePath());

        List<String> expected = List.of(
                "type,fruit,quantity",
                "supply,banana,4",
                "purchase,apple,8"
        );

        assertEquals(expected, lines);

        testFile.delete();
    }

    @Test
    public void read_fileNotFound_NotOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileReader.read("non_existent_file.csv");
        });
        assertEquals("can't open the file", exception.getMessage());
    }
}
