package com.course;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication

public class Main {

        @PersistenceContext
        private EntityManager entityManager;

        public static void main(String[] args) {
            ApplicationContext context = SpringApplication.run(Main.class, args);

            // Pobierz Main bean z kontekstu aplikacji
            Main main = context.getBean(Main.class);

            // Sprawdź połączenie z bazą danych przez Hibernate
            main.testHibernateConnection();
        }

        @Transactional
        public void testHibernateConnection() {
            try {
                // Wykonaj proste zapytanie do bazy danych
                entityManager.createQuery("SELECT 1 ").getSingleResult();
                System.out.println("Połączenie z bazą danych działa poprawnie przez Hibernate.");
            } catch (Exception e) {
                System.err.println("Błąd podczas próby połączenia z bazą danych przez Hibernate: " + e.getMessage());
            }
        }
}
