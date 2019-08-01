package com.example.demo.controller;

import com.example.demo.domain.entities.Ingredient;
import com.example.demo.domain.entities.Shampoo;
import com.example.demo.domain.entities.Size;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.LabelRepository;
import com.example.demo.repository.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class AppController implements CommandLineRunner {

    private final LabelRepository labelRepository;
    private final ShampooRepository shampooRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public AppController(LabelRepository labelRepository, ShampooRepository shampooRepository, IngredientRepository ingredientRepository) {
        this.labelRepository = labelRepository;
        this.shampooRepository = shampooRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        //Ex.1 Select Shampoos By Size
        //selectShampoosBySize(bufferedReader);

        //Ex.2 Select Shampoos By Size or Label
        //selectShampoosBySizeOrLabel(bufferedReader);

        //Ex.3 Select Shampoos By Price
        //selectShampoosByPrice(bufferedReader);

        //Ex.4 Select Ingredients By Name
        //SelectIngredientsByName(bufferedReader);

        //Ex.5 Select Ingredients By Names
        //selectIngredientsByNames(bufferedReader);

        //Ex.6 Count Shampoos By Price
        //countShampoosByPrice(bufferedReader);

        //Ex.7 Select Shampoos By Ingredients
        //selectShampoosByIngredients(bufferedReader);

        //Ex. 8 Select Shampoos By Ingredients Count
        //selectShampoosByIngredientsCount(bufferedReader);

        //Ex. 9 Select Ingredient Name And Shampoo Brand By Name
        //selectIngredientNameAndShampooBrandByName(bufferedReader);

        //Ex. 10 Delete Ingredient By Name
        //deleteIngredientByName(bufferedReader);

        //Ex. 11 Update Ingredient Price By Ten Percent
        //updateIngredientsPriceByTenPercent();

        //Ex. 12 Update Ingredient Price By Ten Percent By Name
        //updateIngredientsPriceByTenPercentByName(bufferedReader);
    }

    private void updateIngredientsPriceByTenPercentByName(BufferedReader bufferedReader) throws IOException {
        List<String> ingredientNames = new ArrayList<>();
        String line;
        while (!(line = bufferedReader.readLine()).equals("")) {
            ingredientNames.add(line);
        }

        this.ingredientRepository.updateIngredientsPriceByTenPercentByName(ingredientNames);
    }

    private void updateIngredientsPriceByTenPercent() {
        this.ingredientRepository.updateIngredientsPriceByTenPercent();
    }

    private void deleteIngredientByName(BufferedReader bufferedReader) throws IOException {
        String ingredientName = bufferedReader.readLine();
        this.ingredientRepository.deleteIngredientByName(ingredientName);
    }

    private void selectIngredientNameAndShampooBrandByName(BufferedReader bufferedReader) throws IOException {
        String brand = bufferedReader.readLine();
        System.out.println(this.shampooRepository.selectIngredientNameAndShampooBrandByName(brand));
    }

    private void selectShampoosByIngredientsCount(BufferedReader bufferedReader) throws IOException {
        int count = Integer.parseInt(bufferedReader.readLine());
        this.shampooRepository.selectShampoosByIngredientsCount(count)
                .forEach(s -> System.out.println(s.getBrand()));
    }

    private void selectShampoosByIngredients(BufferedReader bufferedReader) throws IOException {
        List<Ingredient> ingredients = new ArrayList<>();
        String line;
        while (!(line = bufferedReader.readLine()).equals("")) {
            ingredients.add(this.ingredientRepository.getByName(line));
        }
        this.shampooRepository.selectShampoosByIngredients(ingredients)
                .forEach(s -> System.out.println(s.getBrand()));
    }

    private void countShampoosByPrice(BufferedReader bufferedReader) throws IOException {
        String inputPrice = bufferedReader.readLine();
        BigDecimal price = new BigDecimal(inputPrice);
        System.out.println(this.shampooRepository.countAllByPriceIsLessThan(price));
    }

    private void selectIngredientsByNames(BufferedReader bufferedReader) throws IOException {
        List<String> names = new ArrayList<>();
        String line;
        while (!(line = bufferedReader.readLine()).equals("")) {
            names.add(line);
        }
        this.ingredientRepository.findAllByNameInOrderByPrice(names)
                .forEach(i -> System.out.println(i.getName()));
    }

    private void SelectIngredientsByName(BufferedReader bufferedReader) throws IOException {
        String pattern = bufferedReader.readLine();
        List<Ingredient> ingredients = this.ingredientRepository.findAllByNameStartingWith(pattern);
        ingredients.forEach(i -> System.out.printf("%s%n", i.getName()));
    }

    private void selectShampoosByPrice(BufferedReader bufferedReader) throws IOException {
        String inputPrice = bufferedReader.readLine();
        BigDecimal price = new BigDecimal(inputPrice);
        List<Shampoo> shampoos = this.shampooRepository.findAllByPriceIsGreaterThanOrderByPriceDesc(price);
        shampoos.forEach(s -> System.out.printf("%s %s %.2f lv.%n", s.getBrand(), s.getSize(), s.getPrice()));
    }

    private void selectShampoosBySizeOrLabel(BufferedReader bufferedReader) throws IOException {
        String size = bufferedReader.readLine().toUpperCase();
        long label = Long.parseLong(bufferedReader.readLine());
        List<Shampoo> shampoos = this.shampooRepository.findAllBySizeOrLabel_IdOrderByPrice(Size.valueOf(size), label);
        shampoos.forEach(s -> System.out.printf("%s %s %.2f lv.%n", s.getBrand(), s.getSize(), s.getPrice()));
    }

    private void selectShampoosBySize(BufferedReader bufferedReader) throws IOException {
        String size = bufferedReader.readLine().toUpperCase();
        List<Shampoo> shampoos = this.shampooRepository.findAllBySizeOrderById(Size.valueOf(size));
        shampoos.forEach(s -> System.out.printf("%s %s %.2f lv.%n", s.getBrand(), s.getSize(), s.getPrice()));
    }
}
