package core.basesyntax.shop.service.impl;

import core.basesyntax.shop.service.FileWriteService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriteServiceImpl implements FileWriteService {
    @Override
    public void write(String fileName, String info) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(info);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to the file" + fileName, e);
        }
    }
}
