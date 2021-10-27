package br.com.deilvery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.deilvery.domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{

}
