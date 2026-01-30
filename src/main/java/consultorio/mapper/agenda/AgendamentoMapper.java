package consultorio.mapper.agenda;

import consultorio.domain.agenda.Agendamento;
import consultorio.domain.agenda.StatusAgendamento;
import consultorio.dto.agendamento.AgendamentoRequest;
import consultorio.dto.response.agenda.AgendamentoResponse;
import org.springframework.stereotype.Component;

@Component
public class AgendamentoMapper {

    public Agendamento toEntity(AgendamentoRequest request) {
        Agendamento agendamento = new Agendamento();
        agendamento.setPacienteId(request.getPacienteId());
        agendamento.setPacienteNome(request.getPacienteNome());
        agendamento.setDentistaId(request.getDentistaId());
        agendamento.setDentistaNome(request.getDentistaNome());
        agendamento.setDataConsulta(request.getDataConsulta());
        agendamento.setHoraInicio(request.getHoraInicio());
        agendamento.setHoraFim(request.getHoraFim());
        agendamento.setStatus(request.getStatus() != null ? request.getStatus() : StatusAgendamento.AGENDADO);
        agendamento.setTipoProcedimento(request.getTipoProcedimento());
        agendamento.setObservacoes(request.getObservacoes());
        agendamento.setAtivo(request.getAtivo() != null ? request.getAtivo() : true);
        agendamento.setCriadoPor(request.getCriadoPor());
        return agendamento;
    }

    public AgendamentoResponse toResponse(Agendamento agendamento) {
        return AgendamentoResponse.builder()
                .id(agendamento.getId())
                .pacienteId(agendamento.getPacienteId())
                .pacienteNome(agendamento.getPacienteNome())
                .dentistaId(agendamento.getDentistaId())
                .dentistaNome(agendamento.getDentistaNome())
                .dataConsulta(agendamento.getDataConsulta())
                .horaInicio(agendamento.getHoraInicio())
                .horaFim(agendamento.getHoraFim())
                .status(agendamento.getStatus())
                .tipoProcedimento(agendamento.getTipoProcedimento())
                .observacoes(agendamento.getObservacoes())
                .ativo(agendamento.getAtivo())
                .criadoEm(agendamento.getCriadoEm())
                .atualizadoEm(agendamento.getAtualizadoEm())
                .criadoPor(agendamento.getCriadoPor())
                .atualizadoPor(agendamento.getAtualizadoPor())
                .canceladoPor(agendamento.getCanceladoPor())
                .canceladoEm(agendamento.getCanceladoEm())
                .motivoCancelamento(agendamento.getMotivoCancelamento())
                .confirmadoEm(agendamento.getConfirmadoEm())
                .lembreteEnviado(agendamento.getLembreteEnviado())
                .lembreteEnviadoEm(agendamento.getLembreteEnviadoEm())
                .duracaoMinutos(agendamento.getDuracaoEmMinutos())
                .consultaPassada(agendamento.isConsultaPassada())
                .consultaHoje(agendamento.isHoje())
                .build();
    }

    public void updateEntity(Agendamento agendamento, AgendamentoRequest request) {
        agendamento.setPacienteId(request.getPacienteId());
        agendamento.setPacienteNome(request.getPacienteNome());
        agendamento.setDentistaId(request.getDentistaId());
        agendamento.setDentistaNome(request.getDentistaNome());
        agendamento.setDataConsulta(request.getDataConsulta());
        agendamento.setHoraInicio(request.getHoraInicio());
        agendamento.setHoraFim(request.getHoraFim());
        if (request.getStatus() != null) {
            agendamento.setStatus(request.getStatus());
        }
        agendamento.setTipoProcedimento(request.getTipoProcedimento());
        agendamento.setObservacoes(request.getObservacoes());
        if (request.getAtivo() != null) {
            agendamento.setAtivo(request.getAtivo());
        }
    }
}