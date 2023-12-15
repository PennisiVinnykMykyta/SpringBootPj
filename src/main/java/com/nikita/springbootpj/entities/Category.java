package com.nikita.springbootpj.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
public class Category implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name ="category_id")
    private Integer id;

    @Column(name = "label")
    private String label;

    @Column(name ="attribute")
    private String attribute;

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Book> carCategories;




}
