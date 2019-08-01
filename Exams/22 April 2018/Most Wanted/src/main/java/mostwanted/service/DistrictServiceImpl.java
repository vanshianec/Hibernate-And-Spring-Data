package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.DistrictImportDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Town;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DistrictServiceImpl implements DistrictService {

    private final static String DISTRICT_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/districts.json";
    private final DistrictRepository districtRepository;
    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final Gson gson;

    @Autowired
    public DistrictServiceImpl(DistrictRepository districtRepository, TownRepository townRepository, ValidationUtil validationUtil, ModelMapper modelMapper, FileUtil fileUtil, Gson gson) {
        this.districtRepository = districtRepository;
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }

    @Override
    public Boolean districtsAreImported() {
        return this.districtRepository.count() > 0;
    }

    @Override
    public String readDistrictsJsonFile() throws IOException {
        return this.fileUtil.readFile(DISTRICT_JSON_FILE_PATH);
    }

    @Override
    public String importDistricts(String districtsFileContent) {
        StringBuilder stringBuilder = new StringBuilder();
        DistrictImportDto[] districtImportDtos = this.gson.fromJson(districtsFileContent, DistrictImportDto[].class);
        for (DistrictImportDto districtImportDto : districtImportDtos) {
            District district = this.modelMapper.map(districtImportDto, District.class);
            if (!this.validationUtil.isValid(district)) {
                stringBuilder.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            } else if (this.districtRepository.findDistrictByName(district.getName()) != null) {
                stringBuilder.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            Town town = this.townRepository.findTownByName(districtImportDto.getTownName());
            district.setTown(town);
            stringBuilder.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, district.getClass().getSimpleName(), district.getName())).append(System.lineSeparator());
            this.districtRepository.saveAndFlush(district);
        }
        return stringBuilder.toString().trim();
    }
}
