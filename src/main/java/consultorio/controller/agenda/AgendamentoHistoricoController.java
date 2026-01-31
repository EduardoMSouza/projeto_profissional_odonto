package consultorio.controller.agenda;

import consultorio.domain.agenda.AgendamentoHistorico;
import consultorio.dto.request.agenda.AgendamentoHistoricoRequest;
import consultorio.dto.response.agenda.AgendamentoHistoricoResponse;
import consultorio.service.agenda.AgendamentoHistoricoService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/agendamentos-historico")
public class AgendamentoHistoricoController {

    private final AgendamentoHistoricoService service;

    public AgendamentoHistoricoController(AgendamentoHistoricoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AgendamentoHistoricoResponse> criar(@Valid @RequestBody AgendamentoHistoricoRequest request) {
        AgendamentoHistoricoResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoHistoricoResponse> buscarPorId(@PathVariable Long id) {
        AgendamentoHistoricoResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoHistoricoResponse>> listarTodos() {
        List<AgendamentoHistoricoResponse> response = service.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/agendamento/{agendamentoId}")
    public ResponseEntity<List<AgendamentoHistoricoResponse>> listarPorAgendamento(
            @PathVariable Long agendamentoId) {
        List<AgendamentoHistoricoResponse> response = service.listarPorAgendamento(agendamentoId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/acao/{acao}")
    public ResponseEntity<List<AgendamentoHistoricoResponse>> listarPorAcao(
            @PathVariable AgendamentoHistorico.TipoAcao acao) {
        List<AgendamentoHistoricoResponse> response = service.listarPorAcao(acao);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{usuario}")
    public ResponseEntity<List<AgendamentoHistoricoResponse>> listarPorUsuario(@PathVariable String usuario) {
        List<AgendamentoHistoricoResponse> response = service.listarPorUsuario(usuario);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<AgendamentoHistoricoResponse>> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        List<AgendamentoHistoricoResponse> response = service.listarPorPeriodo(inicio, fim);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/agendamento/{agendamentoId}/periodo")
    public ResponseEntity<List<AgendamentoHistoricoResponse>> listarPorAgendamentoEPeriodo(
            @PathVariable Long agendamentoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        List<AgendamentoHistoricoResponse> response = service.listarPorAgendamentoEPeriodo(agendamentoId, inicio, fim);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recentes")
    public ResponseEntity<List<AgendamentoHistoricoResponse>> listarAcoesRecentes(
            @RequestParam(defaultValue = "50") int limit) {
        List<AgendamentoHistoricoResponse> response = service.listarAcoesRecentes(limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/acao/{acao}/contar")
    public ResponseEntity<Long> contarPorAcao(@PathVariable AgendamentoHistorico.TipoAcao acao) {
        Long count = service.contarPorAcao(acao);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/agendamento/{agendamentoId}/contar")
    public ResponseEntity<Long> contarPorAgendamento(@PathVariable Long agendamentoId) {
        Long count = service.contarPorAgendamento(agendamentoId);
        return ResponseEntity.ok(count);
    }
}