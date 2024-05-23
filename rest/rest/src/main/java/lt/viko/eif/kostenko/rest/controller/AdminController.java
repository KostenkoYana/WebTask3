package lt.viko.eif.kostenko.rest.controller;

import lt.viko.eif.kostenko.rest.model.Admin;
import lt.viko.eif.kostenko.rest.repository.AdminRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AdminController {
    private final AdminRepository repository;

    AdminController(AdminRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/admins")
    CollectionModel<EntityModel<Admin>> all() {
        List<EntityModel<Admin>> admins = repository.findAll().stream()
                .map(admin -> EntityModel.of(admin,
                        linkTo(methodOn(AdminController.class).one(admin.getId())).withSelfRel(),
                        linkTo(methodOn(AdminController.class).all()).withRel("admins")))
                .collect(Collectors.toList());

        return CollectionModel.of(admins, linkTo(methodOn(AdminController.class).all()).withSelfRel());
    }

    @PostMapping("/admin")
    Admin newAdmin(@RequestBody Admin newAdmin) {
        return repository.save(newAdmin);
    }

    @GetMapping("/admins/{id}")
    EntityModel<Admin> one(@PathVariable Integer id) {
        Admin admin = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found: " + id));

        return EntityModel.of(admin,
                linkTo(methodOn(AdminController.class).one(id)).withSelfRel(),
                linkTo(methodOn(AdminController.class).all()).withRel("admins"));
    }

    @PutMapping("/admins/{id}")
    Admin replaceAdmin(@RequestBody Admin newAdmin, @PathVariable Integer id) {
        return repository.findById(id)
                .map(admin -> {
                    admin.setName(newAdmin.getName());
                    admin.setLastName(newAdmin.getLastName());
                    admin.setExpirence(newAdmin.getExpirence());
                    return repository.save(admin);
                })
                .orElseGet(() -> {
                    newAdmin.setId(id);
                    return repository.save(newAdmin);
                });
    }

    @DeleteMapping("/admins/{id}")
    void deleteAdmin(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
