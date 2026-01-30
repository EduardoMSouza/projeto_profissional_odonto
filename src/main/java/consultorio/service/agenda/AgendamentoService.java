package consultorio.service.agenda;

import consultorio.domain.agenda.StatusAgendamento;
import consultorio.domain.agenda.TipoProcedimento;
import consultorio.dto.agendamento.AgendamentoRequest;
import consultorio.dto.response.agenda.AgendamentoResponse;


import java.time.LocalDate;
import java.util.List;

public interface AgendamentoService {

    AgendamentoResponse criar(AgendamentoRequest request);

    AgendamentoResponse atualizar(Long id, AgendamentoRequest request);

    AgendamentoResponse buscarPorId(Long id);

    List<AgendamentoResponse> listarTodos();

    List<AgendamentoResponse> listarPorDentistaEData(Long dentistaId, LocalDate data);

    List<AgendamentoResponse> listarPorPacienteEData(Long pacienteId, LocalDate data);

    List<AgendamentoResponse> listarPorPeriodo(LocalDate dataInicio, LocalDate dataFim);

    List<AgendamentoResponse> listarPorDentistaEPeriodo(Long dentistaId, LocalDate dataInicio, LocalDate dataFim);

    List<AgendamentoResponse> listarPorPacienteEPeriodo(Long pacienteId, LocalDate dataInicio, LocalDate dataFim);

    List<AgendamentoResponse> listarPorStatus(StatusAgendamento status);

    List<AgendamentoResponse> listarPorDataEStatus(LocalDate data, StatusAgendamento status);

    List<AgendamentoResponse> listarPorTipoProcedimento(TipoProcedimento tipo);

    List<AgendamentoResponse> listarPendentesLembrete(LocalDate data);

    List<AgendamentoResponse> listarConsultasDoDia(LocalDate data);

    List<AgendamentoResponse> buscarPorPacienteNome(String nome);

    List<AgendamentoResponse> buscarPorDentistaNome(String nome);

    List<AgendamentoResponse> listarProximosAgendamentosPaciente(Long pacienteId);

    List<AgendamentoResponse> listarHistoricoAgendamentosPaciente(Long pacienteId);

    boolean verificarConflito(Long dentistaId, LocalDate data, Long agendamentoId);

    AgendamentoResponse confirmar(Long id, String usuario);

    AgendamentoResponse iniciarAtendimento(Long id, String usuario);

    AgendamentoResponse concluir(Long id, String usuario);

    AgendamentoResponse cancelar(Long id, String motivo, String usuario);

    AgendamentoResponse marcarFalta(Long id, String usuario);

    AgendamentoResponse enviarLembrete(Long id);

    Long contarAgendamentosDia(Long dentistaId, LocalDate data);

    Long contarPorPaciente(Long pacienteId);

    Long contarPorStatus(StatusAgendamento status);

    void deletar(Long id);
}