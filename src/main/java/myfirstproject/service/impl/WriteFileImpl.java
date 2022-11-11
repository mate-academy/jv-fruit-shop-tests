package myfirstproject.service.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import myfirstproject.service.WriteFile;

public class WriteFileImpl implements WriteFile {
    @Override
    public void writeToFile(String path, String content) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            bufferedWriter.write(content);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file. " + path, e);
        }
    }
}
