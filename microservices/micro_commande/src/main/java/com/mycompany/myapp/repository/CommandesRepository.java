package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Commandes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Commandes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandesRepository extends MongoRepository<Commandes, String> {}
