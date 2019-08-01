package alararestaurant.service;

import alararestaurant.domain.dtos.jsons.EmployeeImportDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final static String EMPLOYEE_JSON_FILE_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\Exams\\09 December 2018\\AlaraRestaurant\\src\\main\\resources\\files\\employees.json";
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PositionRepository positionRepository, FileUtil fileUtil, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEE_JSON_FILE_PATH);
    }

    @Override
    public String importEmployees(String employees) {
        StringBuilder stringBuilder = new StringBuilder();
        EmployeeImportDto[] dtos = this.gson.fromJson(employees, EmployeeImportDto[].class);
        for (EmployeeImportDto dto : dtos) {
            Employee employee = this.modelMapper.map(dto, Employee.class);
            if (!this.validationUtil.isValid(employee)) {
                stringBuilder.append("Invalid data format.").append(System.lineSeparator());
                continue;
            }

            Position position = this.positionRepository.findByName(dto.getPosition());
            if (position == null) {
                position = new Position();
                position.setName(dto.getPosition());
                if (!this.validationUtil.isValid(position)) {
                    stringBuilder.append("Invalid data format.").append(System.lineSeparator());
                    continue;
                }
                position = this.positionRepository.saveAndFlush(position);
            }
            employee.setPosition(position);
            this.employeeRepository.saveAndFlush(employee);
            stringBuilder.append(String.format("Record %s successfully imported.", employee.getName())).append(System.lineSeparator());
        }
        return stringBuilder.toString().trim();
    }
}
