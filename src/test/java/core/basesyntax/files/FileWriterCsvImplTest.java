package core.basesyntax.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class FileWriterCsvImplTest {
    private final FileWriterCsv fileWriter = new FileWriterCsvImpl();

    @Test
    public void write_Ok() throws IOException {
        File testFile = File.createTempFile("testFile", ".csv");
        String data = "type,fruit,quantity";

        fileWriter.write(data, testFile.getAbsolutePath());

        StringBuilder content = new StringBuilder();

        try (FileReader fr = new FileReader(testFile)) {
            int ch;
            while ((ch = fr.read()) != -1) {
                content.append((char) ch);
            }
        }

        assertEquals(data + System.lineSeparator(), content.toString());

        testFile.delete();
    }

    @Test
    public void write_FileWriteError_NotOk() throws IOException {
        File testFile = File.createTempFile("testFile", ".csv");
        testFile.setWritable(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileWriter.write("data", testFile.getAbsolutePath());
        });
        assertEquals("can't write data to file", exception.getMessage());

        testFile.setWritable(true);
        testFile.delete();
    }
}
