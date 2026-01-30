package consultorio.mapper;

import consultorio.domain.exame_dental.ExameDental;
import consultorio.dto.request.exame_dental.ExameDentalRequest;
import consultorio.dto.response.exame_dental.ExameDentalResponse;
import org.springframework.stereotype.Component;

@Component
public class ExameDentalMapper {

    public ExameDental toEntity(ExameDentalRequest request) {
        return ExameDental.builder()
                .pacienteId(request.getPacienteId())
                .pacienteNome(request.getPacienteNome())
                .dente(request.getDente())
                .procedimento(request.getProcedimento())
                .valor(request.getValor())
                .observacoes(request.getObservacoes())
                .valorTotal(request.getValorTotal())
                .build();
    }

    public ExameDentalResponse toResponse(ExameDental exame) {
        return ExameDentalResponse.builder()
                .id(exame.getId())
                .pacienteId(exame.getPacienteId())
                .pacienteNome(exame.getPacienteNome())
                .dente(exame.getDente())
                .procedimento(exame.getProcedimento())
                .valor(exame.getValor())
                .observacoes(exame.getObservacoes())
                .valorTotal(exame.getValorTotal())
                .criadoEm(exame.getCriadoEm())
                .atualizadoEm(exame.getAtualizadoEm())
                .build();
    }

    public void updateEntity(ExameDental exame, ExameDentalRequest request) {
        exame.setPacienteId(request.getPacienteId());
        exame.setPacienteNome(request.getPacienteNome());
        exame.setDente(request.getDente());
        exame.setProcedimento(request.getProcedimento());
        exame.setValor(request.getValor());
        exame.setObservacoes(request.getObservacoes());
        exame.setValorTotal(request.getValorTotal());
    }
}
