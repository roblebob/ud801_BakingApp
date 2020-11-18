# Baking App

The data involved is splitted into 3 destinct entities:

- Recipe
- RecipeIngredient
- recipeStep

In short:

for every __Recipe__, there exist multiple __RecipeIngredient__ _s_ and __RecipeStep__ _s_, each.
Therefore those associated all include the ID of the __Recipe__ they correspond to.  