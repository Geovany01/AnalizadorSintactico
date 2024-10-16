# Analizador Sintáctico AFN - AFD en Java

Este proyecto implementa un analizador sintáctico que trabaja con autómatas finitos no deterministas (AFN) y deterministas (AFD), utilizando Java. El código permite definir, validar y operar con autómatas, así como ejecutar ejemplos de aceptación de cadenas.

## Requisitos

- **Versión de JDK**: JDK 22 (asegúrate de tener esta versión instalada para ejecutar el proyecto correctamente).

## Ejemplos de AFD (Autómata Finito Determinista)

### Ejemplo 1: AFD que acepta cadenas con "ab" o "ba"
- **Estados**: {q0, q1, q2, q3, q4}
- **Alfabeto**: {a, b}
- **Estado inicial**: q0
- **Estados finales**: {q3, q4}

**Transiciones**:
- q0 → a → q1
- q1 → b → q3
- q0 → b → q2
- q2 → a → q4

### Ejemplo 2: AFD que acepta cadenas con un patrón específico
- **Estados**: Q = {q0, q1, q2}
- **Alfabeto**: Σ = {0, 1}
- **Estado inicial**: q0
- **Estados finales**: F = {q2}

**Transiciones**:
- q0 → 0 → q1
- q0 → 1 → q0
- q1 → 0 → q1
- q1 → 1 → q2
- q2 → 0 → q1
- q2 → 1 → q2

**Cadenas aceptadas**:
- 01
- 001
- 0001

**Cadenas no aceptadas**:
- 10
- 11
- 110
- 111

---

## Ejemplos de AFN (Autómata Finito No Determinista)

### Ejemplo 1: AFN que acepta cadenas que terminan en "00" o "11"
- **Estados**: {q0, q1, q2, q3, q4}
- **Alfabeto**: {0, 1}
- **Estado inicial**: q0
- **Estados finales**: {q2, q4}

**Transiciones**:
- q0 → 0 → q1
- q1 → 0 → q2
- q0 → 1 → q3
- q3 → 1 → q4
- q0 → 0 → q0
- q0 → 1 → q0

### Ejemplo 2: AFN que acepta el lenguaje "a*(b|c)"
- **Estados**: {q0, q1, q2}
- **Alfabeto**: {a, b, c}
- **Estado inicial**: q0
- **Estados finales**: {q2}

**Transiciones**:
- q0 → a → q0
- q0 → b → q1
- q0 → c → q1
- q1 → λ → q2 (transición vacía)

### Ejemplo 3: AFN que acepta cualquier combinación de 'a' y 'b' con una transición epsilon
- **Estados**: {q0, q1}
- **Alfabeto**: {a, b}
- **Estado inicial**: q0
- **Estados finales**: {q1}

**Transiciones**:
- q0 — ε → q1 (Transición epsilon)
- q1 — a → q1
- q1 — b → q1

---

## Instrucciones

1. Clonar el repositorio y abrir el proyecto en tu entorno de desarrollo preferido.
2. Asegúrate de tener JDK 22 instalado.
3. Ejecutar el código y probar los ejemplos incluidos para validar cadenas en los autómatas.

---

## Autor
https://github.com/Geovany01.
