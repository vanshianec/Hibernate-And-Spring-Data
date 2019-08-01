package softuni.workshop.domain.dtos.exportDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "projects")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectExportRootDto {
    @XmlElement(name = "project")
    private List<ProjectExportDto> exportDtos;

    public ProjectExportRootDto(){}

    public List<ProjectExportDto> getExportDtos() {
        return exportDtos;
    }

    public void setExportDtos(List<ProjectExportDto> exportDtos) {
        this.exportDtos = exportDtos;
    }
}
