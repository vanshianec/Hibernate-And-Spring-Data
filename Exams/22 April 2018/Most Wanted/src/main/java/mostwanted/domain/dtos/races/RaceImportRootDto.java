package mostwanted.domain.dtos.races;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "races")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceImportRootDto {

    @XmlElement(name = "race")
    private List<RaceImportDto> raceDtos;

    public RaceImportRootDto(){}

    public List<RaceImportDto> getRaceDtos() {
        return raceDtos;
    }

    public void setRaceDtos(List<RaceImportDto> raceDtos) {
        this.raceDtos = raceDtos;
    }
}
