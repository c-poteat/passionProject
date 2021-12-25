package com.mycompany.recipeapplication.repository;

import com.mycompany.recipeapplication.domain.FavoriteRecipes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FavoriteRecipes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FavoriteRecipesRepository extends JpaRepository<FavoriteRecipes, Long> {}
