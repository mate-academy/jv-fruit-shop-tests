package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReaderServiceImplTest {
    private ReaderService readerService;

    @BeforeEach
    void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_OK() {
        // Створюємо тимчасовий файл
        File tmpFile = null;
        try {
            tmpFile = createTempFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't create file", e);
        }

        // Записуємо дані у файл
        try (FileWriter writer = new FileWriter(tmpFile)) {
            writer.write("fruit,quantity\n");
            writer.write("banana,152\n");
            writer.write("apple,90\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Читаємо дані
        List<String> lines = readerService.readFromFileReport(tmpFile.getPath());

        // Перевірки
        Assertions.assertEquals(3, lines.size());
        Assertions.assertEquals("fruit,quantity", lines.get(0));
        Assertions.assertEquals("banana,152", lines.get(1));
        Assertions.assertEquals("apple,90", lines.get(2));

        // Видаляємо файл
        tmpFile.delete();
    }

    @Test
    void readFromInvalidFile_NotOk() {
        File tmpFile = null;
        try {
            tmpFile = createTempFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't create file", e);
        }

        try (FileWriter writer = new FileWriter(tmpFile)) {
            writer.write("test message1\n");
            writer.write("aA1!@:',&*\n");
            writer.write("test message2\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Make a final copy of the path
        final String path = tmpFile.getPath();

        // Спробуємо прочитати цей файл, очікуємо виключення
        assertThrows(RuntimeException.class,
                () -> readerService.readFromFileReport(path));

        // Видаляємо файл
        tmpFile.delete();
    }

    @Test
    void readFromNonExistentFile_NotOk() {
        String nonExistentFilePath = "non-existent-file.csv";
        assertThrows(RuntimeException.class,
                () -> readerService.readFromFileReport(nonExistentFilePath));
    }

    @Test
    void isFileEmpty() {
        File tmpFile = null;
        try {
            tmpFile = createTempFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't create file", e);
        }
        try {
            assertTrue(tmpFile.length() == 0, "File should be empty");
        } finally {
            if (tmpFile != null) {
                tmpFile.delete();
            }
        }
    }



    private File createTempFile() throws IOException {
        String resourcesPath = "src/test/java/resources";

        File tempFile = File.createTempFile("InputOrReport", ".csv", new File(resourcesPath));

        if (!tempFile.exists()) {
            throw new IOException("Failed to create temp file in src/test/resources");
        }

        return tempFile;
    }

}