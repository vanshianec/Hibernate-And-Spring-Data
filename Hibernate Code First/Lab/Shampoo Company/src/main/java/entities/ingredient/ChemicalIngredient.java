package entities.ingredient;


public interface ChemicalIngredient extends Ingredient {

    void setChemicalFormula(String chemicalFormula);

    String getChemicalFormula();
}
