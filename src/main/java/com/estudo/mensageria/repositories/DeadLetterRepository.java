package com.estudo.mensageria.repositories;

import com.estudo.mensageria.models.DeadLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeadLetterRepository extends JpaRepository<DeadLetter,Long> {
}
