"""
Fonction placer_pion(couleur, colonne, grille) où :

couleur est la couleur du pion, qui peut être soit 'R' (rouge), soit 'J' (jaune),

colonne est l’indice de la colonne où le joueur souhaite placer le pion (allant de 0 à 6),

grille est la grille de jeu sous forme de matrice.

Cette matrice contient donc six listes de lignes contenant chacune sept éléments.

La ligne à l’indice 0 représente le bas du jeu.

Les éléments de cette matrice seront soit 'R', soit 'J' ou soit 'V' pour un emplacement encore vide.

Cette fonction placera un pion dans la grille du jeu et renverra un couple de valeurs :

si le placement est possible, la valeur booléenne True et la grille modifiée,

sinon, la valeur booléenne False et la grille non modifiée.
"""

def placer_pion(couleur, colonne, grille):
    jouer = False
    ligne = 0
    while ligne < len(grille) and jouer == False:
        if grille[ligne][colonne] == "V":
            jouer = True
            grille[ligne][colonne] = couleur
        ligne += 1
    return (jouer,grille)


print(placer_pion("J", 4, [['J', 'J', 'J', 'J', 'R', 'R', 'R'],
                     ['R', 'R', 'R', 'R', 'R', 'V', 'V'],
                     ['J', 'J', 'J', 'J', 'J', 'V', 'V'],
                     ['V', 'R', 'J', 'R', 'J', 'V', 'V'],
                     ['V', 'V', 'V', 'V', 'J', 'V', 'V'],
                     ['V', 'V', 'V', 'V', 'R', 'V', 'V']]))
