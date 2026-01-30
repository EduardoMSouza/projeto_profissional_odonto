package consultorio.mapper.agenda;

import consultorio.domain.agenda.FilaEspera;
import consultorio.dto.request.agenda.FilaEsperaRequest;
import consultorio.dto.response.agenda.FilaEsperaResponse;
import org.springframework.stereotype.Component;

@Component
public class FilaEsperaMapper {

    public FilaEspera toEntity(FilaEsperaRequest request) {
        FilaEspera fila = new FilaEspera();
        fila.setPacienteId(request.getPacienteId());
        fila.setPacienteNome(request.getPacienteNome());
        fila.setDentistaId(request.getDentistaId());
        fila.setDentistaNome(request.getDentistaNome());
        fila.setTipoProcedimento(request.getTipoProcedimento());
        fila.setDataPreferencial(request.getDataPreferencial());
        fila.setHoraInicioPreferencial(request.getHoraInicioPreferencial());
        fila.setHoraFimPreferencial(request.getHoraFimPreferencial());
        fila.setPeriodoPreferencial(request.getPeriodoPreferencial());
        fila.setStatus(request.getStatus() != null ? request.getStatus() : FilaEspera.StatusFila.AGUARDANDO);
        fila.setObservacoes(request.getObservacoes());
        fila.setPrioridade(request.getPrioridade() != null ? request.getPrioridade() : 0);
        fila.setAceitaQualquerHorario(request.getAceitaQualquerHorario() != null ? request.getAceitaQualquerHorario() : false);
        fila.setAceitaQualquerDentista(request.getAceitaQualquerDentista() != null ? request.getAceitaQualquerDentista() : false);
        fila.setCriadoPor(request.getCriadoPor());
        return fila;
    }

    public FilaEsperaResponse toResponse(FilaEspera fila) {
        return FilaEsperaResponse.builder()
                .id(fila.getId())
                .pacienteId(fila.getPacienteId())
                .pacienteNome(fila.getPacienteNome())
                .dentistaId(fila.getDentistaId())
                .dentistaNome(fila.getDentistaNome())
                .tipoProcedimento(fila.getTipoProcedimento())
                .dataPreferencial(fila.getDataPreferencial())
                .horaInicioPreferencial(fila.getHoraInicioPreferencial())
                .horaFimPreferencial(fila.getHoraFimPreferencial())
                .periodoPreferencial(fila.getPeriodoPreferencial())
                .status(fila.getStatus())
                .observacoes(fila.getObservacoes())
                .prioridade(fila.getPrioridade())
                .aceitaQualquerHorario(fila.getAceitaQualquerHorario())
                .aceitaQualquerDentista(fila.getAceitaQualquerDentista())
                .criadoEm(fila.getCriadoEm())
                .atualizadoEm(fila.getAtualizadoEm())
                .criadoPor(fila.getCriadoPor())
                .agendamentoId(fila.getAgendamentoId())
                .convertidoEm(fila.getConvertidoEm())
                .notificado(fila.getNotificado())
                .notificadoEm(fila.getNotificadoEm())
                .tentativasContato(fila.getTentativasContato())
                .ultimaTentativaContato(fila.getUltimaTentativaContato())
                .ativa(fila.isAtiva())
                .expirado(fila.isExpirado())
                .build();
    }

    public void updateEntity(FilaEspera fila, FilaEsperaRequest request) {
        fila.setPacienteId(request.getPacienteId());
        fila.setPacienteNome(request.getPacienteNome());
        fila.setDentistaId(request.getDentistaId());
        fila.setDentistaNome(request.getDentistaNome());
        fila.setTipoProcedimento(request.getTipoProcedimento());
        fila.setDataPreferencial(request.getDataPreferencial());
        fila.setHoraInicioPreferencial(request.getHoraInicioPreferencial());
        fila.setHoraFimPreferencial(request.getHoraFimPreferencial());
        fila.setPeriodoPreferencial(request.getPeriodoPreferencial());
        if (request.getStatus() != null) {
            fila.setStatus(request.getStatus());
        }
        fila.setObservacoes(request.getObservacoes());
        if (request.getPrioridade() != null) {
            fila.setPrioridade(request.getPrioridade());
        }
        if (request.getAceitaQualquerHorario() != null) {
            fila.setAceitaQualquerHorario(request.getAceitaQualquerHorario());
        }
        if (request.getAceitaQualquerDentista() != null) {
            fila.setAceitaQualquerDentista(request.getAceitaQualquerDentista());
        }
    }
}