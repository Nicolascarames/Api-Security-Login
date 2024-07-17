package com.universe.posts.infrastructure.controllers;

import com.universe.posts.application.services.PostService;
import com.universe.posts.domain.model.Post;
import com.universe.posts.domain.ports.out.PostDto;
import com.universe.posts.infrastructure.repository.PostRepository;
import com.universe.usersSecurity.persistence.entity.UserEntity;
import com.universe.usersSecurity.persistence.repository.UserRepository;
import com.universe.usersSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){

        Optional<Post> postOptional = postService.findById(id);

        if (postOptional.isPresent()){

            Post post = postOptional.get();

            PostDto postDto = PostDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .description(post.getDescription())
                    .dateUpdate(post.getDateUpdate())
                    .urlData(post.getUrlData())
                    .user(post.getUser())
                    .fileName(post.getFileName())
                    .build();

            return ResponseEntity.ok(postDto);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findAllByUserId/{userId}")
    public ResponseEntity<?> findAllByUserId(@PathVariable Long userId){

        UserEntity user = userRepository.findUserEntityById(userId);

        List<PostDto> postDtoList = postService.findByUser(user)
                .stream()
                .map(post -> PostDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .description(post.getDescription())
                        .dateUpdate(post.getDateUpdate())
                        .urlData(post.getUrlData())
                        .user(post.getUser())
                        .fileName(post.getFileName())
                        .build())
                .toList();
        return ResponseEntity.ok(postDtoList);
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){

        List<PostDto> postDtoList = postService.findAll()
                .stream()
                .map(post -> PostDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .description(post.getDescription())
                        .dateUpdate(post.getDateUpdate())
                        .urlData(post.getUrlData())
                        .user(post.getUser())
                        .fileName(post.getFileName())
                        .build())
                .toList();
        return ResponseEntity.ok(postDtoList);
    }

    @PostMapping("/newPost")
    public ResponseEntity<?> newPost(@RequestBody PostDto postDto) throws URISyntaxException {

        if (postDto.getTitle().isBlank() || postDto.getDescription().isBlank() || postDto.getUser_id() == null){
            return ResponseEntity.status(404).body("Missing : tittle, description or user_id");
        }

        UserEntity user = userRepository.findUserEntityById(postDto.getUser_id());

        Post nuevoPost = postService.createPost(Post.builder()
                .title(postDto.getTitle())
                .description(postDto.getDescription())
                .user(user)
                .build());

        if (nuevoPost == null){
            return ResponseEntity.status(404).body("Ha tenido un error en la subida");
        }

        return ResponseEntity.ok( "Nuevo post publicado" );
    }

    //    ESTO DEVERIA IR EN OTRA CARPETA, ESTA AQUI EN PENDIENTE
    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex >= 0) {
            return filename.substring(dotIndex);
        } else {
            return ".txt";
        }
    }

////     No funciona, puede que sea porque hay que reiniciar el proyecto para que lea las properties
//    @Value("${file.upload-dir}")
//    private String uploadDir;

    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadFile(@RequestPart("file") MultipartFile file, @RequestPart("title") String title, @RequestPart("description") String description, @RequestPart("userId") String userId) throws IOException {

        try {
            // Ruta al directorio de subida
            String uploadDir = Paths.get( "src", "main", "resources", "uploads").toAbsolutePath().normalize().toString();

            // Crear el directorio si no existe
            File dir = new File(uploadDir);
            if (!dir.exists()){
                dir.mkdirs();
            }

            // Comprobar el tamaño del archivo
            long sizeInMb = file.getSize() / (1024 * 1024);
            if (sizeInMb > 10) {
                return new ResponseEntity<>("El archivo es demasiado grande. El tamaño máximo permitido es de 10MB.", HttpStatus.CONFLICT);
            }

            // Creacion nombre random + extension del archivo
            String extension = getFileExtension(file.getOriginalFilename());
            String filenameRandom = UUID.randomUUID().toString() + extension ;

            // Guardar el archivo
            Path destinationPath = Paths.get(uploadDir, filenameRandom);
            file.transferTo(destinationPath.toFile());

            // Buscamos el user para la relacion de las tablas
            Long longId = Long.parseLong(userId);
            UserEntity user = userRepository.findUserEntityById(longId);

            postService.createPost(Post.builder()
                    .title(title)
                    .description(description)
                    .extName(extension)
                    .urlData("/uploads/" + filenameRandom)
                    .fileName(filenameRandom)
                    .user(user)
                    .build());

            return ResponseEntity.ok( "archivo subido correctamente");

        } catch (Exception e) {
            return ResponseEntity.ok( "Hubo un error al subir el archivo: " + e.getMessage() );
        }
    }

    // TODO , ARREGLAR ESTE METODO DESCARGA PERO SIN BYTES , ahora se guarda el file en una carpeta en server no en bd

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        Post post = postService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + id));
        String filename = post.getFileName();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(post.getData());
    }

//    para configurar salida del siguiente endPoint dependiendo de la extension del archivo
    private String getContentType(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

        switch (extension) {
            case "png":
                return MediaType.IMAGE_PNG_VALUE;
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG_VALUE;
            case "gif":
                return MediaType.IMAGE_GIF_VALUE;
            default:
                return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<?> serveFile(@PathVariable String filename) {
        try {
            Path file = Paths.get( "src", "main", "resources", "uploads", filename);
            Resource resource = new UrlResource(file.toUri());

            // Determinar el tipo de contenido basándose en la extensión del archivo
            String contentType = getContentType(filename);

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, contentType)
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
