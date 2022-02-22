package net.artnet.securitySeries.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lasName;

    @JsonBackReference
    @OneToMany(mappedBy = "author", fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Book> books;
}
