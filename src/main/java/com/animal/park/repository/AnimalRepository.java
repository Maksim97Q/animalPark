package com.animal.park.repository;

import com.animal.park.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> {
    @Query("select a from Animal a where a.nickname = ?1")
    Animal findByNickname(String nickname);
}
