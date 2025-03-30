package com.example.pruebasautomatizadasspring.controllers;

import com.example.pruebasautomatizadasspring.models.Producto;
import com.example.pruebasautomatizadasspring.repositories.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ProductoIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ProductoRepository productoRepository;

    @BeforeEach
    void setUp() {
        // Limpia la BD antes de cada prueba
        // productoRepository.deleteAll().block();
    }

    @Test
    void testCrearProducto() {
        Producto nuevoProducto = new Producto(null, "Producto de prueba", 99.99, 5);

        webTestClient.post().uri("/api/productos")
                .body(Mono.just(nuevoProducto), Producto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Producto.class)
                .value(producto -> {
                    assertThat(producto).isNotNull();
                    assertThat(producto.getId()).isNotNull();
                    assertThat(producto.getNombre()).isEqualTo("Producto de prueba");
                });
    }

    @Test
    void testObtenerProductoPorId() {
        Producto producto = new Producto(null, "Producto existente", 49.99, 10);
        Producto productoGuardado = productoRepository.save(producto).block();

        assertThat(productoGuardado).isNotNull();
        assertThat(productoGuardado.getId()).isNotNull();

        // ✅ Verifica si hay datos antes de consultar
        System.out.println("Productos en BD antes de consulta: " + productoRepository.findAll().collectList().block());

        webTestClient.get().uri("/api/productos/{id}", productoGuardado.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Producto.class)
                .value(p -> {
                    assertThat(p).isNotNull();
                    assertThat(p.getId()).isEqualTo(productoGuardado.getId());
                });
    }

    @Test
    void testEliminarProducto() {
        Producto producto = new Producto(null, "Producto a eliminar", 30.0, 2);
        Producto productoGuardado = productoRepository.save(producto).block();

        assertThat(productoGuardado).isNotNull();
        assertThat(productoGuardado.getId()).isNotNull();

        // Eliminar producto
        webTestClient.delete().uri("/api/productos/{id}", productoGuardado.getId())
                .exchange()
                .expectStatus().isNoContent();

        // Verificar que ya no existe
        webTestClient.get().uri("/api/productos/{id}", productoGuardado.getId())
                .exchange()
                .expectStatus().isNotFound();
    }
}
