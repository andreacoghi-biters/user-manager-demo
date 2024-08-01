package it.biters.demo.user.manager.data.repositories.impl;

import it.biters.demo.user.manager.data.entities.UserEntity;
import it.biters.demo.user.manager.data.repositories.UserRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final EntityManager entityManager;

    @Override
    public List<UserEntity> searchByFirstNameAndLastName(String firstName, String lastName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = cb.createQuery(UserEntity.class);
        Root<UserEntity> user = query.from(UserEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.hasText(firstName)) {
            predicates.add(cb.equal(cb.lower(user.get("firstName")), firstName.toLowerCase()));
        }
        if (StringUtils.hasText(lastName)) {
            predicates.add(cb.equal(cb.lower(user.get("lastName")), lastName.toLowerCase()));
        }
        query.where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }
}
