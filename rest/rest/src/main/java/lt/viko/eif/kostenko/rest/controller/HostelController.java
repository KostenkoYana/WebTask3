package lt.viko.eif.kostenko.rest.controller;

import lt.viko.eif.kostenko.rest.model.Hostel;
import lt.viko.eif.kostenko.rest.repository.HostelRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class HostelController {
    private final HostelRepository repository;

    HostelController(HostelRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/hostels")
    CollectionModel<EntityModel<Hostel>> all() {
        List<EntityModel<Hostel>> hostels = repository.findAll().stream()
                .map(hostel -> EntityModel.of(hostel,
                        linkTo(methodOn(HostelController.class).one(hostel.getId())).withSelfRel(),
                        linkTo(methodOn(HostelController.class).all()).withRel("hostels")))
                .collect(Collectors.toList());

        return CollectionModel.of(hostels, linkTo(methodOn(HostelController.class).all()).withSelfRel());
    }

    @PostMapping("/hostel")
    Hostel newHostel(@RequestBody Hostel newHostel) {
        return repository.save(newHostel);
    }

    @GetMapping("/hostels/{id}")
    EntityModel<Hostel> one(@PathVariable Integer id) {
        Hostel hostel = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hostel not found: " + id));

        return EntityModel.of(hostel,
                linkTo(methodOn(HostelController.class).one(id)).withSelfRel(),
                linkTo(methodOn(HostelController.class).all()).withRel("hostels"));
    }

    @PutMapping("/hostels/{id}")
    Hostel replaceHostel(@RequestBody Hostel newHostel, @PathVariable Integer id) {
        return repository.findById(id)
                .map(hostel -> {
                    hostel.setAddress(newHostel.getAddress());
                    hostel.setName(newHostel.getName());
                    hostel.setAdmin(newHostel.getAdmin());
                    hostel.setClients(newHostel.getClients());
                    hostel.setContacts(newHostel.getContacts());
                    hostel.setRooms(newHostel.getRooms());
                    return repository.save(hostel);
                })
                .orElseGet(() -> {
                    newHostel.setId(id);
                    return repository.save(newHostel);
                });
    }

    @DeleteMapping("/hostels/{id}")
    void deleteHostel(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
