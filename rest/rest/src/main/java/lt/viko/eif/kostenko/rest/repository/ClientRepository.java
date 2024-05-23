package lt.viko.eif.kostenko.rest.repository;

import lt.viko.eif.kostenko.rest.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
