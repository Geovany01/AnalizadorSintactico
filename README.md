# AnalizadorSintactico
Analizador sintáctico AFN - AFD con java

Ejemplos de cómo ingresar AFD y AFN
Ejemplos AFD:
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





