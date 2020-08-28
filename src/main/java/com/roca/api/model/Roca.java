package com.roca.api.model;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
public class Roca {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String descricao;

    private boolean fechado;

    @Formula("(SELECT sum(d.total) from Despesa d WHERE d.roca_id = id)")
    private Double despesa;

    @Formula("(SELECT sum(l.total) from Lucro l WHERE l.roca_id = id)")
    private Double lucro;
}
