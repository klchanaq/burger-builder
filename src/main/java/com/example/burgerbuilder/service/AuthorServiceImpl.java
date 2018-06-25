package com.example.burgerbuilder.service;

import com.example.burgerbuilder.domain.Author;
import com.example.burgerbuilder.repository.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Author.
 */
@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Save a author.
     *
     * @param author the entity to save
     * @return the persisted entity
     */
    @Override
    public Author save(Author author) {
        log.debug("Request to save Author : {}", author);
        return authorRepository.save(author);
    }

    /**
     * Get all the authors.
     *
     * @return the collection of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Iterable<Author> findAll() {
        log.debug("Request to get all Authors");
        return authorRepository.findAll();
    }

    /**
     * Get the "id" author.
     *
     * @param id the id of the entity
     * @return the optional entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Author> findOne(Long id) {
        log.debug("Request to get find an Author id: {}", id);
        return authorRepository.findById(id);
    }

    /**
     * Delete the "id" author.
     *
     * @param author
     */
    @Override
    public void delete(Author author) {
        log.debug("Request to get delete an Author : {}" ,author);
        authorRepository.delete(author);
    }
}
