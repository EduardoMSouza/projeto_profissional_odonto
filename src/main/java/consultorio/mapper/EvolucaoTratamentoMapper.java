package consultorio.mapper;

import consultorio.domain.evolucao_tratamento.EvolucaoTratamento;
import consultorio.dto.request.evolcao_tratamento.EvolucaoTratamentoRequest;
import consultorio.dto.response.evolcao_tratamento.EvolucaoTratamentoResponse;
import org.springframework.stereotype.Component;

@Component
public class EvolucaoTratamentoMapper {

    public EvolucaoTratamento toEntity(EvolucaoTratamentoRequest request) {
        return EvolucaoTratamento.builder()
                .pacienteId(request.getPacienteId())
                .pacienteNome(request.getPacienteNome())
                .data(request.getData())
                .evolucao(request.getEvolucao())
                .intercorrencias(request.getIntercorrencias())
                .assinaturaPaciente(request.getAssinaturaPaciente())
                .build();
    }

    public EvolucaoTratamentoResponse toResponse(EvolucaoTratamento evolucao) {
        return EvolucaoTratamentoResponse.builder()
                .id(evolucao.getId())
                .pacienteId(evolucao.getPacienteId())
                .pacienteNome(evolucao.getPacienteNome())
                .data(evolucao.getData())
                .evolucao(evolucao.getEvolucao())
                .intercorrencias(evolucao.getIntercorrencias())
                .assinaturaPaciente(evolucao.getAssinaturaPaciente())
                .criadoEm(evolucao.getCriadoEm())
                .atualizadoEm(evolucao.getAtualizadoEm())
                .build();
    }

    public void updateEntity(EvolucaoTratamento evolucao, EvolucaoTratamentoRequest request) {
        evolucao.setPacienteId(request.getPacienteId());
        evolucao.setPacienteNome(request.getPacienteNome());
        evolucao.setData(request.getData());
        evolucao.setEvolucao(request.getEvolucao());
        evolucao.setIntercorrencias(request.getIntercorrencias());
        evolucao.setAssinaturaPaciente(request.getAssinaturaPaciente());
    }
}