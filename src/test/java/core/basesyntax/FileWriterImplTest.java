package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.FileWriterImpl;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String TEST_FILE = "src/test/resources/testOutput.csv";

    @Test
    void write_validData_shouldCreateFile() throws Exception {
        // Przygotowanie danych
        FileWriterImpl fileWriter = new FileWriterImpl();
        List<String> data = List.of("fruit,quantity", "apple,10", "banana,20");

        // Sprawdzanie, czy katalogi istnieją, jeśli nie, tworzymy je
        Path filePath = Path.of(TEST_FILE);
        Path parentDir = filePath.getParent();
        if (!Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }

        // Zapisanie danych do pliku
        fileWriter.write(data, TEST_FILE);

        // Sprawdzanie, czy plik został utworzony
        assertTrue(Files.exists(filePath), "Plik nie został utworzony.");
    }
}
