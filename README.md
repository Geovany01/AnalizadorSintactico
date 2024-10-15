
# Analizador sintáctico AFN - AFD con java
# Ejemplos AFD:

Autómata que acepta cadenas que alternan entre 'a' y 'b':

Alfabeto: a, b
Estados: q0 (inicial), q1 (final)
Transiciones: q0 a q1, q1 b q0
Cadenas válidas: ab, abab, ababab
Cadenas inválidas: aa, bb, a
Autómata que acepta solo la cadena 'ab':

Alfabeto: a, b
Estados: q0 (inicial), q1 (final)
Transiciones: q0 a q1, q1 b q0
Cadenas válidas: ab
Cadenas inválidas: a, b, aa, bb
Ejemplos AFN:
Autómata que acepta cualquier número de '0' o '1' y termina en '1':

Alfabeto: 0, 1
Estados: q0 (inicial), q1 (final)
Transiciones: q0 0 q0, q0 1 q0, q0 1 q1
Cadenas válidas: 1, 101, 001
Cadenas inválidas: 00, 0000
Autómata que acepta cadenas con al menos una '1' en cualquier parte:

Alfabeto: 0, 1
Estados: q0 (inicial), q1 (final)
Transiciones: q0 0 q0, q0 1 q1, q1 0 q1, q1 1 q1
Cadenas válidas: 1, 01, 001
Cadenas inválidas: 00, 0000


________________________________________________________
# Ejemplos AFN:

Ejemplo 1: AFN que acepta cadenas que contienen "01"

Estados: {q0, q1, q2}
Alfabeto: {0, 1}
Estado inicial: q0
Estados finales: {q2}

Transiciones:
q0 → 0 → q1
q1 → 1 → q2
q0 → 0 → q0 (lazo en q0 con 0)
q0 → 1 → q0 (lazo en q0 con 1)

Cómo ingresarlo en la tabla:

Origin State	Symbol	Destination State
q0	0	q1
q1	1	q2
q0	0	q0
q0	1	q0

Ejemplo 2: AFN que acepta cadenas que terminan en "00" o "11"

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

Ejemplo 3: AFN que acepta el lenguaje "a* (b|c)"

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

Ejemplo 4: AFN con ε-transiciones (transiciones vacías)

Estados: {q0, q1, q2, q3}
Alfabeto: {a, b}
Estado inicial: q0
Estados finales: {q3}

Transiciones:
q0 → ε → q1
q1 → a → q2
q2 → b → q3
q1 → b → q2

Cómo ingresarlo en la tabla:

Origin State	Symbol	Destination State
q0	ε	q1
q1	a	q2
q2	b	q3
q1	b	q2

Ejemplo 5: AFN que acepta cadenas con "ab" o "ba"
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



