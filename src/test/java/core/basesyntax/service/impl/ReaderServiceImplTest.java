package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        try(FileWriter writer = new FileWriter(tmpFile)) {
            writer.write("fruit,quantity\n");
            writer.write("banana,152\n");
            writer.write("apple,90\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Читаємо дані
        List<String> lines = readerService.readFromFile(tmpFile.getPath());

        // Перевірки
        Assertions.assertEquals(3, lines.size());
        Assertions.assertEquals("fruit,quantity", lines.get(0));
        Assertions.assertEquals("banana,152", lines.get(1));
        Assertions.assertEquals("apple,90", lines.get(2));

        // Видаляємо файл
        tmpFile.delete();
    }

    @Test
    void readFromNonExistentFile_NotOk() {
        String nonExistentFilePath = "non-existent-file.csv";
        Assertions.assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(nonExistentFilePath));
    }

    private File createTempFile() throws IOException {
        // Отримуємо шлях до папки resources
        ClassLoader classLoader = getClass().getClassLoader();
        URL resPath = classLoader.getResource("resources");
        if(resPath == null) {
            throw new IllegalArgumentException("Folder src/test/resources not found");
        }

        String resourcesPath = resPath.getPath();

        // Створюємо тимчасовий файл
        File tempFile = File.createTempFile("report", ".csv", new File(resourcesPath));

        // Перевіряємо, що файл створено
        if(!tempFile.exists()) {
            throw new IOException("Failed to create temp file in src/test/resources");
        }

        return tempFile;
    }
}