package com.animal.park.controller;

import com.animal.park.entity.Animal;
import com.animal.park.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animals")
public class AnimalController {
    private AnimalService animalService;

    @Autowired
    public void setAnimalService(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimal(@PathVariable Integer id) {
        Animal animal = animalService.findAnimalById(id);
        return new ResponseEntity<>(animal, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAnimal(@RequestBody Animal animal) {
        Animal animal1 = animalService.saveAnimal(animal);
        if (animal1 != null) {
            return new ResponseEntity<>("successfully created", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("this nickname already exists", HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnimal(@PathVariable Integer id) {
        boolean delete = animalService.deleteAnimal(id);
        return delete
                ? new ResponseEntity<>("animal successfully removed", HttpStatus.OK)
                : new ResponseEntity<>("animal not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/listMyAnimals")
    public ResponseEntity<List<Animal>> listAnimal() {
        List<Animal> byList = animalService.findByList();
        return new ResponseEntity<>(byList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAnimal(@RequestBody Animal animal, @PathVariable Integer id) {
        boolean animal1 = animalService.updateAnimal(animal, id);
        return animal1
                ? new ResponseEntity<>("successfully modified", HttpStatus.OK)
                : new ResponseEntity<>("animal not found", HttpStatus.NOT_FOUND);
    }
}
