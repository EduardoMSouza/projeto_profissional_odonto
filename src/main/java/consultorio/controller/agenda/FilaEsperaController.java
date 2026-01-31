package consultorio.controller.agenda;

import consultorio.domain.agenda.FilaEspera;
import consultorio.domain.agenda.TipoProcedimento;
import consultorio.dto.request.agenda.FilaEsperaRequest;
import consultorio.dto.response.agenda.FilaEsperaResponse;
import consultorio.service.agenda.FilaEsperaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/fila-espera")
public class FilaEsperaController {

    private final FilaEsperaService service;

    public FilaEsperaController(FilaEsperaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<FilaEsperaResponse> criar(
            @Valid @RequestBody FilaEsperaRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (request.getCriadoPor() == null && userDetails != null) {
            request.setCriadoPor(userDetails.getUsername());
        }
        FilaEsperaResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilaEsperaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody FilaEsperaRequest request) {
        FilaEsperaResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilaEsperaResponse> buscarPorId(@PathVariable Long id) {
        FilaEsperaResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<FilaEsperaResponse>> listarTodos() {
        List<FilaEsperaResponse> response = service.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<FilaEsperaResponse>> listarPorStatus(@PathVariable FilaEspera.StatusFila status) {
        List<FilaEsperaResponse> response = service.listarPorStatus(status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ativas")
    public ResponseEntity<List<FilaEsperaResponse>> listarFilasAtivas() {
        List<FilaEsperaResponse> response = service.listarFilasAtivas();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<FilaEsperaResponse>> listarPorPaciente(@PathVariable Long pacienteId) {
        List<FilaEsperaResponse> response = service.listarPorPaciente(pacienteId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dentista/{dentistaId}")
    public ResponseEntity<List<FilaEsperaResponse>> listarPorDentista(@PathVariable Long dentistaId) {
        List<FilaEsperaResponse> response = service.listarPorDentista(dentistaId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tipo-procedimento/{tipo}")
    public ResponseEntity<List<FilaEsperaResponse>> listarPorTipoProcedimento(@PathVariable TipoProcedimento tipo) {
        List<FilaEsperaResponse> response = service.listarPorTipoProcedimento(tipo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/periodo-preferencial/{periodo}")
    public ResponseEntity<List<FilaEsperaResponse>> listarPorPeriodoPreferencial(@PathVariable FilaEspera.PeriodoPreferencial periodo) {
        List<FilaEsperaResponse> response = service.listarPorPeriodoPreferencial(periodo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/data-preferencial/{data}")
    public ResponseEntity<List<FilaEsperaResponse>> listarPorDataPreferencial(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<FilaEsperaResponse> response = service.listarPorDataPreferencial(data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pendentes-notificacao")
    public ResponseEntity<List<FilaEsperaResponse>> listarPendentesNotificacao() {
        List<FilaEsperaResponse> response = service.listarPendentesNotificacao();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/expiradas")
    public ResponseEntity<List<FilaEsperaResponse>> listarExpiradas() {
        List<FilaEsperaResponse> response = service.listarExpiradas();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/compativeis")
    public ResponseEntity<List<FilaEsperaResponse>> listarCompativeis(
            @RequestParam Long dentistaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaInicio) {
        List<FilaEsperaResponse> response = service.listarCompativeis(dentistaId, data, horaInicio);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/paciente")
    public ResponseEntity<List<FilaEsperaResponse>> buscarPorPacienteNome(@RequestParam String nome) {
        List<FilaEsperaResponse> response = service.buscarPorPacienteNome(nome);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/dentista")
    public ResponseEntity<List<FilaEsperaResponse>> buscarPorDentistaNome(@RequestParam String nome) {
        List<FilaEsperaResponse> response = service.buscarPorDentistaNome(nome);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/prioridade-minima/{prioridade}")
    public ResponseEntity<List<FilaEsperaResponse>> listarPorPrioridadeMinima(@PathVariable Integer prioridade) {
        List<FilaEsperaResponse> response = service.listarPorPrioridadeMinima(prioridade);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/multiplas-tentativas/{minTentativas}")
    public ResponseEntity<List<FilaEsperaResponse>> listarComMultiplasTentativas(@PathVariable Integer minTentativas) {
        List<FilaEsperaResponse> response = service.listarComMultiplasTentativas(minTentativas);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/notificar")
    public ResponseEntity<FilaEsperaResponse> notificar(@PathVariable Long id) {
        FilaEsperaResponse response = service.notificar(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/converter")
    public ResponseEntity<FilaEsperaResponse> converterEmAgendamento(
            @PathVariable Long id,
            @RequestParam Long agendamentoId) {
        FilaEsperaResponse response = service.converterEmAgendamento(id, agendamentoId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<FilaEsperaResponse> cancelar(@PathVariable Long id) {
        FilaEsperaResponse response = service.cancelar(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/incrementar-tentativa")
    public ResponseEntity<FilaEsperaResponse> incrementarTentativaContato(@PathVariable Long id) {
        FilaEsperaResponse response = service.incrementarTentativaContato(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/expirar-antigas")
    public ResponseEntity<Void> expirarFilasAntigas() {
        service.expirarFilasAntigas();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/{status}/contar")
    public ResponseEntity<Long> contarPorStatus(@PathVariable FilaEspera.StatusFila status) {
        Long count = service.contarPorStatus(status);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/paciente/{pacienteId}/contar")
    public ResponseEntity<Long> contarPorPaciente(@PathVariable Long pacienteId) {
        Long count = service.contarPorPaciente(pacienteId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/ativas/contar")
    public ResponseEntity<Long> contarFilasAtivas() {
        Long count = service.contarFilasAtivas();
        return ResponseEntity.ok(count);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}