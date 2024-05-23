package lt.viko.eif.kostenko.rest.controller;

import lt.viko.eif.kostenko.rest.model.Client;
import lt.viko.eif.kostenko.rest.repository.ClientRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ClientController {
    private final ClientRepository repository;

    ClientController(ClientRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/clients")
    CollectionModel<EntityModel<Client>> all() {
        List<EntityModel<Client>> clients = repository.findAll().stream()
                .map(client -> EntityModel.of(client,
                        linkTo(methodOn(ClientController.class).one(client.getId())).withSelfRel(),
                        linkTo(methodOn(ClientController.class).all()).withRel("clients")))
                .collect(Collectors.toList());

        return CollectionModel.of(clients, linkTo(methodOn(ClientController.class).all()).withSelfRel());
    }

    @PostMapping("/client")
    Client newClient(@RequestBody Client newClient) {
        return repository.save(newClient);
    }

    @GetMapping("/clients/{id}")
    EntityModel<Client> one(@PathVariable Integer id) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found: " + id));

        return EntityModel.of(client,
                linkTo(methodOn(ClientController.class).one(id)).withSelfRel(),
                linkTo(methodOn(ClientController.class).all()).withRel("clients"));
    }

    @PutMapping("/clients/{id}")
    Client replaceClient(@RequestBody Client newClient, @PathVariable Integer id) {
        return repository.findById(id)
                .map(client -> {
                    client.setName(newClient.getName());
                    client.setLastName(newClient.getLastName());
                    return repository.save(client);
                })
                .orElseGet(() -> {
                    newClient.setId(id);
                    return repository.save(newClient);
                });
    }

    @DeleteMapping("/clients/{id}")
    void deleteClient(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
