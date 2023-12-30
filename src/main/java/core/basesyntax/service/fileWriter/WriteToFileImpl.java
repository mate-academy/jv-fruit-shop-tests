package core.basesyntax.service.fileWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class WriteToFileImpl implements WriteToFile {
    @Override
    public void writeToFile(String fileName, String fruitList) {
        File file = new File(fileName);
        try {
            Files.write(file.toPath(), fruitList.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Can`t write to file fruits ", e);
        }
    }
}
