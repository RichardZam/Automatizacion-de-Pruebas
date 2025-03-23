# Pruebas Automatizadas y Configuración del Entorno

Este proyecto es una API REST sencilla de Productos desarrollada en Spring Boot. El objetivo de la evaluación fue demostrar el conocimiento básico en la automatización de pruebas y la configuración de un entorno adecuado para pruebas automatizadas en una aplicación en desarrollo.

---

## Índice

- [Objetivo de la Evaluación](#objetivo-de-la-evaluación)
- [Tecnologías y Herramientas Utilizadas](#tecnologías-y-herramientas-utilizadas)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Implementación del Proyecto](#implementación-del-proyecto)
    - [API de Productos](#api-de-productos)
    - [Servicios y Modelo](#servicios-y-modelo)
- [Pruebas Automatizadas](#pruebas-automatizadas)
    - [Pruebas Unitarias](#pruebas-unitarias)
    - [Pruebas de Integración](#pruebas-de-integración)
- [Errores Encontrados y Soluciones Aplicadas](#errores-encontrados-y-soluciones-aplicadas)
- [Ejecución y Evidencias](#ejecución-y-evidencias)
- [Conclusiones](#conclusiones)

---

## Objetivo de la Evaluación

Pues mas que todo es mi capacidad para configurar un entorno de pruebas automatizadas, seleccionar y utilizar frameworks de pruebas adecuados (JUnit, Mockito, etc..), implementar pruebas unitarias e integración en una aplicación Spring Boot, y documentar el proceso y las soluciones implementadas.

---

## Tecnologías y Herramientas Utilizadas

- **Spring Boot 3.4.4**
- **Java 21 (Eclipse Temurin 21.0.6)**
- **Maven 3.9.9**
- **JUnit 5 y Mockito**
- **Git y GitHub** (para la gestión del código fuente y entrega)
- **IntelliJ IDEA** (IDE de desarrollo)

---

## Estructura del Proyecto

![img.png](img.png)


---

## Implementación del Proyecto

### API de Productos

Se implementaron tres endpoints:

- **GET /api/productos**: Lista todos los productos.
- **POST /api/productos**: Agrega un producto (requiere parámetros `nombre` y `precio`).
- **GET /api/productos/{id}**: Obtiene un producto por su ID.

### Servicios y Modelo

- **Modelo (`Producto.java`)**  
  Define los atributos `id`, `nombre` y `precio` y sus getters.

- **Servicio (`ProductoService.java`)**  
  Maneja la lógica en memoria para listar, agregar y obtener productos. Se usa una lista interna para simular la persistencia y un contador para asignar IDs.

---

## Pruebas Automatizadas

### Pruebas Unitarias

Se implementaron pruebas unitarias para el servicio, verificando:

- **Agregar Producto**: Se valida que el producto agregado tenga el nombre y precio correctos y se le asigne un ID.
- **Listar Productos**: Se verifica que la lista devuelta no esté vacía.
- **Obtener Producto por ID**: Se comprueba que se pueda recuperar un producto agregado mediante su ID.

*Ejemplo de prueba unitaria en `ProductoServiceTest.java`:*

```java 
@Test
void testAgregarProducto() {
    Producto producto = productoService.agregarProducto("Laptop", 1500.0);
    assertNotNull(producto.getId());
    assertEquals("Laptop", producto.getNombre());
    assertEquals(1500.0, producto.getPrecio());
    }
```
---
### Pruebas de Integración
Se implementaron pruebas de integración con MockMvc para validar el comportamiento de los endpoints REST:

- **Listar Productos**: Se simula que el servicio retorna una lista de productos y se valida el JSON devuelto.

- **Obtener Producto por ID**: Se simula la recuperación de un producto por su ID y se valida el JSON.

*Ejemplo de prueba de integración en `ProductoControllerTest.java`:*

```java
@Test
void testObtenerProductoPorId() throws Exception {
    when(productoService.obtenerProductoPorId(anyLong()))
          .thenReturn(Optional.of(new Producto(1L, "Monitor", 300.0)));

    mockMvc.perform(get("/api/productos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Monitor"));
}
```

### Errores Encontrados y Soluciones Aplicadas
> *Error al cargar el ApplicationContext:*
- **Problema**: Spring Boot intentaba configurar un DataSource, pese a que no se usa base de datos.
- **Solución**: Se agregó la exclusión de DataSourceAutoConfiguration en la clase principal:

`@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})`

> *Problema de configuración en los tests:*

- **Problema**: No se encontraba la clase de configuración principal al cargar el contexto de prueba.
- **Solución**: Se usó @Import(PruebasAutomatizadasSpringApplication.class) o se definió una configuración de prueba que inyecta los mocks necesarios.

> *Errores de Tipografía y Advertencias del IDE:*

- **Problema**: Advertencias de "typo" en nombres como "Producto", "nombre", etc.
- **Solución**: Se ignoraron estas advertencias o se agregaron al diccionario del IDE, ya que no afectan la ejecución.

> *Uso de @MockBean Deprecado:*

- **Problema**: Las anotaciones @MockBean estaban marcadas como deprecadas.
- **Solución**: Se sustituyó su uso con una configuración de prueba que inyecta el mock mediante @Import y definición de un bean de prueba.

### Ejecución y Evidencias
- **Ejecución de la API**:
```
iniciar la aplicación con mvn spring-boot:run, se puede acceder a:

http://localhost:8080/api/productos para listar productos

http://localhost:8080/api/productos/{id} para obtener un producto específico.

Se pueden usar herramientas como Postman o cURL para probar el endpoint POST y agregar productos.
```
---
- **Ejecución de las Pruebas**:
```sh
Las pruebas se ejecutan correctamente con mvn test.

Se adjuntaron capturas de pantalla (en la carpeta docs/capturas/) que muestran la salida exitosa de las pruebas.

Ejemplo de comando:

mvn clean test
Salida esperada:
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
``` 
![img_1.png](img_1.png)

### Conclusiones
Se logró configurar un entorno de pruebas automatizadas en un proyecto Spring Boot.
Se implementaron pruebas unitarias e integración, cumpliendo con los criterios de evaluación.
La documentación describe el proceso, errores encontrados y soluciones aplicadas.
El código fuente y las pruebas están disponibles en GitHub.

![img_2.png](img_2.png)
![img_3.png](img_3.png)

Richard Zambrano Diaz.
Repository : https://github.com/RichardZam/Automatizacion-de-Pruebas