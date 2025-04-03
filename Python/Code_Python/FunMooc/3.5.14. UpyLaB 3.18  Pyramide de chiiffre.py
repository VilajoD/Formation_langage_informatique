"""
Programme qui lit un nombre entier strictement positif n et imprime une pyramide de chiffres de
hauteur n (sur n lignes complètes, c'est-à-dire toutes terminées par une fin de ligne).

La première ligne imprime un “1” (au milieu de la pyramide).

La ligne i commence par le chiffre i % 10 et tant que l’on n’est pas au milieu, le chiffre suivant
a la valeur suivante ((i+1) % 10).

Après le milieu de la ligne, les chiffres vont en décroissant modulo 10 (symétriquement au début de la ligne).

Notons qu’à la dernière ligne, aucune espace n’est imprimée avant d’écrire les chiffres 0123....

Auteur : David VILAJOSANA
Date : 07/09/2021

"""

nombre = int(input())

for i in range(nombre):
    nbr_actuelle = (i+1)

    # Partir blanche avant la pyramide
    taille = nombre - nbr_actuelle
    for blanc in range(taille):
        print(end  = ' ')

    # Partie croissante de la pyramide
    for j in range(nbr_actuelle,((nbr_actuelle*2))):
        print((j%10),end ='')

    # Partie décroissante de la pyramide
    for j in range(((nbr_actuelle*2)-2),nbr_actuelle-1,-1):
        print((j%10),end ='')

    print(end='\n')