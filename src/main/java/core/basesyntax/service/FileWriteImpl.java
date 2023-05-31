package core.basesyntax.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;

public class FileWriteImpl implements FileWrite {
    @Override
    public void writeDataToFile(String data, String fileTo) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(fileTo))) {
            bufferedWriter.write(data);
        } catch (IOException e) {
            throw new RuntimeException("File can not be written " + Path.of(fileTo));
        }
    }
}
