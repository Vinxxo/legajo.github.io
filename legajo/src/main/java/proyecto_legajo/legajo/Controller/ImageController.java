package proyecto_legajo.legajo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin("*")
public class ImageController {

    // Directorio donde se guardarán las imágenes
    private static final String UPLOAD_DIR = "src/main/resources/static/imgs/libros";

    @PostMapping("/imagen")
    public ResponseEntity<?> uploadImagen(@RequestParam("file") MultipartFile file) {
        try {
            // Validar que sea una imagen
            if (!file.getContentType().startsWith("image/")) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "El archivo debe ser una imagen"));
            }

            // Crear el directorio si no existe
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Generar un nombre único para el archivo
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFilename = UUID.randomUUID().toString() + extension;

            // Guardar el archivo
            Path uploadPath = Paths.get(UPLOAD_DIR, uniqueFilename);
            Files.write(uploadPath, file.getBytes());

            // Retornar la URL relativa de la imagen
            String imageUrl = "/imgs/libros/" + uniqueFilename;
            return ResponseEntity.ok(Map.of(
                "url", imageUrl,
                "filename", uniqueFilename
            ));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al subir la imagen: " + e.getMessage()));
        }
    }
}
