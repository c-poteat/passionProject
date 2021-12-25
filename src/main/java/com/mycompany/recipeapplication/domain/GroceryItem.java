package com.mycompany.recipeapplication.domain;

import com.mycompany.recipeapplication.domain.enumeration.Category;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GroceryItem.
 */
@Entity
@Table(name = "grocery_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroceryItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "item")
    private String item;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GroceryItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return this.item;
    }

    public GroceryItem item(String item) {
        this.setItem(item);
        return this;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Category getCategory() {
        return this.category;
    }

    public GroceryItem category(Category category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroceryItem)) {
            return false;
        }
        return id != null && id.equals(((GroceryItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroceryItem{" +
            "id=" + getId() +
            ", item='" + getItem() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
