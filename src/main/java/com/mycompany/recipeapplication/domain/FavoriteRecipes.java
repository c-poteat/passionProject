package com.mycompany.recipeapplication.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FavoriteRecipes.
 */
@Entity
@Table(name = "favorite_recipes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FavoriteRecipes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "favoritelinks")
    private String favoritelinks;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FavoriteRecipes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFavoritelinks() {
        return this.favoritelinks;
    }

    public FavoriteRecipes favoritelinks(String favoritelinks) {
        this.setFavoritelinks(favoritelinks);
        return this;
    }

    public void setFavoritelinks(String favoritelinks) {
        this.favoritelinks = favoritelinks;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FavoriteRecipes)) {
            return false;
        }
        return id != null && id.equals(((FavoriteRecipes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FavoriteRecipes{" +
            "id=" + getId() +
            ", favoritelinks='" + getFavoritelinks() + "'" +
            "}";
    }
}
