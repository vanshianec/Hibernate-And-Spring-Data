package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.RacerImportDto;
import mostwanted.domain.entities.Racer;
import mostwanted.domain.entities.Town;
import mostwanted.repository.RacerRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RacerServiceImpl implements RacerService {

    private final static String RACERS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/racers.json";
    private final RacerRepository racerRepository;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public RacerServiceImpl(RacerRepository racerRepository, TownRepository townRepository, FileUtil fileUtil, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.racerRepository = racerRepository;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public Boolean racersAreImported() {
        return this.racerRepository.count() > 0;
    }

    @Override
    public String readRacersJsonFile() throws IOException {
        return this.fileUtil.readFile(RACERS_JSON_FILE_PATH);
    }

    @Override
    public String importRacers(String racersFileContent) {
        StringBuilder stringBuilder = new StringBuilder();
        RacerImportDto[] racerImportDtos = this.gson.fromJson(racersFileContent, RacerImportDto[].class);
        for (RacerImportDto racerImportDto : racerImportDtos) {
            Racer racer = this.modelMapper.map(racerImportDto, Racer.class);
            if (!this.validationUtil.isValid(racer)) {
                stringBuilder.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            } else if (this.racerRepository.findRacerByName(racer.getName()) != null) {
                stringBuilder.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            Town town = this.townRepository.findTownByName(racerImportDto.getHomeTownName());
            racer.setHomeTown(town);
            stringBuilder.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, racer.getClass().getSimpleName(), racer.getName())).append(System.lineSeparator());
            this.racerRepository.saveAndFlush(racer);
        }
        return stringBuilder.toString().trim();
    }

    @Override
    public String exportRacingCars() {
        StringBuilder sb = new StringBuilder();
        List<Racer> racers = this.racerRepository.getAllRacersWithCars();
        for (Racer racer : racers) {
            String ageInfoString = racer.getAge() != null ? String.format("Age: %d%n", racer.getAge()) : "";
            StringBuilder carsStringBuilder = new StringBuilder();
            racer.getCars().forEach(c -> carsStringBuilder.append(String.format("\t%s %s %d%n", c.getBrand(), c.getModel(), c.getYearOfProduction())));
            sb.append(String.format("Name: %s%n", racer.getName()))
                    .append(ageInfoString)
                    .append(String.format("Cars:%n%s", carsStringBuilder.toString()));
        }
        return sb.toString().trim();
    }
}
