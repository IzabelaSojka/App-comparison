package com.course.hibernate.reader;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HReaderService {
    @Autowired
    private HReaderRepository readerRepository;

    @Autowired
    private HContactRepository contactRepository;

    @Transactional
    public void addReader(String name, String surname, String phone) {
        HReader reader = new HReader(name, surname);
        readerRepository.save(reader);

        HContact contact = new HContact();
        contact.setReader(reader);
        contact.setPhone(phone);
        contactRepository.save(contact);
    }

    public List<HReaderRents> getAllReaders() {
        return readerRepository.findAll().stream()
                .map(this::mapReaderToReaderRents)
                .collect(Collectors.toList());
    }

    public HReader getReaderById(int readerId) {
        return readerRepository.findById(readerId)
                .map(reader -> {
                    // Pobranie informacji o kontakcie czytelnika
                    HContact contact = reader.getContact();
                    // Ustawienie kontaktu w obiekcie czytelnika
                    reader.setContact(contact);
                    return reader;
                })
                .orElse(null);
    }

    @Transactional
    public void deleteReader(int readerId) {
        readerRepository.deleteById(readerId);
    }

    public List<HReaderRents> getReadersBySurname(String surname) {
        return readerRepository.findBySurname(surname).stream()
                .map(this::mapReaderToReaderRents)
                .collect(Collectors.toList());
    }

    private HReaderRents mapReaderToReaderRents(HReader reader) {
        HReaderRents readerRents = new HReaderRents();
        readerRents.setId(reader.getId());
        readerRents.setName(reader.getName());
        readerRents.setSurname(reader.getSurname());
        readerRents.setTotalBooks(reader.getRents().size());
        readerRents.setNotReturnedBooks(reader.getRents().stream().filter(rent -> rent.getDateReturn() == null).count());
        return readerRents;
    }
}
