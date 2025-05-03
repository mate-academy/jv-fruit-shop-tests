package core.basesyntax.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FruitDaoImpl implements FruitDao {
    @Override
    public String readFromFile(File file) {
        validFile(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder stringBuilder = new StringBuilder();
            String value = reader.readLine();
            while (value != null) {
                stringBuilder.append(value);
                value = reader.readLine();
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException("Can't read a file:" + file.getName(), e);
        }
    }

    @Override
    public void writeToFile(File file, List<String> data) {
        validFile(file);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String row : data) {
                writer.write(row);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + file.getName(), e);
        }
    }

    private void validFile(File file) {
        if (file == null) {
            throw new RuntimeException("File cannot be null");
        }
        if (!file.exists()) {
            throw new RuntimeException("File does not exist: " + file.getAbsolutePath());
        }
        if (!file.isFile()) {
            throw new RuntimeException("Not a file: " + file.getAbsolutePath());
        }
        if (!file.canWrite()) {
            throw new RuntimeException("Cannot write to file: " + file.getAbsolutePath());
        }
    }
}
