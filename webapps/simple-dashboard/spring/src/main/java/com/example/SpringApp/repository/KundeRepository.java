package com.example.SpringApp.repository;

import com.example.SpringApp.model.Kunde;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository der Kunden
 */

public interface KundeRepository extends CrudRepository<Kunde, Integer> {

}
