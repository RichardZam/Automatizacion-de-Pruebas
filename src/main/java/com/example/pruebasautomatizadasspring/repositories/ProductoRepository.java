package com.example.pruebasautomatizadasspring.repositories;

import com.example.pruebasautomatizadasspring.models.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends ReactiveMongoRepository<Producto, String> {
    // Los métodos básicos como findAll(), findById(), save(), etc.
    // ya están definidos en ReactiveMongoRepository
}