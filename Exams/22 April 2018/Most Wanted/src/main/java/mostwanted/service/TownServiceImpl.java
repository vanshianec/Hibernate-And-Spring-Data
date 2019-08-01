package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.TownImportDto;
import mostwanted.domain.entities.Town;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TownServiceImpl implements TownService {

    private final static String TOWNS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/towns.json";
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, FileUtil fileUtil, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public Boolean townsAreImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return this.fileUtil.readFile(TOWNS_JSON_FILE_PATH);
    }

    @Override
    public String importTowns(String townsFileContent) {
        StringBuilder stringBuilder = new StringBuilder();
        TownImportDto[] townImportDtos = this.gson.fromJson(townsFileContent, TownImportDto[].class);
        for (TownImportDto townImportDto : townImportDtos) {
            Town town = this.modelMapper.map(townImportDto, Town.class);
            if (!this.validationUtil.isValid(town)) {
                stringBuilder.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            } else if (this.townRepository.findTownByName(town.getName()) != null) {
                stringBuilder.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            stringBuilder.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, town.getClass().getSimpleName(), town.getName())).append(System.lineSeparator());
            this.townRepository.saveAndFlush(town);
        }
        return stringBuilder.toString().trim();
    }

    @Override
    public String exportRacingTowns() {
        StringBuilder sb = new StringBuilder();
        List<Town> towns = this.townRepository.getTownsWithRacers();
        for (Town town : towns) {
            sb.append(String.format("Name: %s%nRacers: %d%n%n", town.getName(), town.getRacers().size()));
        }
        return sb.toString().trim();
    }
}
