"""
En entrée se trouve un nombre. Le programme créer un carré de X en fonction du nombre reçu.

Avec la donnée lue suivante : 6
Le résultat à imprimer vaudra :

XXXXXX
XXXXXX
XXXXXX
XXXXXX
XXXXXX
XXXXXX

Aureur : David VILAJOSANA
Date : 07/09/2021

"""

nombre = int(input())

for i in range(nombre):
    for j in range(nombre):
        print("X",end = '')
    print(end ='\n')