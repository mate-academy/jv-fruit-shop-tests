package service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import service.FileWriter;

public class FileWriterImpl implements FileWriter {
    @Override
    public boolean write(String report, String fileName) {
        if (report == null) {
            throw new RuntimeException("The report for writing can't be null");
        }
        if (fileName == null) {
            throw new RuntimeException("The file name for writing can't be null");
        }
        File file = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file))) {
            writer.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Cannot write into the file " + file.getPath(), e);
        }
        return true;
    }
}
