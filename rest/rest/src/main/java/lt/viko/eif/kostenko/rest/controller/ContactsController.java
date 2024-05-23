package lt.viko.eif.kostenko.rest.controller;

import lt.viko.eif.kostenko.rest.model.Contacts;
import lt.viko.eif.kostenko.rest.repository.ContactsRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ContactsController {
    private final ContactsRepository repository;

    ContactsController(ContactsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/contacts")
    CollectionModel<EntityModel<Contacts>> all() {
        List<EntityModel<Contacts>> contacts = repository.findAll().stream()
                .map(contact -> EntityModel.of(contact,
                        linkTo(methodOn(ContactsController.class).one(contact.getId())).withSelfRel(),
                        linkTo(methodOn(ContactsController.class).all()).withRel("contacts")))
                .collect(Collectors.toList());

        return CollectionModel.of(contacts, linkTo(methodOn(ContactsController.class).all()).withSelfRel());
    }

    @PostMapping("/contact")
    Contacts newContact(@RequestBody Contacts newContact) {
        return repository.save(newContact);
    }

    @GetMapping("/contacts/{id}")
    EntityModel<Contacts> one(@PathVariable Integer id) {
        Contacts contact = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found: " + id));

        return EntityModel.of(contact,
                linkTo(methodOn(ContactsController.class).one(id)).withSelfRel(),
                linkTo(methodOn(ContactsController.class).all()).withRel("contacts"));
    }

    @PutMapping("/contacts/{id}")
    Contacts replaceContact(@RequestBody Contacts newContact, @PathVariable Integer id) {
        return repository.findById(id)
                .map(contact -> {
                    contact.setMail(newContact.getMail());
                    contact.setPhone(newContact.getPhone());
                    return repository.save(contact);
                })
                .orElseGet(() -> {
                    newContact.setId(id);
                    return repository.save(newContact);
                });
    }

    @DeleteMapping("/contacts/{id}")
    void deleteContact(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
