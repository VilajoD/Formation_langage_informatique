"""
fonction booléenne bonne_planete(diametre) qui reçoit en paramètre un
nombre représentant le diamètre, en mètres, d'une planète candidate.
La fonction retourne la valeur True ou False selon que la planète convient ou non.

Écrire ensuite un programme qui lit un diamètre depuis l'entrée et affiche

la chaîne de caractères "Bonne planète" si le résultat de l'appel à la fonction bonne_planete est True
la chaîne de caractères "Trop petite" sinon
"""

from math import pi

def bonne_planete(diametre):
    surface = pi * int(diametre)**2
    if surface / 50 >= 1000:
        resultat = True
    else:
        resultat = False

    return resultat


if bonne_planete(input()) == True:
    print("Bonne planète")
else:
    print("Trop petite")
