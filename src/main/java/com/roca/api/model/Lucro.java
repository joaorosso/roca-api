package com.roca.api.model;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.Formula;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
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
    private BigDecimal quantidade;

    @NotNull
    private BigDecimal valorUnitario;

    @Formula("(SELECT quantidade * valor_unitario from Lucro l WHERE l.id = id)")
    private BigDecimal total;
}
