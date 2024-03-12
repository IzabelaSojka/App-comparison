package com.course.Reader;

import java.util.List;

public interface ReaderRepository {
    void addReader(Reader reader);
    List<Reader> getAllReaders();
    Reader getReaderById(int id);
    void updateReader(Reader reader);
    void deleteReader(int id);
}