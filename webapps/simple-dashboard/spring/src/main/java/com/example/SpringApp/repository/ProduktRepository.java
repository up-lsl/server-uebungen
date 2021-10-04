package com.example.SpringApp.repository;

import com.example.SpringApp.model.Produkt;
import org.springframework.data.repository.CrudRepository;

public interface ProduktRepository extends CrudRepository<Produkt, Integer> {
}
