package mostwanted.service;

import mostwanted.common.Constants;
import mostwanted.domain.dtos.raceentries.RaceEntryImportDto;
import mostwanted.domain.dtos.raceentries.RaceEntryImportRootDto;
import mostwanted.domain.dtos.races.EntryImportRootDto;
import mostwanted.domain.dtos.races.RaceImportDto;
import mostwanted.domain.dtos.races.RaceImportRootDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RaceRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RaceServiceImpl implements RaceService {

    private final static String RACES_XML_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/races.xml";
    private final RaceRepository raceRepository;
    private final RaceEntryRepository raceEntryRepository;
    private final DistrictRepository districtRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;

    public RaceServiceImpl(RaceRepository raceRepository, RaceEntryRepository raceEntryRepository, DistrictRepository districtRepository, ModelMapper modelMapper, ValidationUtil validationUtil, FileUtil fileUtil, XmlParser xmlParser) {
        this.raceRepository = raceRepository;
        this.raceEntryRepository = raceEntryRepository;
        this.districtRepository = districtRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public Boolean racesAreImported() {
        return raceRepository.count() > 0;
    }

    @Override
    public String readRacesXmlFile() throws IOException {
        return this.fileUtil.readFile(RACES_XML_FILE_PATH);
    }

    @Override
    public String importRaces() throws JAXBException {
        StringBuilder stringBuilder = new StringBuilder();
        RaceImportRootDto rootDto = this.xmlParser.parseXml(RaceImportRootDto.class, RACES_XML_FILE_PATH);
        for (RaceImportDto raceDto : rootDto.getRaceDtos()) {
            Race race = this.modelMapper.map(raceDto, Race.class);
            if (!this.validationUtil.isValid(race)) {
                stringBuilder.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            District district = this.districtRepository.findDistrictByName(raceDto.getDistrictName());
            race.setDistrict(district);
            Race finalRace = race;
            List<RaceEntry> entries = raceDto.getEntries().getEntryDtos().stream()
                    .map(e -> {
                        RaceEntry entry = this.raceEntryRepository.findRaceEntriesById(e.getId());
                        entry.setRace(finalRace);
                        return entry;
                    }).collect(Collectors.toList());
            race.setEntries(entries);
            race = this.raceRepository.saveAndFlush(race);
            stringBuilder.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, race.getClass().getSimpleName(), race.getId())).append(System.lineSeparator());
        }
        return stringBuilder.toString().trim();
    }
}