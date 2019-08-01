package softuni.workshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.workshop.domain.dtos.exportDto.EmployeeExportDto;
import softuni.workshop.domain.dtos.exportDto.EmployeeExportRootDto;
import softuni.workshop.domain.dtos.importDto.EmployeeDto;
import softuni.workshop.domain.dtos.importDto.EmployeeRootDto;
import softuni.workshop.domain.entities.Employee;
import softuni.workshop.domain.entities.Project;
import softuni.workshop.repository.EmployeeRepository;
import softuni.workshop.repository.ProjectRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private static final String EMPLOYEE_XML_FILE_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\Workoshop MVC project\\src\\main\\resources\\files\\xmls\\employees.xml";
    private static final String EXPORT_EMPLOYEES_XML_FILE_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\Workoshop MVC project\\src\\main\\resources\\files\\xmls\\exported-employees.xml";
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ProjectRepository projectRepository, ModelMapper modelMapper, FileUtil fileUtil, XmlParser xmlParser, ValidatorUtil validatorUtil) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public void importEmployees() throws JAXBException {
        EmployeeRootDto employeeRootDto = this.xmlParser.importXml(EmployeeRootDto.class, EMPLOYEE_XML_FILE_PATH);
        for (EmployeeDto employeeDto : employeeRootDto.getEmployeeDtos()) {
            if (!this.validatorUtil.isValid(employeeDto)) {
                this.validatorUtil.printErrors(employeeDto);
                continue;
            }
            Employee employee = this.modelMapper.map(employeeDto, Employee.class);
            Project project = this.projectRepository.findProjectByName(employeeDto.getProjectDto().getName());
            employee.setProject(project);
            this.employeeRepository.saveAndFlush(employee);
        }
    }


    @Override
    public boolean areImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEE_XML_FILE_PATH);
    }

    @Override
    public String exportEmployeesWithAgeAbove() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Employee> employees = this.employeeRepository.findAllByAgeGreaterThan(25);
        for (Employee employee : employees) {
            stringBuilder.append(String.format(" Name: %s %s%n    Age: %d%n    Project name: %s%n",
                    employee.getFirstName(), employee.getLastName(), employee.getAge(), employee.getProject().getName()));
        }
        return stringBuilder.toString().trim();
    }

    @Override
    public void exportEmployees() throws JAXBException {
        List<EmployeeExportDto> exportDtos =
                this.employeeRepository.findAll()
                        .stream()
                        .map(e -> this.modelMapper.map(e, EmployeeExportDto.class))
                        .collect(Collectors.toList());

        EmployeeExportRootDto export = new EmployeeExportRootDto();
        export.setEmployeeExportDtos(exportDtos);
        this.xmlParser.exportXml(export, EXPORT_EMPLOYEES_XML_FILE_PATH);
    }
}
