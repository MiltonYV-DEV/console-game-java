# CONSOLE GAME WITH JAVA

# Comando para correr el juego

```bash
mvn exec:java -Dexec.mainClass="vyom.dunk.app.App"
```

# Comando windows
primera terminal:
```bash
mvn test
mvn exec:java "-Dexec.mainClass=vyom.dunk.app.App"
```

# Comando windows para activar venv
Segunda temrinal: entra a la carpeta py/ y ejecuta el siguiente comando
```bash
.\.venv\Scripts\Activate.ps1
```
una vez puesto ese comando te debe salir un (venv) en tu linea de comando en termital
luego corres el backend con el siguiente comando

```bash
uvicorn app.main:app --reload --port 8000
```
