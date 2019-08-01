package softuni.workshop.domain.dtos.importDto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "projects")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectRootDto implements Serializable {

    @XmlElement(name = "project")
    private List<ProjectDto> projectDtos;

    public ProjectRootDto() {
        this.projectDtos = new ArrayList<>();
    }

    public List<ProjectDto> getProjectDtos() {
        return projectDtos;
    }

    public void setProjectDtos(List<ProjectDto> projectDtos) {
        this.projectDtos = projectDtos;
    }
}
