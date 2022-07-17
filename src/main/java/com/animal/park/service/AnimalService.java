package com.animal.park.service;

import com.animal.park.config.CustomUserDetailsService;
import com.animal.park.entity.Animal;
import com.animal.park.entity.User;
import com.animal.park.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnimalService {
    private AnimalRepository animalRepository;
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public void setCustomUserDetailsService(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Autowired
    public void setAnimalRepository(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Transactional
    public boolean deleteAnimal(Integer id) {
        Animal animal = findAnimalById(id);
        if (animal != null) {
            animalRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Animal findAnimalById(Integer id) {
        Optional<Animal> byId = animalRepository.findById(id);
        return byId.orElse(new Animal());
    }

    @Transactional
    public Animal saveAnimal(Animal animal) {
        Animal byNickname = animalRepository.findByNickname(animal.getNickname());
        User user = customUserDetailsService.getUser();
        if (byNickname == null) {
            animal.setUser(user);
            return animalRepository.save(animal);
        } else {
            return null;
        }
    }

    @Transactional
    public List<Animal> findByList() {
        List<Animal> all = animalRepository.findAll();
        User user = customUserDetailsService.getUser();
        return all.stream()
                .filter(c -> c.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean updateAnimal(Animal animal, Integer id) {
        Animal animalById1 = findAnimalById(id);
        if (animalById1 != null) {
            Animal animalById = findAnimalById(id);
            animalById.setGender(animal.getGender());
            animalById.setDateOfBirth(animal.getDateOfBirth());
            animalById.setView(animal.getView());
            animalById.setNickname(animal.getNickname());
            animalRepository.save(animalById);
            return true;
        }
        return false;
    }
}
