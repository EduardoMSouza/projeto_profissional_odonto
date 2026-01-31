// DentistaController.java
package consultorio.controller;

import consultorio.dto.request.dentista.DentistaRequest;
import consultorio.dto.response.dentista.DentistaResponse;
import consultorio.service.DentistaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dentistas")
@RequiredArgsConstructor
public class DentistaController {

    private final DentistaService service;

    @PostMapping
    public ResponseEntity<DentistaResponse> criar(@Valid @RequestBody DentistaRequest request) {
        DentistaResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DentistaResponse> buscarPorId(@PathVariable Long id) {
        DentistaResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<DentistaResponse>> listarTodos(
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        Page<DentistaResponse> response = service.listarTodos(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ativos")
    public ResponseEntity<Page<DentistaResponse>> listarAtivos(
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        Page<DentistaResponse> response = service.listarAtivos(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/especialidade/{especialidade}")
    public ResponseEntity<Page<DentistaResponse>> listarPorEspecialidade(
            @PathVariable String especialidade,
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        Page<DentistaResponse> response = service.listarPorEspecialidade(especialidade, pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DentistaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody DentistaRequest request) {
        DentistaResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        service.desativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        service.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}