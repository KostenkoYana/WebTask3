package lt.viko.eif.kostenko.rest.controller;

import lt.viko.eif.kostenko.rest.model.Room;
import lt.viko.eif.kostenko.rest.repository.RoomRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RoomController {
    private final RoomRepository repository;

    RoomController(RoomRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/rooms")
    CollectionModel<EntityModel<Room>> all() {
        List<EntityModel<Room>> rooms = repository.findAll().stream()
                .map(room -> EntityModel.of(room,
                        linkTo(methodOn(RoomController.class).one(room.getId())).withSelfRel(),
                        linkTo(methodOn(RoomController.class).all()).withRel("rooms")))
                .collect(Collectors.toList());

        return CollectionModel.of(rooms, linkTo(methodOn(RoomController.class).all()).withSelfRel());
    }

    @PostMapping("/room")
    Room newRoom(@RequestBody Room newRoom) {
        return repository.save(newRoom);
    }

    @GetMapping("/rooms/{id}")
    EntityModel<Room> one(@PathVariable Integer id) {
        Room room = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found: " + id));

        return EntityModel.of(room,
                linkTo(methodOn(RoomController.class).one(id)).withSelfRel(),
                linkTo(methodOn(RoomController.class).all()).withRel("rooms"));
    }

    @PutMapping("/rooms/{id}")
    Room replaceRoom(@RequestBody Room newRoom, @PathVariable Integer id) {
        return repository.findById(id)
                .map(room -> {
                    room.setTypeOfRoom(newRoom.getTypeOfRoom());
                    room.setNumberOfRoom(newRoom.getNumberOfRoom());
                    room.setPrice(newRoom.getPrice());
                    return repository.save(room);
                })
                .orElseGet(() -> {
                    newRoom.setId(id);
                    return repository.save(newRoom);
                });
    }

    @DeleteMapping("/rooms/{id}")
    void deleteRoom(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
