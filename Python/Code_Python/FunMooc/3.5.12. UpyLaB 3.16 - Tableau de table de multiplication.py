"""
Écrivez un code qui lit un nombre entier strictement positif n et affiche sur n lignes une
table de multiplication de taille  n x n, avec, pour i entre 1 et n,  les n premières valeurs
multiples de i strictement positives sur la ième ligne.
Ainsi, les n premiers multiples de 1 strictement positifs (0 non compris) sont affichés sur la
première ligne, les n premiers multiples de 2 sur la deuxième, et cætera.


Auteur : David VILAJOSANA
Date : 07/09/2021

"""

nombre = int(input())

for i in range(nombre):
    for j in range(1,nombre+1):
        print((i+1)*j, end = '   ')
    print(end = "\n")
