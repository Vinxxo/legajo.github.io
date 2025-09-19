<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Registrar Libro</title>
    <link rel="stylesheet" href="../estilos/styles.css">
    
</head>
<body>
    <div class="formulario">
        <form>
            <h2><i class="fas fa-book"></i> Registrar Libro</h2>

            <input type="text" id="titulo" name="titulo" placeholder="Título del libro" required>

            <input type="text" id="autor" name="autor" placeholder="Autor" required>

            <label for="sinopsis"></label>
            <textarea id="sinopsis" name="sinopsis" rows="4" placeholder="Escribe aquí la sinopsis..."></textarea>

            <select id="genero" name="genero" required>
                <option value="">Seleccione un género</option>
                <option value="ficcion">Ficción</option>
                <option value="fantasia">Fantasía</option>
                <option value="romance">Romance</option>
                <option value="misterio">Misterio</option>
                <option value="biografia">Biografía</option>
                <option value="autoayuda">Autoayuda</option>
            </select>


            <div class="form-grupo">
            <label for="imagen" class="label-file">Seleccionar portada</label>
            <input type="file" id="imagen" name="imagen" accept="image/*">
            </div>


            <button type="submit"><i class="fas fa-save"></i> Guardar Libro</button>
        </form>
    </div>
</body>
</html>