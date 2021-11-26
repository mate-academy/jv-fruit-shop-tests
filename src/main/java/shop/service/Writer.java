package shop.service;

import java.util.List;

public interface Writer {
    boolean write(List<String> dataToWrite, String fileName);
}
