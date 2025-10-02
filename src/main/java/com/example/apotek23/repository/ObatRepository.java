package com.example.apotek23.repository;

import com.example.apotek23.model.Obat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ObatRepository extends JpaRepository<Obat, Long> {
    List<Obat> findByNamaObatContainingIgnoreCase(String keyword);
}
