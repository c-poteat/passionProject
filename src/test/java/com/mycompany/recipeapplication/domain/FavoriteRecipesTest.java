package com.mycompany.recipeapplication.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.recipeapplication.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FavoriteRecipesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FavoriteRecipes.class);
        FavoriteRecipes favoriteRecipes1 = new FavoriteRecipes();
        favoriteRecipes1.setId(1L);
        FavoriteRecipes favoriteRecipes2 = new FavoriteRecipes();
        favoriteRecipes2.setId(favoriteRecipes1.getId());
        assertThat(favoriteRecipes1).isEqualTo(favoriteRecipes2);
        favoriteRecipes2.setId(2L);
        assertThat(favoriteRecipes1).isNotEqualTo(favoriteRecipes2);
        favoriteRecipes1.setId(null);
        assertThat(favoriteRecipes1).isNotEqualTo(favoriteRecipes2);
    }
}
