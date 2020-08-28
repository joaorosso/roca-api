package com.roca.api.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class Lucro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID rocaId;

    private Date data;

    @NotNull
    private String descricao;

    @NotNull
    private Double quantidade;

    @NotNull
    private Double valorUnitario;

    private Double total;
}