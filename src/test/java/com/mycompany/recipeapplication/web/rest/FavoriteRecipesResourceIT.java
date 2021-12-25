package com.mycompany.recipeapplication.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.recipeapplication.IntegrationTest;
import com.mycompany.recipeapplication.domain.FavoriteRecipes;
import com.mycompany.recipeapplication.repository.FavoriteRecipesRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FavoriteRecipesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FavoriteRecipesResourceIT {

    private static final String DEFAULT_FAVORITELINKS = "AAAAAAAAAA";
    private static final String UPDATED_FAVORITELINKS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/favorite-recipes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FavoriteRecipesRepository favoriteRecipesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFavoriteRecipesMockMvc;

    private FavoriteRecipes favoriteRecipes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FavoriteRecipes createEntity(EntityManager em) {
        FavoriteRecipes favoriteRecipes = new FavoriteRecipes().favoritelinks(DEFAULT_FAVORITELINKS);
        return favoriteRecipes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FavoriteRecipes createUpdatedEntity(EntityManager em) {
        FavoriteRecipes favoriteRecipes = new FavoriteRecipes().favoritelinks(UPDATED_FAVORITELINKS);
        return favoriteRecipes;
    }

    @BeforeEach
    public void initTest() {
        favoriteRecipes = createEntity(em);
    }

    @Test
    @Transactional
    void createFavoriteRecipes() throws Exception {
        int databaseSizeBeforeCreate = favoriteRecipesRepository.findAll().size();
        // Create the FavoriteRecipes
        restFavoriteRecipesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(favoriteRecipes))
            )
            .andExpect(status().isCreated());

        // Validate the FavoriteRecipes in the database
        List<FavoriteRecipes> favoriteRecipesList = favoriteRecipesRepository.findAll();
        assertThat(favoriteRecipesList).hasSize(databaseSizeBeforeCreate + 1);
        FavoriteRecipes testFavoriteRecipes = favoriteRecipesList.get(favoriteRecipesList.size() - 1);
        assertThat(testFavoriteRecipes.getFavoritelinks()).isEqualTo(DEFAULT_FAVORITELINKS);
    }

    @Test
    @Transactional
    void createFavoriteRecipesWithExistingId() throws Exception {
        // Create the FavoriteRecipes with an existing ID
        favoriteRecipes.setId(1L);

        int databaseSizeBeforeCreate = favoriteRecipesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFavoriteRecipesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(favoriteRecipes))
            )
            .andExpect(status().isBadRequest());

        // Validate the FavoriteRecipes in the database
        List<FavoriteRecipes> favoriteRecipesList = favoriteRecipesRepository.findAll();
        assertThat(favoriteRecipesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFavoriteRecipes() throws Exception {
        // Initialize the database
        favoriteRecipesRepository.saveAndFlush(favoriteRecipes);

        // Get all the favoriteRecipesList
        restFavoriteRecipesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favoriteRecipes.getId().intValue())))
            .andExpect(jsonPath("$.[*].favoritelinks").value(hasItem(DEFAULT_FAVORITELINKS)));
    }

    @Test
    @Transactional
    void getFavoriteRecipes() throws Exception {
        // Initialize the database
        favoriteRecipesRepository.saveAndFlush(favoriteRecipes);

        // Get the favoriteRecipes
        restFavoriteRecipesMockMvc
            .perform(get(ENTITY_API_URL_ID, favoriteRecipes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(favoriteRecipes.getId().intValue()))
            .andExpect(jsonPath("$.favoritelinks").value(DEFAULT_FAVORITELINKS));
    }

    @Test
    @Transactional
    void getNonExistingFavoriteRecipes() throws Exception {
        // Get the favoriteRecipes
        restFavoriteRecipesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFavoriteRecipes() throws Exception {
        // Initialize the database
        favoriteRecipesRepository.saveAndFlush(favoriteRecipes);

        int databaseSizeBeforeUpdate = favoriteRecipesRepository.findAll().size();

        // Update the favoriteRecipes
        FavoriteRecipes updatedFavoriteRecipes = favoriteRecipesRepository.findById(favoriteRecipes.getId()).get();
        // Disconnect from session so that the updates on updatedFavoriteRecipes are not directly saved in db
        em.detach(updatedFavoriteRecipes);
        updatedFavoriteRecipes.favoritelinks(UPDATED_FAVORITELINKS);

        restFavoriteRecipesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFavoriteRecipes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFavoriteRecipes))
            )
            .andExpect(status().isOk());

        // Validate the FavoriteRecipes in the database
        List<FavoriteRecipes> favoriteRecipesList = favoriteRecipesRepository.findAll();
        assertThat(favoriteRecipesList).hasSize(databaseSizeBeforeUpdate);
        FavoriteRecipes testFavoriteRecipes = favoriteRecipesList.get(favoriteRecipesList.size() - 1);
        assertThat(testFavoriteRecipes.getFavoritelinks()).isEqualTo(UPDATED_FAVORITELINKS);
    }

    @Test
    @Transactional
    void putNonExistingFavoriteRecipes() throws Exception {
        int databaseSizeBeforeUpdate = favoriteRecipesRepository.findAll().size();
        favoriteRecipes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFavoriteRecipesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, favoriteRecipes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(favoriteRecipes))
            )
            .andExpect(status().isBadRequest());

        // Validate the FavoriteRecipes in the database
        List<FavoriteRecipes> favoriteRecipesList = favoriteRecipesRepository.findAll();
        assertThat(favoriteRecipesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFavoriteRecipes() throws Exception {
        int databaseSizeBeforeUpdate = favoriteRecipesRepository.findAll().size();
        favoriteRecipes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFavoriteRecipesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(favoriteRecipes))
            )
            .andExpect(status().isBadRequest());

        // Validate the FavoriteRecipes in the database
        List<FavoriteRecipes> favoriteRecipesList = favoriteRecipesRepository.findAll();
        assertThat(favoriteRecipesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFavoriteRecipes() throws Exception {
        int databaseSizeBeforeUpdate = favoriteRecipesRepository.findAll().size();
        favoriteRecipes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFavoriteRecipesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(favoriteRecipes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FavoriteRecipes in the database
        List<FavoriteRecipes> favoriteRecipesList = favoriteRecipesRepository.findAll();
        assertThat(favoriteRecipesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFavoriteRecipesWithPatch() throws Exception {
        // Initialize the database
        favoriteRecipesRepository.saveAndFlush(favoriteRecipes);

        int databaseSizeBeforeUpdate = favoriteRecipesRepository.findAll().size();

        // Update the favoriteRecipes using partial update
        FavoriteRecipes partialUpdatedFavoriteRecipes = new FavoriteRecipes();
        partialUpdatedFavoriteRecipes.setId(favoriteRecipes.getId());

        restFavoriteRecipesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFavoriteRecipes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFavoriteRecipes))
            )
            .andExpect(status().isOk());

        // Validate the FavoriteRecipes in the database
        List<FavoriteRecipes> favoriteRecipesList = favoriteRecipesRepository.findAll();
        assertThat(favoriteRecipesList).hasSize(databaseSizeBeforeUpdate);
        FavoriteRecipes testFavoriteRecipes = favoriteRecipesList.get(favoriteRecipesList.size() - 1);
        assertThat(testFavoriteRecipes.getFavoritelinks()).isEqualTo(DEFAULT_FAVORITELINKS);
    }

    @Test
    @Transactional
    void fullUpdateFavoriteRecipesWithPatch() throws Exception {
        // Initialize the database
        favoriteRecipesRepository.saveAndFlush(favoriteRecipes);

        int databaseSizeBeforeUpdate = favoriteRecipesRepository.findAll().size();

        // Update the favoriteRecipes using partial update
        FavoriteRecipes partialUpdatedFavoriteRecipes = new FavoriteRecipes();
        partialUpdatedFavoriteRecipes.setId(favoriteRecipes.getId());

        partialUpdatedFavoriteRecipes.favoritelinks(UPDATED_FAVORITELINKS);

        restFavoriteRecipesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFavoriteRecipes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFavoriteRecipes))
            )
            .andExpect(status().isOk());

        // Validate the FavoriteRecipes in the database
        List<FavoriteRecipes> favoriteRecipesList = favoriteRecipesRepository.findAll();
        assertThat(favoriteRecipesList).hasSize(databaseSizeBeforeUpdate);
        FavoriteRecipes testFavoriteRecipes = favoriteRecipesList.get(favoriteRecipesList.size() - 1);
        assertThat(testFavoriteRecipes.getFavoritelinks()).isEqualTo(UPDATED_FAVORITELINKS);
    }

    @Test
    @Transactional
    void patchNonExistingFavoriteRecipes() throws Exception {
        int databaseSizeBeforeUpdate = favoriteRecipesRepository.findAll().size();
        favoriteRecipes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFavoriteRecipesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, favoriteRecipes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(favoriteRecipes))
            )
            .andExpect(status().isBadRequest());

        // Validate the FavoriteRecipes in the database
        List<FavoriteRecipes> favoriteRecipesList = favoriteRecipesRepository.findAll();
        assertThat(favoriteRecipesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFavoriteRecipes() throws Exception {
        int databaseSizeBeforeUpdate = favoriteRecipesRepository.findAll().size();
        favoriteRecipes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFavoriteRecipesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(favoriteRecipes))
            )
            .andExpect(status().isBadRequest());

        // Validate the FavoriteRecipes in the database
        List<FavoriteRecipes> favoriteRecipesList = favoriteRecipesRepository.findAll();
        assertThat(favoriteRecipesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFavoriteRecipes() throws Exception {
        int databaseSizeBeforeUpdate = favoriteRecipesRepository.findAll().size();
        favoriteRecipes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFavoriteRecipesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(favoriteRecipes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FavoriteRecipes in the database
        List<FavoriteRecipes> favoriteRecipesList = favoriteRecipesRepository.findAll();
        assertThat(favoriteRecipesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFavoriteRecipes() throws Exception {
        // Initialize the database
        favoriteRecipesRepository.saveAndFlush(favoriteRecipes);

        int databaseSizeBeforeDelete = favoriteRecipesRepository.findAll().size();

        // Delete the favoriteRecipes
        restFavoriteRecipesMockMvc
            .perform(delete(ENTITY_API_URL_ID, favoriteRecipes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FavoriteRecipes> favoriteRecipesList = favoriteRecipesRepository.findAll();
        assertThat(favoriteRecipesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
