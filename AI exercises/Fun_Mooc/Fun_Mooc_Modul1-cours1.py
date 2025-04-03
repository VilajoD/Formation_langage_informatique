import matplotlib
import pandas as pd


adult_census = pd.read_csv("/Document_de_travail/adult-census.csv")



print (adult_census.head())



target_column = "class"
print(adult_census[target_column].value_counts())


numerical_columns = [
    "age", "education-num", "capital-gain", "capital-loss", "hours-per-week",
]
categorical_columns = [
    "workclass", "education", "marital-status", "occupation", "relationship",
    "race", "sex", "native-country",
]
all_columns = numerical_columns + categorical_columns + [target_column]
adult_census = adult_census[all_columns]

print("Toutes les colonnes : ")
print(all_columns)
print("Le fichier : ")
print (adult_census)

print(
    f"Le jeu de données contient {adult_census.shape[0]} échantillons "
    f"et {adult_census.shape[1]} colonnes."
)

print(f"Le jeu de données contient {adult_census.shape[1] - 1} caractéristiques.")

_ = adult_census.hist(figsize=(20, 14))

print(adult_census["sex"].value_counts())

print(pd.crosstab(
    index=adult_census["education"], columns=adult_census["education-num"]
))