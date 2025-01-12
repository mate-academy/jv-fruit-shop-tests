package core.basesyntax.filereader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileReaderImplTest {
    private final FileReaderImpl fileReader = new FileReaderImpl();

    @Test
    void read_missingFile_throwsException() { 
        //Перевіряється, що при виклику методу
        // з неіснуючим файлом викидається виняток RuntimeException
        assertThrows(RuntimeException.class, () ->
        fileReader.read("nonexistent.csv"));
    }

    @Test
    void read_emptyFile_returnsEmptyList() throws IOException {
        File tempFile = File.createTempFile("empty", ".csv");
        List<String> result = fileReader.read(tempFile.getPath());
        assertTrue(result.isEmpty());
    }

    @Test
    void read_existingFile_ok() throws IOException { //існуючий файл
        File tempFile = File.createTempFile("test", ".csv");
        //Використовуючи FileWriter, у файл записуються три рядки тексту.
        // Блок try автоматично закриває файл після запису.
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("line1\nline2\nline3");
            //Викликається метод read, який зчитує вміст файлу у список рядків.
            List<String> result = fileReader.read(tempFile.getPath());
            //Перевіряється, що список містить три рядки.
            assertEquals(3, result.size());
            //Перевіряється, що перший рядок у списку — "line1".
            assertEquals("line1", result.get(0));
        }
    }
}
