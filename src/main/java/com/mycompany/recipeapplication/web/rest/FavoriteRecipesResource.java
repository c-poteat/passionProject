package com.mycompany.recipeapplication.web.rest;

import com.mycompany.recipeapplication.domain.FavoriteRecipes;
import com.mycompany.recipeapplication.repository.FavoriteRecipesRepository;
import com.mycompany.recipeapplication.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.recipeapplication.domain.FavoriteRecipes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FavoriteRecipesResource {

    private final Logger log = LoggerFactory.getLogger(FavoriteRecipesResource.class);

    private static final String ENTITY_NAME = "favoriteRecipes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FavoriteRecipesRepository favoriteRecipesRepository;

    public FavoriteRecipesResource(FavoriteRecipesRepository favoriteRecipesRepository) {
        this.favoriteRecipesRepository = favoriteRecipesRepository;
    }

    /**
     * {@code POST  /favorite-recipes} : Create a new favoriteRecipes.
     *
     * @param favoriteRecipes the favoriteRecipes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new favoriteRecipes, or with status {@code 400 (Bad Request)} if the favoriteRecipes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/favorite-recipes")
    public ResponseEntity<FavoriteRecipes> createFavoriteRecipes(@RequestBody FavoriteRecipes favoriteRecipes) throws URISyntaxException {
        log.debug("REST request to save FavoriteRecipes : {}", favoriteRecipes);
        if (favoriteRecipes.getId() != null) {
            throw new BadRequestAlertException("A new favoriteRecipes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FavoriteRecipes result = favoriteRecipesRepository.save(favoriteRecipes);
        return ResponseEntity
            .created(new URI("/api/favorite-recipes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /favorite-recipes/:id} : Updates an existing favoriteRecipes.
     *
     * @param id the id of the favoriteRecipes to save.
     * @param favoriteRecipes the favoriteRecipes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated favoriteRecipes,
     * or with status {@code 400 (Bad Request)} if the favoriteRecipes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the favoriteRecipes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/favorite-recipes/{id}")
    public ResponseEntity<FavoriteRecipes> updateFavoriteRecipes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FavoriteRecipes favoriteRecipes
    ) throws URISyntaxException {
        log.debug("REST request to update FavoriteRecipes : {}, {}", id, favoriteRecipes);
        if (favoriteRecipes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, favoriteRecipes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!favoriteRecipesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FavoriteRecipes result = favoriteRecipesRepository.save(favoriteRecipes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, favoriteRecipes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /favorite-recipes/:id} : Partial updates given fields of an existing favoriteRecipes, field will ignore if it is null
     *
     * @param id the id of the favoriteRecipes to save.
     * @param favoriteRecipes the favoriteRecipes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated favoriteRecipes,
     * or with status {@code 400 (Bad Request)} if the favoriteRecipes is not valid,
     * or with status {@code 404 (Not Found)} if the favoriteRecipes is not found,
     * or with status {@code 500 (Internal Server Error)} if the favoriteRecipes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/favorite-recipes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FavoriteRecipes> partialUpdateFavoriteRecipes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FavoriteRecipes favoriteRecipes
    ) throws URISyntaxException {
        log.debug("REST request to partial update FavoriteRecipes partially : {}, {}", id, favoriteRecipes);
        if (favoriteRecipes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, favoriteRecipes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!favoriteRecipesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FavoriteRecipes> result = favoriteRecipesRepository
            .findById(favoriteRecipes.getId())
            .map(existingFavoriteRecipes -> {
                if (favoriteRecipes.getFavoritelinks() != null) {
                    existingFavoriteRecipes.setFavoritelinks(favoriteRecipes.getFavoritelinks());
                }

                return existingFavoriteRecipes;
            })
            .map(favoriteRecipesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, favoriteRecipes.getId().toString())
        );
    }

    /**
     * {@code GET  /favorite-recipes} : get all the favoriteRecipes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of favoriteRecipes in body.
     */
    @GetMapping("/favorite-recipes")
    public List<FavoriteRecipes> getAllFavoriteRecipes() {
        log.debug("REST request to get all FavoriteRecipes");
        return favoriteRecipesRepository.findAll();
    }

    /**
     * {@code GET  /favorite-recipes/:id} : get the "id" favoriteRecipes.
     *
     * @param id the id of the favoriteRecipes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the favoriteRecipes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/favorite-recipes/{id}")
    public ResponseEntity<FavoriteRecipes> getFavoriteRecipes(@PathVariable Long id) {
        log.debug("REST request to get FavoriteRecipes : {}", id);
        Optional<FavoriteRecipes> favoriteRecipes = favoriteRecipesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(favoriteRecipes);
    }

    /**
     * {@code DELETE  /favorite-recipes/:id} : delete the "id" favoriteRecipes.
     *
     * @param id the id of the favoriteRecipes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/favorite-recipes/{id}")
    public ResponseEntity<Void> deleteFavoriteRecipes(@PathVariable Long id) {
        log.debug("REST request to delete FavoriteRecipes : {}", id);
        favoriteRecipesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
