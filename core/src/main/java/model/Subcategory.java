package model;

import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "subcategory")
public class Subcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "subname", nullable = false)
    private String subname;

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    public Subcategory() {
    }

    public Subcategory(String subname) {
        this.subname = Objects.requireNonNull(subname, "Subcategory name cannot be NULL!");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = Objects.requireNonNull(id, "Id cannot be NULL!");
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = Objects.requireNonNull(subname, "Subcategory name cannot be NULL!");
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
