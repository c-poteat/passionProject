package com.mycompany.recipeapplication.web.rest;

import com.mycompany.recipeapplication.domain.GroceryItem;
import com.mycompany.recipeapplication.repository.GroceryItemRepository;
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
 * REST controller for managing {@link com.mycompany.recipeapplication.domain.GroceryItem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GroceryItemResource {

    private final Logger log = LoggerFactory.getLogger(GroceryItemResource.class);

    private static final String ENTITY_NAME = "groceryItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroceryItemRepository groceryItemRepository;

    public GroceryItemResource(GroceryItemRepository groceryItemRepository) {
        this.groceryItemRepository = groceryItemRepository;
    }

    /**
     * {@code POST  /grocery-items} : Create a new groceryItem.
     *
     * @param groceryItem the groceryItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groceryItem, or with status {@code 400 (Bad Request)} if the groceryItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grocery-items")
    public ResponseEntity<GroceryItem> createGroceryItem(@RequestBody GroceryItem groceryItem) throws URISyntaxException {
        log.debug("REST request to save GroceryItem : {}", groceryItem);
        if (groceryItem.getId() != null) {
            throw new BadRequestAlertException("A new groceryItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroceryItem result = groceryItemRepository.save(groceryItem);
        return ResponseEntity
            .created(new URI("/api/grocery-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grocery-items/:id} : Updates an existing groceryItem.
     *
     * @param id the id of the groceryItem to save.
     * @param groceryItem the groceryItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groceryItem,
     * or with status {@code 400 (Bad Request)} if the groceryItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groceryItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grocery-items/{id}")
    public ResponseEntity<GroceryItem> updateGroceryItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GroceryItem groceryItem
    ) throws URISyntaxException {
        log.debug("REST request to update GroceryItem : {}, {}", id, groceryItem);
        if (groceryItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groceryItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groceryItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GroceryItem result = groceryItemRepository.save(groceryItem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groceryItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /grocery-items/:id} : Partial updates given fields of an existing groceryItem, field will ignore if it is null
     *
     * @param id the id of the groceryItem to save.
     * @param groceryItem the groceryItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groceryItem,
     * or with status {@code 400 (Bad Request)} if the groceryItem is not valid,
     * or with status {@code 404 (Not Found)} if the groceryItem is not found,
     * or with status {@code 500 (Internal Server Error)} if the groceryItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/grocery-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GroceryItem> partialUpdateGroceryItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GroceryItem groceryItem
    ) throws URISyntaxException {
        log.debug("REST request to partial update GroceryItem partially : {}, {}", id, groceryItem);
        if (groceryItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groceryItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groceryItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GroceryItem> result = groceryItemRepository
            .findById(groceryItem.getId())
            .map(existingGroceryItem -> {
                if (groceryItem.getItem() != null) {
                    existingGroceryItem.setItem(groceryItem.getItem());
                }
                if (groceryItem.getCategory() != null) {
                    existingGroceryItem.setCategory(groceryItem.getCategory());
                }

                return existingGroceryItem;
            })
            .map(groceryItemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groceryItem.getId().toString())
        );
    }

    /**
     * {@code GET  /grocery-items} : get all the groceryItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groceryItems in body.
     */
    @GetMapping("/grocery-items")
    public List<GroceryItem> getAllGroceryItems() {
        log.debug("REST request to get all GroceryItems");
        return groceryItemRepository.findAll();
    }

    /**
     * {@code GET  /grocery-items/:id} : get the "id" groceryItem.
     *
     * @param id the id of the groceryItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groceryItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grocery-items/{id}")
    public ResponseEntity<GroceryItem> getGroceryItem(@PathVariable Long id) {
        log.debug("REST request to get GroceryItem : {}", id);
        Optional<GroceryItem> groceryItem = groceryItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(groceryItem);
    }

    /**
     * {@code DELETE  /grocery-items/:id} : delete the "id" groceryItem.
     *
     * @param id the id of the groceryItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grocery-items/{id}")
    public ResponseEntity<Void> deleteGroceryItem(@PathVariable Long id) {
        log.debug("REST request to delete GroceryItem : {}", id);
        groceryItemRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
