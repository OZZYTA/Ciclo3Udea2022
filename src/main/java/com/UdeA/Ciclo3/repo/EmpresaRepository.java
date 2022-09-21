package com.UdeA.Ciclo3.repo;

import com.UdeA.Ciclo3.modelos.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Anotacion que le dice a Spring que esta clase es un repositorio
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
}
