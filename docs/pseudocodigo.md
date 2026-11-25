# Pseudocódigo

## Actividad 1 – Recursividad
```
FUNCION contarAulas(zona)
    SI zona NO tiene subzonas ENTONCES          // caso base
        RETORNAR zona.aulasPropias
    total <- zona.aulasPropias
    PARA CADA hija EN zona.subzonas             // caso recursivo
        total <- total + contarAulas(hija)
    RETORNAR total

FUNCION rutaJerarquica(zona, destino, camino)
    agregar zona.nombre a camino
    SI zona.nombre = destino ENTONCES RETORNAR VERDADERO      // base: éxito
    PARA CADA hija EN zona.subzonas
        SI rutaJerarquica(hija, destino, camino) ENTONCES RETORNAR VERDADERO
    quitar último elemento de camino                          // retroceso
    RETORNAR FALSO                                            // base: sin salida
```

## Actividad 2 – Merge Sort
```
FUNCION mergeSort(lista, criterio)
    SI tamaño(lista) <= 1 ENTONCES RETORNAR lista             // caso base
    medio <- tamaño(lista) / 2
    izq <- mergeSort(lista[0 .. medio-1], criterio)           // divide + vence
    der <- mergeSort(lista[medio .. fin], criterio)
    RETORNAR mezclar(izq, der, criterio)                      // combina

FUNCION mezclar(a, b, criterio)
    i <- 0 ; j <- 0 ; res <- lista vacía
    MIENTRAS i < |a| Y j < |b|
        SI criterio(a[i], b[j]) <= 0 ENTONCES agregar a[i++] a res
        SINO agregar b[j++] a res
    agregar el resto de a y el resto de b
    RETORNAR res
```

## Actividad 3 – Búsquedas
```
FUNCION DFS(actual, destino, visitados, camino)
    marcar actual como visitado ; agregar actual a camino
    SI actual = destino ENTONCES RETORNAR VERDADERO
    PARA CADA vecino de actual NO visitado
        SI DFS(vecino, destino, visitados, camino) ENTONCES RETORNAR VERDADERO
    quitar actual de camino ; RETORNAR FALSO

FUNCION BFS(origen, esMeta)
    cola <- [origen] ; visitados <- {origen}
    MIENTRAS cola NO vacía
        actual <- sacar de cola
        SI esMeta(actual) ENTONCES RETORNAR reconstruir(padre, actual)
        PARA CADA vecino NO visitado: marcar, padre[vecino] <- actual, encolar

FUNCION AEstrella(origen, destino)
    g[origen] <- 0 ; abiertos <- cola de prioridad con (origen, h(origen))
    MIENTRAS abiertos NO vacía
        actual <- extraer el de menor f = g + h
        SI actual ya está en cerrados ENTONCES continuar
        SI actual = destino ENTONCES RETORNAR reconstruir(padre, actual)
        agregar actual a cerrados
        PARA CADA vecino con peso w
            nuevoG <- g[actual] + w
            SI nuevoG < g[vecino] ENTONCES
                g[vecino] <- nuevoG ; padre[vecino] <- actual
                insertar (vecino, nuevoG + h(vecino)) en abiertos
```
`h(n)` = distancia en línea recta de `n` al destino (admisible, porque ningún camino real mide menos que la recta).
