package core.basesyntax.dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MyFileWriterImpl implements MyFileWriter {

    @Override
    public void write(String fileName, String content) {
        if (content == null) {
            throw new RuntimeException("Content is null");
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(content);
        } catch (IOException e) {
            throw new RuntimeException("Cannot write data to file: " + fileName);
        }
    }
}
