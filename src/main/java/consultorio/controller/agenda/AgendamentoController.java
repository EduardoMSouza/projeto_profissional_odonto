package consultorio.controller.agenda;

import consultorio.domain.agenda.StatusAgendamento;
import consultorio.domain.agenda.TipoProcedimento;
import consultorio.dto.agendamento.AgendamentoRequest;
import consultorio.dto.response.agenda.AgendamentoResponse;
import consultorio.service.agenda.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    private final AgendamentoService service;

    public AgendamentoController(AgendamentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AgendamentoResponse> criar(
            @Valid @RequestBody AgendamentoRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (request.getCriadoPor() == null && userDetails != null) {
            request.setCriadoPor(userDetails.getUsername());
        }
        AgendamentoResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AgendamentoRequest request) {
        AgendamentoResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponse> buscarPorId(@PathVariable Long id) {
        AgendamentoResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoResponse>> listarTodos() {
        List<AgendamentoResponse> response = service.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dentista/{dentistaId}/data/{data}")
    public ResponseEntity<List<AgendamentoResponse>> listarPorDentistaEData(
            @PathVariable Long dentistaId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<AgendamentoResponse> response = service.listarPorDentistaEData(dentistaId, data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}/data/{data}")
    public ResponseEntity<List<AgendamentoResponse>> listarPorPacienteEData(
            @PathVariable Long pacienteId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<AgendamentoResponse> response = service.listarPorPacienteEData(pacienteId, data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<AgendamentoResponse>> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<AgendamentoResponse> response = service.listarPorPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dentista/{dentistaId}/periodo")
    public ResponseEntity<List<AgendamentoResponse>> listarPorDentistaEPeriodo(
            @PathVariable Long dentistaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<AgendamentoResponse> response = service.listarPorDentistaEPeriodo(dentistaId, dataInicio, dataFim);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}/periodo")
    public ResponseEntity<List<AgendamentoResponse>> listarPorPacienteEPeriodo(
            @PathVariable Long pacienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<AgendamentoResponse> response = service.listarPorPacienteEPeriodo(pacienteId, dataInicio, dataFim);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AgendamentoResponse>> listarPorStatus(@PathVariable StatusAgendamento status) {
        List<AgendamentoResponse> response = service.listarPorStatus(status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/data/{data}/status/{status}")
    public ResponseEntity<List<AgendamentoResponse>> listarPorDataEStatus(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @PathVariable StatusAgendamento status) {
        List<AgendamentoResponse> response = service.listarPorDataEStatus(data, status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tipo-procedimento/{tipo}")
    public ResponseEntity<List<AgendamentoResponse>> listarPorTipoProcedimento(@PathVariable TipoProcedimento tipo) {
        List<AgendamentoResponse> response = service.listarPorTipoProcedimento(tipo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pendentes-lembrete/{data}")
    public ResponseEntity<List<AgendamentoResponse>> listarPendentesLembrete(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<AgendamentoResponse> response = service.listarPendentesLembrete(data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/consultas-dia/{data}")
    public ResponseEntity<List<AgendamentoResponse>> listarConsultasDoDia(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<AgendamentoResponse> response = service.listarConsultasDoDia(data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/paciente")
    public ResponseEntity<List<AgendamentoResponse>> buscarPorPacienteNome(@RequestParam String nome) {
        List<AgendamentoResponse> response = service.buscarPorPacienteNome(nome);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/dentista")
    public ResponseEntity<List<AgendamentoResponse>> buscarPorDentistaNome(@RequestParam String nome) {
        List<AgendamentoResponse> response = service.buscarPorDentistaNome(nome);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}/proximos")
    public ResponseEntity<List<AgendamentoResponse>> listarProximosAgendamentosPaciente(@PathVariable Long pacienteId) {
        List<AgendamentoResponse> response = service.listarProximosAgendamentosPaciente(pacienteId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}/historico")
    public ResponseEntity<List<AgendamentoResponse>> listarHistoricoAgendamentosPaciente(@PathVariable Long pacienteId) {
        List<AgendamentoResponse> response = service.listarHistoricoAgendamentosPaciente(pacienteId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<AgendamentoResponse> confirmar(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        String usuario = userDetails != null ? userDetails.getUsername() : "Sistema";
        AgendamentoResponse response = service.confirmar(id, usuario);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/iniciar-atendimento")
    public ResponseEntity<AgendamentoResponse> iniciarAtendimento(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        String usuario = userDetails != null ? userDetails.getUsername() : "Sistema";
        AgendamentoResponse response = service.iniciarAtendimento(id, usuario);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/concluir")
    public ResponseEntity<AgendamentoResponse> concluir(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        String usuario = userDetails != null ? userDetails.getUsername() : "Sistema";
        AgendamentoResponse response = service.concluir(id, usuario);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<AgendamentoResponse> cancelar(
            @PathVariable Long id,
            @RequestParam String motivo,
            @AuthenticationPrincipal UserDetails userDetails) {
        String usuario = userDetails != null ? userDetails.getUsername() : "Sistema";
        AgendamentoResponse response = service.cancelar(id, motivo, usuario);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/falta")
    public ResponseEntity<AgendamentoResponse> marcarFalta(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        String usuario = userDetails != null ? userDetails.getUsername() : "Sistema";
        AgendamentoResponse response = service.marcarFalta(id, usuario);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/enviar-lembrete")
    public ResponseEntity<AgendamentoResponse> enviarLembrete(@PathVariable Long id) {
        AgendamentoResponse response = service.enviarLembrete(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dentista/{dentistaId}/data/{data}/contar")
    public ResponseEntity<Long> contarAgendamentosDia(
            @PathVariable Long dentistaId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        Long count = service.contarAgendamentosDia(dentistaId, data);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/paciente/{pacienteId}/contar")
    public ResponseEntity<Long> contarPorPaciente(@PathVariable Long pacienteId) {
        Long count = service.contarPorPaciente(pacienteId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/status/{status}/contar")
    public ResponseEntity<Long> contarPorStatus(@PathVariable StatusAgendamento status) {
        Long count = service.contarPorStatus(status);
        return ResponseEntity.ok(count);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}