package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderCsvTest {

    private final FileReaderCsv fileReader = new FileReaderCsv();

    @Test
    void readFile_existingFile_returnsContent() throws Exception {
        Path file = Files.createTempFile("test", ".csv");

        Files.write(
                file,
                List.of(
                        "type,fruit,quantity",
                        "s,banana,100"
                )
        );

        List<String> result = fileReader.readFile(file.toString());

        assertEquals(2, result.size());
        assertEquals("type,fruit,quantity", result.get(0));
        assertEquals("s,banana,100", result.get(1));

        Files.deleteIfExists(file);
    }

    @Test
    void readFile_nonExistingFile_throwsException() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> fileReader.readFile("non-existing-file.csv")
        );

        assertEquals(
                "Could not read file: non-existing-file.csv",
                exception.getMessage()
        );
    }
}
