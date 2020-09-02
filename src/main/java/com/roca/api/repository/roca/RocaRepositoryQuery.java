package com.roca.api.repository.roca;

import com.roca.api.model.Roca;
import com.roca.api.repository.filter.RocaFilter;

import java.util.List;

public interface RocaRepositoryQuery {

    List<Roca> filtrar(RocaFilter rocaFilter);
}
