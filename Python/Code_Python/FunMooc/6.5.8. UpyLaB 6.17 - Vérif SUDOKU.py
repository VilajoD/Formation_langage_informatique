"""
EXERCICE 1 :
 fonction check_rows(grid) qui prend en paramètre une grille sous forme de matrice à deux dimensions et
 vérifie si toutes les lignes sont valides (c’est-à-dire que sur chaque ligne, chaque chiffre apparaît une et
 une seule fois).

 EXERCICE 2 :
 fonction check_cols qui prend en paramètre une grille sous forme de matrice à deux dimensions et vérifie
 si toutes les colonnes sont valides (c’est-à-dire que sur chaque colonne, chaque chiffre apparaît une
 et une seule fois).

 EXERCICE 3 :
  fonction check_regions qui prend en paramètre une grille sous forme de matrice à deux dimensions et
  vérifie si toutes les régions sont valides (c’est-à-dire que dans chaque région, chaque chiffre apparaît
  une et une seule fois).



 EXERCICE 4 :
  onction check_sudoku capable de vérifier si la grille passée en paramètre, sous forme d’une matrice
  9x 9 d’entiers, est une solution au problème du sudoku. La fonction retournera la réponse (True ou False).

  Cette fonction devra utiliser les trois fonctions suivantes (que vous devez aussi définir) :

  check_rows qui prend en paramètre une grille sous forme de matrice à deux dimensions et vérifie si toutes
  les lignes sont valides (c’est-à-dire que sur chaque ligne, chaque chiffre apparaît une et une seule fois).

  check_cols qui prend en paramètre une grille sous forme de matrice à deux dimensions et vérifie si toutes
  les colonnes sont valides (c’est-à-dire que sur chaque colonne, chaque chiffre apparaît une et une seule fois).

  check_regions qui prend en paramètre une grille sous forme de matrice à deux dimensions et vérifie si toutes
  les régions sont valides (c’est-à-dire que dans chaque région, chaque chiffre apparaît une et une seule fois).
"""

def check_sudoku(grid):

    resultat = check_rows(grid)
    if resultat == True:
        resultat = check_cols(grid)
        if resultat == True:
            resultat = check_regions(grid)

    return resultat


def check_rows(grid):
        nbr_sudoku = "123456789"
        resultat = True
        for ligne in grid:
            for i in nbr_sudoku:
                if int(i) not in ligne:
                    resultat = False
        return resultat


def check_cols(grid):
        nbr_sudoku = "123456789"
        resultat = True
        #tourne la grille de 90 degré
        grille = [[grid[j][i] for j in range(len(grid[0]))] for i in range(len(grid))]
        for ligne in grille:
            for i in nbr_sudoku:
                if int(i) not in ligne:
                    resultat = False
        return resultat


def check_regions(grid):
        nbr_sudoku = "123456789"
        resultat = True
        #Création de region 3*3. Chaque liste du dictionnaire correspond a une region de grid
        region = [[grid[i+z][j+w] for j in range(3) for i in range(3)] for z in range(0,7,3) for w in range(0,7,3)]
        print(region)
        for ligne in region:
            for i in nbr_sudoku:
                if int(i) not in ligne:
                    resultat = False
        return resultat

grille = [[9, 6, 3, 1, 7, 4, 2, 5, 8],
          [1, 7, 8, 3, 2, 5, 6, 4, 9],
          [2, 5, 4, 6, 8, 9, 7, 3, 1],
          [8, 2, 1, 4, 3, 7, 5, 9, 6],
          [4, 9, 6, 8, 5, 2, 3, 1, 7],
          [7, 3, 5, 9, 6, 1, 8, 2, 4],
          [5, 8, 9, 7, 1, 3, 4, 6, 2],
          [3, 1, 7, 2, 4, 6, 9, 8, 5],
          [6, 4, 2, 5, 9, 8, 1, 7, 3]]

print(check_sudoku(grille))