package consultorio.controller;

import consultorio.dto.request.exame_dental.ExameDentalRequest;
import consultorio.dto.response.exame_dental.ExameDentalResponse;
import consultorio.service.ExameDentalService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/exames-dentais")
public class ExameDentalController {

    private final ExameDentalService service;

    public ExameDentalController(ExameDentalService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ExameDentalResponse> criar(@Valid @RequestBody ExameDentalRequest request) {
        ExameDentalResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExameDentalResponse> atualizar(
            @PathVariable String id,
            @Valid @RequestBody ExameDentalRequest request) {
        ExameDentalResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExameDentalResponse> buscarPorId(@PathVariable String id) {
        ExameDentalResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ExameDentalResponse>> listarTodos() {
        List<ExameDentalResponse> response = service.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ExameDentalResponse>> listarPorPaciente(@PathVariable Long pacienteId) {
        List<ExameDentalResponse> response = service.listarPorPaciente(pacienteId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}/ordenado")
    public ResponseEntity<List<ExameDentalResponse>> listarPorPacienteOrdenado(@PathVariable Long pacienteId) {
        List<ExameDentalResponse> response = service.listarPorPacienteOrdenado(pacienteId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<ExameDentalResponse>> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<ExameDentalResponse> response = service.listarPorPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}/periodo")
    public ResponseEntity<List<ExameDentalResponse>> listarPorPacienteEPeriodo(
            @PathVariable Long pacienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<ExameDentalResponse> response = service.listarPorPacienteEPeriodo(pacienteId, dataInicio, dataFim);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ExameDentalResponse>> buscarPorNomePaciente(@RequestParam String nome) {
        List<ExameDentalResponse> response = service.buscarPorNomePaciente(nome);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}/contar")
    public ResponseEntity<Long> contarPorPaciente(@PathVariable Long pacienteId) {
        Long count = service.contarPorPaciente(pacienteId);
        return ResponseEntity.ok(count);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}