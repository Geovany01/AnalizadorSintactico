
# Analizador sintáctico AFN - AFD con java
# Ejemplos AFD:

AFD Revisado

Componentes del AFD

- Estados:  
  Q = {q0, q1, q2}
- 
- Alfabeto:  
  Σ = {0, 1}

- Estado inicial:  
  q0

- Estados finales:  
  F = {q2}

- Transiciones:
    - q0 → 0 → q1
    - q0 → 1 → q0
    - q1 → 0 → q1
    - q1 → 1 → q2
    - q2 → 0 → q1
    - q2 → 1 → q2

Cadenas aceptadas

Las cadenas que terminan en q2 son:

- 01:
    - q0 → 0 → q1 → 1 → q2 (aceptada)
- 001:
    - q0 → 0 → q1 → 0 → q1 → 1 → q2 (aceptada)
- 0001:
    - q0 → 0 → q1 → 0 → q1 → 0 → q1 → 1 → q2 (aceptada)

Cadenas no aceptadas

Las siguientes cadenas no son aceptadas por el AFD:

- 10:
    - q0 → 1 → q0 → 0 → q1 (no aceptada)
- 11:
    - q0 → 1 → q0 → 1 → q0 (no aceptada)
- 110:
    - q0 → 1 → q0 → 1 → q0 → 0 → q1 (no aceptada)
- 111:
    - q0 → 1 → q0 → 1 → q0 → 1 → q0 (no aceptada)

________________________________________________________
# Ejemplos AFN:

AFN Revisado

Ejemplo 1: AFN que acepta cadenas que terminan en "00" o "11"

Estados: {q0, q1, q2, q3, q4}
Alfabeto: {0, 1}
Estado inicial: q0
Estados finales: {q2, q4}

Transiciones:
q0 → 0 → q1
q1 → 0 → q2
q0 → 1 → q3
q3 → 1 → q4
q0 → 0 → q0 (lazo en q0 con 0)
q0 → 1 → q0 (lazo en q0 con 1)

Cómo ingresarlo en la tabla:

Origin State	Symbol	Destination State
q0	0	q1
q1	0	q2
q0	1	q3
q3	1	q4
q0	0	q0
q0	1	q0

Ejemplo 2: AFN que acepta el lenguaje "a* (b|c)"

Estados: {q0, q1, q2}
Alfabeto: {a, b, c}
Estado inicial: q0
Estados finales: {q2}

Transiciones:
q0 → a → q0
q0 → b → q1
q0 → c → q1
q1 → λ → q2 (transición vacía)

Cómo ingresarlo en la tabla:

Origin State	Symbol	Destination State
q0	a	q0
q0	b	q1
q0	c	q1
q1	λ	q2

Ejemplo 3: AFN que acepta cadenas con "ab" o "ba"
Estados: {q0, q1, q2, q3, q4}
Alfabeto: {a, b}
Estado inicial: q0
Estados finales: {q3, q4}

Transiciones:
q0 → a → q1
q1 → b → q3
q0 → b → q2
q2 → a → q4

Cómo ingresarlo en la tabla:

Origin State	Symbol	Destination State
q0	a	q1
q1	b	q3
q0	b	q2
q2	a	q4



