package com.mycompany.recipeapplication.repository;

import com.mycompany.recipeapplication.domain.GroceryItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GroceryItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {}
