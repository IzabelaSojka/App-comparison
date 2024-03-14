package com.course.Copy;

import java.util.List;

public interface CopyRepository {
    void addCopy(Copy copy);
    List<Copy> getAllCopies();
    Copy getCopyById(int id);
    void updateCopy(Copy copy);
    void deleteCopy(int id);
}