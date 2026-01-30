package consultorio.api.mapper.tratamento;

import consultorio.api.dto.request.plano_dental.PlanoDentalRequest;
import consultorio.api.dto.response.plano_dental.PlanoDentalResponse;
import consultorio.domain.entity.tratamento.PlanoDental;
import org.springframework.stereotype.Component;

@Component
public class PlanoDentalMapper {

    public PlanoDental toEntity(PlanoDentalRequest request) {
        return PlanoDental.builder()
                .dente(request.getDente())
                .procedimento(request.getProcedimento())
                .valor(request.getValor())
                .valorFinal(request.getValorFinal())
                .observacoes(request.getObservacoes())
                .build();
    }

    public PlanoDentalResponse toResponse(PlanoDental plano) {
        return PlanoDentalResponse.builder()
                .id(plano.getId())
                .pacienteId(plano.getPaciente().getId())
                .pacienteNome(plano.getPaciente().getNome())
                .dentistaId(plano.getDentista().getId())
                .dentistaNome(plano.getDentista().getNome())
                .dente(plano.getDente())
                .procedimento(plano.getProcedimento())
                .valor(plano.getValor())
                .valorFinal(plano.getValorFinal())
                .observacoes(plano.getObservacoes())
                .criadoEm(plano.getCriadoEm())
                .atualizadoEm(plano.getAtualizadoEm())
                .build();
    }

    public void updateEntity(PlanoDental plano, PlanoDentalRequest request) {
        plano.setDente(request.getDente());
        plano.setProcedimento(request.getProcedimento());
        plano.setValor(request.getValor());
        plano.setValorFinal(request.getValorFinal());
        plano.setObservacoes(request.getObservacoes());
    }
}