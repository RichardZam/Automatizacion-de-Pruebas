package com.example.pruebasautomatizadasspring.controllers;

import com.example.pruebasautomatizadasspring.models.Producto;
import com.example.pruebasautomatizadasspring.repositories.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureWebTestClient
class ProductoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ProductoRepository productoRepository;

    private Producto productoTest;

    @BeforeEach
    void setUp() {
        // Limpiar base de datos de prueba y crear producto de prueba
         //productoRepository.deleteAll().block();

        productoTest = new Producto(null, "Producto de Prueba", 50.0, 5);
        productoTest = productoRepository.save(productoTest).block();
    }

    @Test
    void crearProducto() {
        Producto nuevoProducto = new Producto(null, "Nuevo Producto", 75.0, 10);

        webTestClient.post()
                .uri("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(nuevoProducto), Producto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Producto.class)
                .value(producto -> {
                    assert producto.getId() != null;
                    assert producto.getNombre().equals("Nuevo Producto");
                    assert producto.getPrecio() == 75.0;
                    assert producto.getCantidad() == 10;
                });
    }

    @Test
    void obtenerProductoPorId() {
        webTestClient.get()
                .uri("/api/productos/{id}", productoTest.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Producto.class)
                .value(producto -> {
                    assert producto.getId().equals(productoTest.getId());
                    assert producto.getNombre().equals(productoTest.getNombre());
                    assert producto.getPrecio().equals(productoTest.getPrecio());
                    assert producto.getCantidad().equals(productoTest.getCantidad());
                });
    }

    @Test
    void eliminarProducto() {
        webTestClient.delete()
                .uri("/api/productos/{id}", productoTest.getId())
                .exchange()
                .expectStatus().isNoContent();

        // Verificar que el producto fue eliminado
        webTestClient.get()
                .uri("/api/productos/{id}", productoTest.getId())
                .exchange()
                .expectStatus().isNotFound();
    }
}