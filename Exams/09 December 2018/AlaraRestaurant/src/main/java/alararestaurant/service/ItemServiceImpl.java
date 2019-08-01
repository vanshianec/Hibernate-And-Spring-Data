package alararestaurant.service;

import alararestaurant.domain.dtos.jsons.ItemImportDto;
import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ItemServiceImpl implements ItemService {

    private static final String ITEM_JSON_FILE_PATH = "C:\\Users\\Иван Йовов\\IdeaProjects\\Hibernate\\Exams\\09 December 2018\\AlaraRestaurant\\src\\main\\resources\\files\\items.json";
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public ItemServiceImpl(ItemRepository itemRepository, CategoryRepository categoryRepository, FileUtil fileUtil, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public Boolean itemsAreImported() {
        return this.itemRepository.count() > 0;
    }

    @Override
    public String readItemsJsonFile() throws IOException {
        return this.fileUtil.readFile(ITEM_JSON_FILE_PATH);
    }

    @Override
    public String importItems(String items) {
        StringBuilder stringBuilder = new StringBuilder();
        ItemImportDto[] dtos = this.gson.fromJson(items, ItemImportDto[].class);
        for (ItemImportDto dto : dtos) {
            Item item = this.modelMapper.map(dto, Item.class);

            if (!this.validationUtil.isValid(item) || this.itemRepository.findByName(item.getName()) != null) {
                stringBuilder.append("Invalid data format.").append(System.lineSeparator());
                continue;
            }

            Category category = this.categoryRepository.findByName(dto.getCategory());
            if (category == null) {
                category = new Category();
                category.setName(dto.getCategory());
                if (!this.validationUtil.isValid(category)) {
                    stringBuilder.append("Invalid data format.").append(System.lineSeparator());
                    continue;
                }

                category = this.categoryRepository.saveAndFlush(category);
            }

            item.setCategory(category);
            this.itemRepository.saveAndFlush(item);
            stringBuilder.append(String.format("Record %s successfully imported.", item.getName())).append(System.lineSeparator());

        }


        return stringBuilder.toString().trim();
    }
}
