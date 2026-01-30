package consultorio.service;

import consultorio.domain.agenda.FilaEspera;
import consultorio.domain.agenda.TipoProcedimento;
import consultorio.dto.request.agenda.FilaEsperaRequest;
import consultorio.dto.response.agenda.FilaEsperaResponse;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface FilaEsperaService {

    FilaEsperaResponse criar(FilaEsperaRequest request);

    FilaEsperaResponse atualizar(Long id, FilaEsperaRequest request);

    FilaEsperaResponse buscarPorId(Long id);

    List<FilaEsperaResponse> listarTodos();

    List<FilaEsperaResponse> listarPorStatus(FilaEspera.StatusFila status);

    List<FilaEsperaResponse> listarFilasAtivas();

    List<FilaEsperaResponse> listarPorPaciente(Long pacienteId);

    List<FilaEsperaResponse> listarPorDentista(Long dentistaId);

    List<FilaEsperaResponse> listarPorTipoProcedimento(TipoProcedimento tipo);

    List<FilaEsperaResponse> listarPorPeriodoPreferencial(FilaEspera.PeriodoPreferencial periodo);

    List<FilaEsperaResponse> listarPorDataPreferencial(LocalDate data);

    List<FilaEsperaResponse> listarPendentesNotificacao();

    List<FilaEsperaResponse> listarExpiradas();

    List<FilaEsperaResponse> listarCompativeis(Long dentistaId, LocalDate data, LocalTime horaInicio);

    List<FilaEsperaResponse> buscarPorPacienteNome(String nome);

    List<FilaEsperaResponse> buscarPorDentistaNome(String nome);

    List<FilaEsperaResponse> listarPorPrioridadeMinima(Integer prioridade);

    List<FilaEsperaResponse> listarComMultiplasTentativas(Integer minTentativas);

    FilaEsperaResponse notificar(Long id);

    FilaEsperaResponse converterEmAgendamento(Long id, Long agendamentoId);

    FilaEsperaResponse cancelar(Long id);

    FilaEsperaResponse incrementarTentativaContato(Long id);

    void expirarFilasAntigas();

    Long contarPorStatus(FilaEspera.StatusFila status);

    Long contarPorPaciente(Long pacienteId);

    Long contarFilasAtivas();

    void deletar(Long id);
}