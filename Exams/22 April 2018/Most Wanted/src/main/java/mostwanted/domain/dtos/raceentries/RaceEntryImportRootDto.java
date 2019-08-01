package mostwanted.domain.dtos.raceentries;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "race-entries")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceEntryImportRootDto {

    @XmlElement(name = "race-entry")
    private List<RaceEntryImportDto> dtos;

    public RaceEntryImportRootDto(){
        this.dtos = new ArrayList<>();
    }

    public List<RaceEntryImportDto> getDtos() {
        return dtos;
    }

    public void setDtos(List<RaceEntryImportDto> dtos) {
        this.dtos = dtos;
    }
}