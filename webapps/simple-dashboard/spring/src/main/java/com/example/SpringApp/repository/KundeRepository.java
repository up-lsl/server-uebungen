package com.example.SpringApp.repository;

import com.example.SpringApp.model.Kunde;
import org.springframework.data.repository.CrudRepository;

public interface KundeRepository extends CrudRepository<Kunde, Integer> {

}
