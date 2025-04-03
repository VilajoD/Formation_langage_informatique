"""
fonction naked_single(grid) qui reçoit en paramètre une matrice 9 x 9 d’entiers représentant
une grille de sudoku, et qui renvoie un couple de valeurs :

un booléen ok qui indique si la grille peut être résolue en utilisant cette seule méthode du candidat unique,

la grille complétée si ok est égal à True, None sinon.
"""

def naked_single(grid):

    grille_ligne = list(grid)
    grille_colonne = [[grille_ligne[j][i] for j in range(len(grille_ligne[0]))] for i in range(len(grille_ligne))]
    grille_region = [[grille_ligne[i + z][j + w] for j in range(3) for i in range(3)] for z in range(0, 7, 3) for w in
                     range(0, 7, 3)]

    nbr = {1,2,3,4,5,6,7,8,9}
    modif = True
    while modif == True:
        region = 0
        modif = False

        for j in range(len(grille_ligne)):

            if j == 0 or j == 1 or j == 2 :
                region = 0
            elif j == 3 or j == 4 or j == 5:
                region = 3
            elif j == 6 or j == 7 or j == 8:
                region = 6

            for i in range(len(grille_ligne[j])):
                if grille_ligne[j][i] == 0:
                    manquant = list(nbr - ((set(grille_ligne[j])) | set(grille_colonne[i]) | set(grille_region[region + (int(i / 3))])))

                    if len(manquant) == 1:
                        grille_ligne[j][i] = manquant[0]
                        grille_colonne = [[grille_ligne[j][i] for j in range(len(grille_ligne[0]))] for i in
                                          range(len(grille_ligne))]
                        grille_region = [[grille_ligne[i + z][j + w] for j in range(3) for i in range(3)] for z in
                                         range(0, 7, 3) for w in
                                         range(0, 7, 3)]
                        modif = True


    modif = True
    indice = 0
    while indice < len(grille_ligne) and modif == True:
        if 0 not in grille_ligne[indice]:
            resultat = grille_ligne
        else:
            resultat = None
            modif = False
        indice += 1

    return (modif,resultat)





grille =     [[4, 0, 3, 0, 9, 6, 0, 1, 0],
              [0, 0, 2, 8, 0, 1, 0, 0, 3],
              [0, 1, 0, 0, 0, 0, 0, 0, 7],
              [0, 4, 0, 7, 0, 0, 0, 2, 6],
              [5, 0, 7, 0, 1, 0, 4, 0, 9],
              [1, 2, 0, 0, 0, 3, 0, 8, 0],
              [2, 0, 0, 0, 0, 0, 0, 7, 0],
              [7, 0, 0, 2, 0, 9, 8, 0, 0],
              [0, 6, 0, 1, 5, 0, 3, 0, 2]]

print(naked_single(grille))