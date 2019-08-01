package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.CarImportDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CarServiceImpl implements CarService {

    private final static String CARS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/cars.json";

    private final CarRepository carRepository;
    private final RacerRepository racerRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final Gson gson;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, RacerRepository racerRepository, ValidationUtil validationUtil, ModelMapper modelMapper, FileUtil fileUtil, Gson gson) {
        this.carRepository = carRepository;
        this.racerRepository = racerRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }

    @Override
    public Boolean carsAreImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsJsonFile() throws IOException {
        return this.fileUtil.readFile(CARS_JSON_FILE_PATH);
    }

    @Override
    public String importCars(String carsFileContent) {
        StringBuilder stringBuilder = new StringBuilder();
        CarImportDto[] carImportDtos = this.gson.fromJson(carsFileContent, CarImportDto[].class);
        for (CarImportDto carImportDto : carImportDtos) {
            Car car = this.modelMapper.map(carImportDto, Car.class);
            if (!this.validationUtil.isValid(car)) {
                stringBuilder.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            Racer racer = this.racerRepository.findRacerByName(carImportDto.getRacerName());
            car.setRacer(racer);
            stringBuilder.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
                    car.getClass().getSimpleName(),
                    String.format("%s %s @ %d", car.getBrand(), car.getModel(), car.getYearOfProduction()))).append(System.lineSeparator());
            this.carRepository.saveAndFlush(car);
        }
        return stringBuilder.toString().trim();
    }
}
