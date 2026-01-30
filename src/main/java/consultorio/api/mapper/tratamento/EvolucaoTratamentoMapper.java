package consultorio.api.mapper.tratamento;


import consultorio.api.dto.request.tratamento.EvolucaoTratamentoRequest;
import consultorio.api.dto.response.tratamento.EvolucaoTratamentoResponse;
import consultorio.domain.entity.tratamento.EvolucaoTratamento;
import org.springframework.stereotype.Component;

@Component
public class EvolucaoTratamentoMapper {

    public EvolucaoTratamento toEntity(EvolucaoTratamentoRequest request) {
        return EvolucaoTratamento.builder()
                .data(request.getData())
                .evolucaoEIntercorrencias(request.getEvolucaoEIntercorrencias())
                .build();
    }

    public EvolucaoTratamentoResponse toResponse(EvolucaoTratamento evolucao) {
        return EvolucaoTratamentoResponse.builder()
                .id(evolucao.getId())
                .pacienteId(evolucao.getPaciente().getId())
                .pacienteNome(evolucao.getPaciente().getNome())
                .dentistaId(evolucao.getDentista().getId())
                .dentistaNome(evolucao.getDentista().getNome())
                .data(evolucao.getData())
                .evolucaoEIntercorrencias(evolucao.getEvolucaoEIntercorrencias())
                .build();
    }

    public void updateEntity(EvolucaoTratamento evolucao, EvolucaoTratamentoRequest request) {
        evolucao.setData(request.getData());
        evolucao.setEvolucaoEIntercorrencias(request.getEvolucaoEIntercorrencias());
    }
}