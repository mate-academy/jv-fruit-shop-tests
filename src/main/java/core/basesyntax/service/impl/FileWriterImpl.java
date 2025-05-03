package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class FileWriterImpl implements FileWriter {
    @Override
    public void write(String content, String filePath) {
        if (content.isEmpty()) {
            throw new RuntimeException("report " + content + " is empty");
        }
        File file = new File(filePath);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(
                file))) {
            bufferedWriter.write(content);
        } catch (IOException e) {
            throw new RuntimeException("can't write to file " + filePath, e);
        }
    }
}
