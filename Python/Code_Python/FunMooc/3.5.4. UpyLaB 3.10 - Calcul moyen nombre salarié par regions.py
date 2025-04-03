"""
Calcul de la moyenne des salariés dans les petite et moyenne entreprise de la region. On obtient en entrée les nombres
chacun sur une ligne et lorque la valeur en entrée est de -1 cela signifie qu'il n'y a pas d'autre valeur. A ce moment
la il faut renvoyer la moyenne a l'utilisateur.

Aureur : David VILAJOSANA
Date : 07/09/2021

"""

valeur = int(input())
total = 0
nombre_entreprise = 0

while valeur != -1:
    total += valeur
    nombre_entreprise += 1
    valeur = int(input())

if nombre_entreprise == 0:
    moyenne = 0
else:
    moyenne = total/nombre_entreprise

print(moyenne)