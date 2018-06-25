package com.example.burgerbuilder.service;

import com.example.burgerbuilder.domain.Author;

import java.util.Optional;

/**
 * Service Interface for managing Author.
 */
public interface AuthorService {

    /**
     * Save a author.
     *
     * @param author the entity to save
     * @return the persisted entity
     */
    Author save(Author author);

    /**
     * Get all the authors.
     *
     * @return the collection of entities
     */
    Iterable<Author> findAll();

    /**
     * Get the "id" author.
     *
     * @param id the id of the entity
     * @return the optional entity
     */
    Optional<Author> findOne(Long id);

    /**
     * Delete the "id" author.
     *
     * @param author the entity to delete
     */
    void delete(Author author);

}

