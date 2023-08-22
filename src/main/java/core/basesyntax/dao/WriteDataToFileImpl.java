package core.basesyntax.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class WriteDataToFileImpl implements WriteDataToFile {
    @Override
    public void writeDataToFile(String data, String fileName) {
        if (data == null) {
            throw new RuntimeException("Report can't be null");
        }
        if (fileName == null) {
            throw new RuntimeException("File name can't be null");
        }

        File file = new File(fileName);
        try {
            file.createNewFile();
            Files.write(file.toPath(), data.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Can't create new file " + file, e);
        }
    }
}
