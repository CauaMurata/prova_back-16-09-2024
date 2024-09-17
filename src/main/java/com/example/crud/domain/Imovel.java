package com.example.crud.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Imovel")
@Entity()
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Imovel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private LocalDate dataCompra;

    private String endereco;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comodo> comodos = new ArrayList<>();

    public void addComodo(Comodo comodo) {
        this.comodos.add(comodo);
    }

    public void removeComodo(Comodo comodo) {
        this.comodos.remove(comodo);
    }

}
