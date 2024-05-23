package lt.viko.eif.kostenko.rest.repository;

import lt.viko.eif.kostenko.rest.model.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactsRepository extends JpaRepository<Contacts, Integer> {
}
