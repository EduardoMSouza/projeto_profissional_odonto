package consultorio.controller;

import consultorio.dto.request.evolcao_tratamento.EvolucaoTratamentoRequest;
import consultorio.dto.response.evolcao_tratamento.EvolucaoTratamentoResponse;
import consultorio.service.EvolucaoTratamentoService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/evolucoes-tratamento")
public class EvolucaoTratamentoController {

    private final EvolucaoTratamentoService service;

    public EvolucaoTratamentoController(EvolucaoTratamentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EvolucaoTratamentoResponse> criar(@Valid @RequestBody EvolucaoTratamentoRequest request) {
        EvolucaoTratamentoResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvolucaoTratamentoResponse> atualizar(
            @PathVariable String id,
            @Valid @RequestBody EvolucaoTratamentoRequest request) {
        EvolucaoTratamentoResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvolucaoTratamentoResponse> buscarPorId(@PathVariable String id) {
        EvolucaoTratamentoResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<EvolucaoTratamentoResponse>> listarTodos() {
        List<EvolucaoTratamentoResponse> response = service.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<EvolucaoTratamentoResponse>> listarPorPaciente(@PathVariable Long pacienteId) {
        List<EvolucaoTratamentoResponse> response = service.listarPorPaciente(pacienteId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}/ordenado")
    public ResponseEntity<List<EvolucaoTratamentoResponse>> listarPorPacienteOrdenado(@PathVariable Long pacienteId) {
        List<EvolucaoTratamentoResponse> response = service.listarPorPacienteOrdenado(pacienteId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<EvolucaoTratamentoResponse>> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<EvolucaoTratamentoResponse> response = service.listarPorPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}/periodo")
    public ResponseEntity<List<EvolucaoTratamentoResponse>> listarPorPacienteEPeriodo(
            @PathVariable Long pacienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<EvolucaoTratamentoResponse> response = service.listarPorPacienteEPeriodo(pacienteId, dataInicio, dataFim);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<EvolucaoTratamentoResponse>> buscarPorNomePaciente(@RequestParam String nome) {
        List<EvolucaoTratamentoResponse> response = service.buscarPorNomePaciente(nome);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}/data")
    public ResponseEntity<List<EvolucaoTratamentoResponse>> buscarPorPacienteEData(
            @PathVariable Long pacienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<EvolucaoTratamentoResponse> response = service.buscarPorPacienteEData(pacienteId, data);
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