package com.roca.api.model;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
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

    private Date dataFechamento;

    @Formula("(SELECT sum(d.quantidade * d.valor_unitario) from Despesa d WHERE d.roca_id = id)")
    private BigDecimal despesa;

    @Formula("(SELECT sum(l.quantidade * l.valor_unitario) from Lucro l WHERE l.roca_id = id)")
    private BigDecimal lucro;
}
