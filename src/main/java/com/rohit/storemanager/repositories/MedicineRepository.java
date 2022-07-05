package com.rohit.storemanager.repositories;

import com.rohit.storemanager.models.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    Optional<Medicine> findByName(@Param("name") String name);

    Long deleteByName(String medicineName);

    List<Medicine> findAllByName(String name);

    List<Medicine> findAllByNameLike(String name);

    List<Medicine> findAllByNameContainingIgnoreCase(String name);
}
