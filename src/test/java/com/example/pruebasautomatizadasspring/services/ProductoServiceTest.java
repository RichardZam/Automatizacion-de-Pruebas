package com.example.pruebasautomatizadasspring.services;

import com.example.pruebasautomatizadasspring.models.Producto;
import com.example.pruebasautomatizadasspring.repositories.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void listarProductos() {
        // Given
        Producto producto1 = new Producto("1", "Producto 1", 100.0, 10);
        Producto producto2 = new Producto("2", "Producto 2", 200.0, 20);

        when(productoRepository.findAll()).thenReturn(Flux.just(producto1, producto2));

        // When
        Flux<Producto> resultado = productoService.findAll();

        // Then
        StepVerifier.create(resultado)
                .expectNext(producto1)
                .expectNext(producto2)
                .verifyComplete();

        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void obtenerProductoPorId_Existe() {
        // Given
        String id = "1";
        Producto producto = new Producto(id, "Producto 1", 100.0, 10);

        when(productoRepository.findById(id)).thenReturn(Mono.just(producto));

        // When
        Mono<Producto> resultado = productoService.findById(id);

        // Then
        StepVerifier.create(resultado)
                .expectNext(producto)
                .verifyComplete();

        verify(productoRepository, times(1)).findById(id);
    }

    @Test
    void obtenerProductoPorId_NoExiste() {
        // Given
        String id = "999"; // ID inexistente

        when(productoRepository.findById(id)).thenReturn(Mono.empty());

        // When
        Mono<Producto> resultado = productoService.findById(id);

        // Then
        StepVerifier.create(resultado)
                .expectNextCount(0) // No devuelve ningún producto
                .verifyComplete();

        verify(productoRepository, times(1)).findById(id);
    }

    @Test
    void crearProducto() {
        // Given
        Producto producto = new Producto(null, "Nuevo Producto", 150.0, 15);
        Producto productoGuardado = new Producto("1", "Nuevo Producto", 150.0, 15);

        when(productoRepository.save(producto)).thenReturn(Mono.just(productoGuardado));

        // When
        Mono<Producto> resultado = productoService.save(producto);

        // Then
        StepVerifier.create(resultado)
                .expectNext(productoGuardado)
                .verifyComplete();

        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void eliminarProducto() {
        // Given
        String id = "1";

        when(productoRepository.deleteById(id)).thenReturn(Mono.empty());

        // When
        Mono<Void> resultado = productoService.deleteById(id);

        // Then
        StepVerifier.create(resultado)
                .verifyComplete();

        verify(productoRepository, times(1)).deleteById(id);
    }
}
