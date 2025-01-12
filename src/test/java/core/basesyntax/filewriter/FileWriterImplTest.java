package core.basesyntax.filewriter;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Files;
import static org.junit.jupiter.api.Assertions.*;

class FileWriterImplTest {
    private final FileWriter fileWriter = new FileWriterImpl();

    @Test
    void write_validFile_ok() throws Exception {
        //Створюється тимчасовий файл із розширенням .csv
        File tempFile = File.createTempFile("output", ".csv");
        //Готується тестовий рядок із даними для запису у файл
        String data = "line1\nline2\nline3";
        //Викликається метод write, який записує дані у тимчасовий файл
        fileWriter.write(data, tempFile.getPath());
        //Метод readString зчитує вміст файлу у змінну result
        String result = Files.readString(tempFile.toPath());
        assertEquals(data, result);
    }

    @Test
    void write_invalidPath_throwsException() {
        //Використовується assertThrows, щоб переконатися,
        // що виклик методу з некоректним шляхом викидає RuntimeException
        assertThrows(RuntimeException.class, () ->
                fileWriter.write("data", "invalid/path.csv"));
    }
}
