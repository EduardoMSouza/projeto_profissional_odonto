package consultorio.controller;

import consultorio.dto.request.paciente.PacienteRequest;
import consultorio.dto.response.paciente.PacienteResponse;
import consultorio.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PacienteResponse> criar(@Valid @RequestBody PacienteRequest request) {
        PacienteResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponse> atualizar(
            @PathVariable String id,
            @Valid @RequestBody PacienteRequest request) {
        PacienteResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> buscarPorId(@PathVariable String id) {
        PacienteResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponse>> listarTodos() {
        List<PacienteResponse> response = service.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/prontuario/{prontuario}")
    public ResponseEntity<PacienteResponse> buscarPorProntuario(@PathVariable String prontuario) {
        PacienteResponse response = service.buscarPorProntuario(prontuario);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<PacienteResponse> buscarPorCpf(@PathVariable String cpf) {
        PacienteResponse response = service.buscarPorCpf(cpf);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<PacienteResponse>> buscarPorNome(@RequestParam String nome) {
        List<PacienteResponse> response = service.buscarPorNome(nome);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/telefone/{telefone}")
    public ResponseEntity<List<PacienteResponse>> buscarPorTelefone(@PathVariable String telefone) {
        List<PacienteResponse> response = service.buscarPorTelefone(telefone);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/convenio")
    public ResponseEntity<List<PacienteResponse>> buscarPorConvenio(@RequestParam String convenio) {
        List<PacienteResponse> response = service.buscarPorConvenio(convenio);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
