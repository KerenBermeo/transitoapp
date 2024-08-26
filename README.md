# Sistema de Cálculo de Multas de Tránsito

Este proyecto es un sistema software diseñado para calcular el valor a pagar por mora en las multas de tránsito en el municipio de Ginebra, Norte del Valle del Cauca. La aplicación permite la gestión de usuarios, el manejo de una base de datos con información relevante y la implementación de reglas para el cálculo de multas.

## Descripción del Proyecto

La solución software desarrollada incluye:

1. **Gestión de Usuarios**: Un sistema de inicio de sesión con control de excepciones.
2. **Base de Datos**: Creación y gestión de la base de datos `transitoBD` que contiene tablas para almacenar información de usuarios, personas y multas.
3. **Cálculo de Mora**: Implementación de una lógica para calcular el valor a pagar por mora en las multas de tránsito, con diferentes tasas según los días de retraso.
4. **Mensajes Informativos**: Notificaciones al usuario sobre el estado de las operaciones, como inserciones exitosas o fallidas y consultas vacías.

## Funcionalidades

### 1. Creación de la Base de Datos `transitoBD`
- La base de datos `transitoBD` se crea para almacenar toda la información relevante para la aplicación.

### 2. Tablas de la Base de Datos
- `tblUser`: Almacena la información de los usuarios del sistema (`userId`, `userName`, `userLogin`, `userPassword`).
- `tblPersona`: Contiene los datos personales de los infractores (`personaId`, `personaNombre`, `personaCorreo`).
- `tblMulta`: Almacena la información de las multas, incluyendo el valor de la multa, los días en mora y el valor a pagar (`multaValorMulta`, `multaDiasMora`, `multaValorPagar`).

### 3. Inicio de Sesión
- Módulo de inicio de sesión con manejo de excepciones para credenciales vacías, incorrectas o inicio válido.

### 4. Visualización de Datos
- Muestra los datos de todas las personas almacenadas en la base de datos.

### 5. Mensajes al Usuario
- Proporciona retroalimentación al usuario sobre el estado de las operaciones:
  - Dato insertado correctamente.
  - Dato no ingresado.
  - No hay datos en la tabla.

### 6. Cálculo del Valor de la Multa
- Calcula el valor de la multa basado en los días de mora:
  - Menos de 30 días: incremento del 5%.
  - 30 días exactos: incremento del 10%.
  - Más de 30 días: incremento del 15%.
- Guarda el valor calculado en la base de datos.

### 7. Script de la Base de Datos
- Incluye un script SQL para la creación de la base de datos `transitoBD` y sus tablas.