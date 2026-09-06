# Chat cliente–servidor sobre sockets TCP

Aplicación de mensajería en tiempo real construida en **Java puro**, sin frameworks ni librerías externas: la comunicación se implementa directamente sobre **sockets TCP** de `java.net`, y la interfaz con **Swing**. Además del intercambio de mensajes de texto, permite **transferir archivos** entre cliente y servidor.

Proyecto del curso de **Redes de Computadoras** de la Licenciatura en Ingeniería de Software (Universidad Autónoma de Zacatecas, 2023).

---

## Qué demuestra

El objetivo del proyecto no era "hacer un chat", sino implementar a mano lo que un framework normalmente esconde:

- **El apretón de manos TCP** — el servidor abre un `ServerSocket`, se bloquea en `accept()` esperando conexión, y el cliente se conecta con `new Socket(HOST, PUERTO)`.
- **Flujos de entrada y salida bidireccionales** — cada extremo envuelve su `InputStream`/`OutputStream` en `BufferedReader`/`BufferedWriter` para el texto, y usa los streams crudos para los bytes de archivo.
- **Concurrencia** — el modelo (`Modelo extends Thread`) corre el bucle de recepción en un hilo aparte. Sin esto, el `readLine()` bloqueante congelaría la interfaz de Swing: es la razón concreta por la que un chat necesita hilos.
- **Transferencia binaria por bloques** — los archivos viajan en búferes de 8 KB, precedidos por una línea con el nombre del archivo para que el receptor sepa cómo nombrarlo.

---

## Arquitectura

Ambos extremos siguen el mismo patrón **Modelo–Vista–Controlador**, lo que hace evidente qué parte del código es red y qué parte es interfaz:

```
        CLIENTE                                     SERVIDOR
   ┌─────────────────┐                        ┌─────────────────┐
   │   GUI (Swing)   │                        │   GUI (Swing)   │
   │  vista: campo,  │                        │  vista: campo,  │
   │  botones, área  │                        │  botones, área  │
   └────────┬────────┘                        └────────┬────────┘
            │                                          │
   ┌────────┴────────┐                        ┌────────┴────────┐
   │   Controlador   │                        │   Controlador   │
   │ orquesta vista  │                        │ orquesta vista  │
   │    y modelo     │                        │    y modelo     │
   └────────┬────────┘                        └────────┬────────┘
            │                                          │
   ┌────────┴────────┐      TCP : 60002       ┌────────┴────────┐
   │  Modelo (Thread)│ ◄────────────────────► │ Modelo (Thread) │
   │  Socket, flujos │   texto + archivos     │  ServerSocket   │
   └─────────────────┘                        └─────────────────┘
```

| Clase | Responsabilidad |
|---|---|
| `Servidor.java` / `Cliente.java` | Punto de entrada: instancia GUI, Modelo y Controlador, y los enlaza entre sí |
| `Modelo.java` | Toda la lógica de red: apertura del socket, creación de flujos, envío y recepción de mensajes y archivos. Extiende `Thread` para escuchar sin bloquear la interfaz |
| `Controlador.java` | Implementa `ActionListener`; traduce los eventos de la interfaz en llamadas al modelo y viceversa |
| `GUI.java` | Ventana Swing: área de mensajes con scroll, campo de entrada, botón *Enviar* y `JFileChooser` para seleccionar archivos |

### Flujo de una sesión

1. El **servidor** abre el puerto `60002` y muestra *"Esperando conexiones..."*.
2. El **cliente** se conecta; el servidor reporta *"Cliente conectado!"*.
3. Ambos extremos crean sus flujos de E/S y arrancan su hilo de escucha.
4. A partir de ahí, cualquiera de los dos puede escribir un mensaje o enviar un archivo; el otro lo recibe de inmediato.
5. Los archivos recibidos se guardan en la carpeta `files/`.

---

## Estructura del repositorio

```
.
├── chat/
│   └── src/
│       ├── Cliente/
│       │   ├── Cliente.java       # Punto de entrada del cliente
│       │   ├── Modelo.java        # Socket, flujos, envío de mensajes y archivos
│       │   ├── Controlador.java   # Eventos de la interfaz
│       │   └── GUI.java           # Ventana Swing + JFileChooser
│       └── Servidor/
│           ├── Servidor.java      # Punto de entrada del servidor
│           ├── Modelo.java        # ServerSocket, accept(), recepción de archivos
│           ├── Controlador.java   # Eventos de la interfaz
│           └── GUI.java           # Ventana Swing
├── .gitignore
└── README.md
```

---

## Cómo ejecutarlo

Requiere únicamente el **JDK 8 o superior**. No hay dependencias externas ni gestor de build.

### 1. Compilar

```bash
cd chat
mkdir -p bin files
javac -d bin src/Servidor/*.java src/Cliente/*.java
```

### 2. Levantar el servidor

```bash
java -cp bin Servidor.Servidor
```

Se abre la ventana del servidor con el mensaje *"Esperando conexiones..."*.

### 3. Levantar el cliente

En otra terminal, desde el mismo directorio `chat/`:

```bash
java -cp bin Cliente.Cliente
```

En cuanto conecta, ambas ventanas quedan listas para intercambiar mensajes.

### Enviar un archivo

Pulsa **Seleccionar archivo**, elige el archivo en el diálogo y se transfiere al otro extremo. Los archivos recibidos aparecen en `chat/files/`, y la ruta absoluta se imprime en el área de mensajes.

> La carpeta `files/` debe existir antes de recibir un archivo; el comando de compilación de arriba ya la crea.

---

## Configuración

Los parámetros de conexión están declarados como constantes en cada `Modelo.java`:

| Parámetro | Valor por defecto | Dónde |
|---|---|---|
| Puerto | `60002` | `Cliente/Modelo.java` y `Servidor/Modelo.java` |
| Host | `localhost` | `Cliente/Modelo.java` |

Para probarlo entre dos máquinas de la misma red, cambia `HOST` en el cliente por la IP del servidor y asegúrate de que el puerto `60002` esté abierto en el firewall.

---

## Alcance y limitaciones

El proyecto es una implementación didáctica y estas decisiones fueron deliberadas para el alcance del curso:

- **Conexión uno a uno.** El servidor llama a `accept()` una sola vez, así que atiende a un único cliente. Soportar varios requeriría un `accept()` en bucle y un hilo por conexión.
- **Sin protocolo de aplicación.** Los mensajes de texto y los bytes de archivo comparten el mismo socket sin encabezados que los distingan, así que la recepción de archivos se coordina por convención y no por *framing*.
- **Sin cifrado.** El tráfico viaja en claro; el equivalente cifrado sería sustituir `Socket` por `SSLSocket`.

---

## Stack

`Java` · `java.net (Sockets TCP)` · `java.io (Streams)` · `Swing` · `Threads` · Patrón `MVC`

---

## Autores

- **Adalberto Cerrillo Vázquez**
- **Elliot Axel Noriega**

Universidad Autónoma de Zacatecas — Licenciatura en Ingeniería de Software, 2023.
