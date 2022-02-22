package net.artnet.securitySeries.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "lend")
public class Lend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private Instant startOn;
    private Instant dueOn;

    @Enumerated(EnumType.ORDINAL)
    private LendStatus stat;

    @ManyToOne
    @JoinColumn(name= "book_id")
    @JsonManagedReference
    private Book book;

    @ManyToOne
    @JoinColumn(name= "memberb_id")
    @JsonManagedReference
    private Member member;

}
