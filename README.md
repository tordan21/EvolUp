# EvolUp

> Construye mejores hábitos. Mide tu evolución.

Aplicación de escritorio en Java para gestionar objetivos personales, asociar hábitos y registrar el cumplimiento diario.

---

## ¿Qué es EvolUp?

Muchas veces sabemos qué queremos mejorar, pero no llevamos un seguimiento real.
EvolUp permite definir objetivos, dividirlos en hábitos concretos y registrar cada día si se cumplen.

A partir de esos datos, la aplicación muestra información útil como la racha, el porcentaje de cumplimiento y la evolución a lo largo del tiempo.

---

## Funcionalidades principales

- Crear y gestionar objetivos personales
- Asociar hábitos a cada objetivo
- Registrar el cumplimiento diario
- Visualizar rachas y progreso

---

## Tecnologías utilizadas

- Java 17+
- MySQL 8.0
- JDBC (mysql-connector-j 9.3.0)
- Maven
- XML / XSD

---

## Instalación y ejecución

**Requisitos previos:**
- JDK 17 o superior
- MySQL 8.0 o superior
- Maven

**Pasos:**

1. Clona el repositorio:
   ```
   git clone https://github.com/tordan21/EvolUp.git
   ```

2. Crea la base de datos ejecutando en MySQL Workbench:
   ```
   source sql/schema.sql
   source sql/data.sql
   ```

3. Abre el proyecto en IntelliJ IDEA y deja que Maven descargue las dependencias.

4. Ejecuta la clase `Main.java` desde `src/main/java/com/evolup/`.

**Credenciales por defecto:** `localhost:3306`, usuario `root`, contraseña `mysql123`.

---

## Vista previa

![Menú de la aplicación](docs/demo.png)

---

## Estructura del proyecto

```
EvolUp/
├── src/                 # Código fuente Java
├── sql/                 # Scripts de base de datos
├── xml/                 # Exportación de datos XML
└── docs/
    ├── sistemas/        # Informe técnico del entorno
    ├── empleabilidad/   # Perfil profesional y portfolio
    └── diagrams/        # Diagramas E/R y de clases
```

---

## Base de datos

### Análisis de datos

La aplicación maneja información de usuarios que crean objetivos personales, cada uno con sus propios hábitos asociados. Cada hábito tiene registros diarios que indican si se cumplió o no. Además, los usuarios pueden obtener logros y los hábitos pueden pertenecer a categorías.

Las entidades principales son:

- **USUARIO** — persona que usa la aplicación
- **OBJETIVO** — meta personal con fecha límite y estado
- **HABITO** — acción concreta ligada a un objetivo
- **REGISTRO** — apunte diario de si un hábito se cumplió
- **LOGRO** — recompensa que obtiene un usuario
- **CATEGORIA** — clasificación de los hábitos

Las relaciones entre ellas:

- Un usuario puede tener varios objetivos (1:N)
- Un objetivo puede tener varios hábitos (1:N)
- Un hábito puede tener varios registros (1:N)
- Un usuario puede tener varios logros, y un logro puede pertenecer a varios usuarios (N:M → tabla USUARIO_LOGRO)
- Un hábito puede pertenecer a varias categorías, y una categoría puede agrupar varios hábitos (N:M → tabla HABITO_CATEGORIA)

### Diagrama E/R y modelo relacional

Los diagramas están disponibles en `docs/diagrams/`:

- `Diagrama ER EvolUp.png` — diagrama entidad-relación
- `Modelo relacional EvolUp.drawio` — modelo relacional con tablas, tipos de datos y restricciones

### Creación de la base de datos

Requisitos: MySQL 8.0 o superior.

Ejecutar en orden desde MySQL Workbench u otro cliente SQL:

```sql
-- 1. Crear las tablas
source sql/schema.sql

-- 2. Insertar datos de ejemplo
source sql/data.sql
```

El script `schema.sql` incluye la instrucción `CREATE DATABASE IF NOT EXISTS evolup`, por lo que no es necesario crear la base de datos manualmente.

### Consultas

El archivo `sql/queries.sql` contiene diez consultas que cubren los casos de uso principales de la aplicación:

- Listado de usuarios con sus objetivos activos
- Hábitos de un objetivo con su frecuencia
- Registros de cumplimiento por hábito
- Usuarios con más objetivos completados
- Hábitos con mayor porcentaje de cumplimiento
- Logros obtenidos por usuario
- Hábitos sin ningún registro
- Objetivos agrupados por estado
- Categorías con más hábitos asignados
- Rachas de cumplimiento por hábito

### Integración con Java

La aplicación accede a la base de datos mediante JDBC. La clase `DBConnection` gestiona la conexión como singleton. Cada entidad tiene su propio DAO con consultas preparadas (`PreparedStatement`) que evitan inyección SQL.

El menú de consola en `Main.java` permite consultar datos y realizar operaciones de inserción, modificación y borrado directamente desde la aplicación.

---

## Exportación XML

La carpeta `xml/` contiene una exportación de los datos de la aplicación en formato XML.

- **`exportacion.xml`** — representa los usuarios registrados con sus objetivos, hábitos, registros de cumplimiento y logros obtenidos. Los datos son coherentes con los de la base de datos.
- **`exportacion.xsd`** — esquema que valida la estructura del XML. Define tipos, restricciones (patrón de email, enumeraciones de estado y frecuencia, longitudes) y cardinalidades (un usuario tiene mínimo 0 objetivos, un objetivo mínimo 1 hábito).
- **`exportacion_invalido.xml`** — ejemplo de XML con errores deliberados (email sin @, estado no permitido, frecuencia no permitida) para demostrar que el XSD los detecta.

La validación se realizó con IntelliJ IDEA. Las evidencias están en `docs/validacion.png` y `docs/validacion_error.png`.

El XML sirve como exportación de datos de la aplicación, de forma que la información queda en un formato legible fuera del programa.

---

## Arquitectura del proyecto

El código se organiza en cuatro capas con responsabilidades separadas:

- **`model/`** — clases de datos (Usuario, Objetivo, Hábito, Registro, Logro) con atributos privados, constructores y getters/setters
- **`dao/`** — acceso a la base de datos mediante PreparedStatement; cada entidad tiene su propio DAO
- **`database/`** — DBConnection gestiona la conexión como singleton
- **`util/`** — utilidades reutilizables; actualmente contiene la clase Validador
- **`Main`** — menú de consola que coordina la interacción con el usuario

Así cada parte tiene una función clara y no se mezclan responsabilidades.

---

## MPO — Ampliación de Programación

La mejora estructural incorporada para este módulo es un **sistema de validación de entradas** mediante la clase `util/Validador.java`.

Antes de insertar datos, el menú valida:
- Que los IDs introducidos sean números positivos (`Validador.idValido`)
- Que los nombres no estén vacíos (`Validador.nombreValido`)
- Que las fechas tengan el formato correcto AAAA-MM-DD (`Validador.parsearFecha`)

La lógica de validación queda en un sólo lugar y se reutiliza en varias opciones del menú, en lugar de duplicar bloques try/catch en cada método.

---

## Objetivo del proyecto

Este proyecto forma parte del primer curso de DAM y tiene como finalidad integrar bases de datos, programación y gestión de información en una aplicación coherente y funcional.
