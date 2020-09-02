package com.roca.api.repository.roca;

import com.roca.api.model.Roca;
import com.roca.api.repository.filter.RocaFilter;
import org.springframework.data.domain.PageImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class RocaRepositoryImpl implements RocaRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Roca> filtrar(RocaFilter rocaFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Roca> criteria = builder.createQuery(Roca.class);
        Root<Roca> root = criteria.from(Roca.class);

        Predicate[] predicates = criarRestricoes(rocaFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Roca> query = manager.createQuery(criteria);
        return query.getResultList();
    }

    private Predicate[] criarRestricoes(RocaFilter rocaFilter, CriteriaBuilder builder, Root<Roca> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (rocaFilter.getStatus() != null) {
            switch (rocaFilter.getStatus()) {
                case "ABERTO":
                    predicates.add(builder.isFalse(root.get("fechado")));
                    break;
                case "FECHADO":
                    predicates.add(builder.isTrue(root.get("fechado")));
                    break;
            }
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
