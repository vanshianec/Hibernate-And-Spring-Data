package softuni.workshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.workshop.domain.dtos.exportDto.ProjectExportDto;
import softuni.workshop.domain.dtos.exportDto.ProjectExportRootDto;
import softuni.workshop.domain.dtos.importDto.ProjectDto;
import softuni.workshop.domain.dtos.importDto.ProjectRootDto;
import softuni.workshop.domain.entities.Company;
import softuni.workshop.domain.entities.Project;
import softuni.workshop.repository.CompanyRepository;
import softuni.workshop.repository.ProjectRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final static String PROJECT_XML_FILE_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\Workoshop MVC project\\src\\main\\resources\\files\\xmls\\projects.xml";
    private final static String EXPORT_PROJECT_XML_FILE_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\Workoshop MVC project\\src\\main\\resources\\files\\xmls\\exported-projects.xml";
    private final ProjectRepository projectRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final ValidatorUtil validatorUtil;
    private final CompanyRepository companyRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, XmlParser xmlParser, ModelMapper modelMapper, FileUtil fileUtil, ValidatorUtil validatorUtil, CompanyRepository companyRepository) {
        this.projectRepository = projectRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.validatorUtil = validatorUtil;
        this.companyRepository = companyRepository;
    }

    @Override
    public void importProjects() throws JAXBException {
        ProjectRootDto projectRootDto = this.xmlParser.importXml(ProjectRootDto.class, PROJECT_XML_FILE_PATH);
        for (ProjectDto projectDto : projectRootDto.getProjectDtos()) {
            if (!this.validatorUtil.isValid(projectDto)) {
                this.validatorUtil.printErrors(projectDto);
                continue;
            }
            Company company = this.companyRepository.findCompanyByName(projectDto.getCompany().getName());
            Project project = this.modelMapper.map(projectDto, Project.class);
            project.setCompany(company);
            this.projectRepository.saveAndFlush(project);
        }
    }


    @Override
    public boolean areImported() {
        return this.projectRepository.count() > 0;
    }

    @Override
    public String readProjectsXmlFile() throws IOException {
        return this.fileUtil.readFile(PROJECT_XML_FILE_PATH);
    }

    @Override
    public String exportFinishedProjects() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Project> projects = this.projectRepository.findAllByFinishedIsTrue();

        for (Project project : projects) {
            stringBuilder.append(String.format("Project name: %s%n    Description: %s%n    Payment: %.2f%n",
                    project.getName(), project.getDescription(), project.getPayment()));
        }
        return stringBuilder.toString().trim();
    }

    @Override
    public void exportProjects() throws JAXBException {
        List<ProjectExportDto> exportDtos = this.projectRepository.findAll().stream()
                .map(p -> this.modelMapper.map(p, ProjectExportDto.class))
                .collect(Collectors.toList());
        ProjectExportRootDto projectExportRootDto = new ProjectExportRootDto();
        projectExportRootDto.setExportDtos(exportDtos);
        this.xmlParser.exportXml(projectExportRootDto, EXPORT_PROJECT_XML_FILE_PATH);
    }
}
