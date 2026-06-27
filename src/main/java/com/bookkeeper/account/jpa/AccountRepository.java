package com.bookkeeper.account.jpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  @EntityGraph(attributePaths = {"parent"})
  Optional<Account> findByName(String name);

  @Override
  @EntityGraph(attributePaths = {"parent"})
  List<Account> findAll();

  @Override
  @EntityGraph(attributePaths = {"parent"})
  Optional<Account> findById(Long id);
}
