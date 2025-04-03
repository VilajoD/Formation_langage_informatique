"""

Reception d'un temperature.
    Si superieur à 0 et inferieur à 10 renvoyer "Il va faire frais.".
    Si inferieur ou egal à 0, renvoyer "Il va faire froid.".
Auteur David VILAJSOANA
Date : 05/09/2021

"""

temperature = int(input())


if temperature > 0 and temperature <= 10:
    print("Il va faire frais.")
elif temperature <= 0:
    print("Il va faire froid.")
