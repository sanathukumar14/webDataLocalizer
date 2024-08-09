package com.postgresql.webDataLocalizer.repo;

import com.postgresql.webDataLocalizer.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource
public interface PersonRepo extends JpaRepository<Person, UUID> {
    //what entity does it manage
    //what is the type the id field

    // Custom query to find persons by name and address
    @Query("SELECT p FROM Person p WHERE p.name = :name AND p.address = :address")
    List<Person> findByNameAndAddress(@Param("name") String name, @Param("address") String address);
}
