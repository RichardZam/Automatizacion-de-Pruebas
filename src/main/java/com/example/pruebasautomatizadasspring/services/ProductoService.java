package com.example.pruebasautomatizadasspring.services;

import com.example.pruebasautomatizadasspring.models.Producto;
import com.example.pruebasautomatizadasspring.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Flux<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Mono<Producto> findById(String id) {
        return productoRepository.findById(id);
    }

    public Mono<Producto> save(Producto producto) {
        return productoRepository.save(producto);
    }

    public Mono<Void> deleteById(String id) {
        return productoRepository.deleteById(id);
    }


}