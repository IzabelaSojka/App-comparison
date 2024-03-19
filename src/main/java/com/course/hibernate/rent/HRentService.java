package com.course.hibernate.rent;

import com.course.hibernate.copy.HCopy;
import com.course.hibernate.copy.HCopyRepository;
import com.course.hibernate.reader.HReader;
import com.course.hibernate.reader.HReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
public class HRentService {

    @Autowired
    private HRentRepository rentRepository;

    @Autowired
    private HCopyRepository copyRepository;

    @Autowired
    private HReaderRepository readerRepository;


    @Transactional
    public void addRent(int readerId, int copyId) throws Exception {
        HCopy copy = copyRepository.findById(copyId).orElse(null);
        if (copy == null || !"dostępny".equals(copy.getStatus())) {
            throw new Exception("Egzemplarz nie jest dostępny do wypożyczenia.");
        }

        HReader reader = readerRepository.findById(readerId).orElse(null);
        if (reader == null) {
            throw new Exception("Nie znaleziono czytelnika o podanym identyfikatorze.");
        }

        copy.setStatus("wypożyczony");
        copyRepository.save(copy);

        Date currentDate = new Date(System.currentTimeMillis());

        HRent rent = new HRent(currentDate, null, copyId, reader);
        rentRepository.save(rent);
    }

    public List<HRent> getAllRents() {
        return rentRepository.findAll();
    }

    public HRent getRentById(int rentId) {
        return rentRepository.findById(rentId).orElse(null);
    }

    @Transactional
    public void updateRent(int copyId) throws Exception {
        HRent rent = rentRepository.findFirstByCopyIdAndDateReturnIsNull(copyId);
        if (rent == null) {
            throw new Exception("Egzemplarz nie jest aktualnie wypożyczony.");
        }

        rent.setDateReturn(new Date(System.currentTimeMillis()));
        rentRepository.save(rent);

        HCopy copy = copyRepository.findById(copyId).orElse(null);
        if (copy != null) {
            copy.setStatus("dostępny");
            copyRepository.save(copy);
        }
    }

    public void deleteRent(int rentId) {
        rentRepository.deleteById(rentId);
    }

    public List<HRent> getRentsForReader(int readerId) {
        return rentRepository.findByReaderId(readerId);
    }
}
