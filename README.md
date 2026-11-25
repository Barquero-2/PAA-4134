# PA4 – Módulo inteligente de orientación del campus (Recursividad y Grafos)

**Curso:** Algoritmo y Estructura de Datos Basados en Inteligencia Artificial (4134)


## Objetivo
Construir un módulo de orientación para un campus que **ordena** información de ubicaciones
(Merge Sort recursivo), **modela** el campus como un grafo ponderado y **analiza rutas** con
DFS, BFS y A*, justificando qué algoritmo conviene según el contexto.

## Integrantes
| N.º | Nombre completo | Rol / aporte principal |
|-----|-----------------|------------------------|
| 1 | Carlos Mathias Barquero Jara| |


## Organización del trabajo
1. Lectura conjunta del caso y reparto de actividades por integrante.
2. Cada integrante implementó su parte y la explicó al resto; se revisó en pareja.
3. Integración en `Main.java`, pruebas con el mismo campus de ejemplo y redacción del informe.
4. Grabación del video con todas las cámaras encendidas.

## Video de exposición
🎥 **YouTube (no listado):** 

## Propuesta del equipo (resumen)
- **Recursividad:** `contarAulas` y `rutaJerarquica` recorren la jerarquía Campus > Pabellón > Piso > Aula.
- **Merge Sort:** ordena el directorio alfabéticamente y por cercanía al usuario en O(n log n).
- **Grafo:** 9 nodos y 12 aristas no dirigidas, peso = metros, lista de adyacencia.
- **DFS** → verificar conectividad / explorar. **BFS** → servicio más cercano por tramos.
  **A\*** → ruta más corta en metros con heurística de línea recta.
- **Recomendación:** A* como motor principal de rutas; BFS/DFS como apoyo; Merge Sort para presentar resultados.

## Estructura del repositorio
```
.
├── README.md
├── informe/Informe_PA4.pdf       # Entrega 1
├── src/
│   ├── Main.java                 # Demostración completa
│   ├── Ubicacion.java            # Nodo del grafo
│   ├── Zona.java                 # Jerarquía del campus
│   ├── Recursividad.java         # Actividad 1
│   ├── MergeSort.java            # Actividad 2
│   ├── GrafoCampus.java          # Actividad 3: grafo
│   └── Busquedas.java            # Actividad 3: DFS, BFS, A*
└── docs/
    ├── pseudocodigo.md
    ├── diagramas.md
    ├── grafo_campus.png
    └── salida_ejecucion.txt      # Salida real del programa
```

## Instrucciones de uso
```bash
javac -d out src/*.java
java -cp out Main
```
La salida esperada está en `docs/salida_ejecucion.txt`.
