"""
fonction gagnant(grille) où grille est la grille de jeu sous forme de matrice.

Cette matrice contient donc six listes de lignes contenant chacune sept éléments.

La ligne à l’indice 0 représente le bas du jeu.

Les éléments de cette matrice seront soit 'R', soit 'J' ou soit 'V' pour un emplacement encore vide.

Cette fonction renverra 'R' si le joueur à la couleur rouge a gagné, 'J' si le joueur à la couleur
jaune a gagné ou bien None si aucun joueur n’a gagné.
"""

def gagnant(grille):

    def test_ligne(grille):
        print(grille)
        gagnant = None
        for ligne in grille:
            if "JJJJ" in "".join(ligne):
                gagnant = 'J'
            elif "RRRR" in "".join(ligne):
                gagnant = 'R'
        print(gagnant)
        return gagnant

    # Test si gangnat sur 1 ligne
    resultat = test_ligne(grille)

    # Test si gagnant sur une colonne
    if resultat == None:
        print([[grille[j][i] for j in range(len(grille[0])-1)] for i in range(len(grille))])
        resultat = test_ligne([[grille[j][i] for j in range(len(grille[0])-1)] for i in range(len(grille))])

    # Test si gagnant sur diagonale 1
    if resultat == None:
        print([[grille[j][(j+i)%7]  for j in range(len(grille[0])-1)]for i in range(len(grille))])
        resultat = test_ligne([[grille[j][(j+i)%7]  for j in range(len(grille[0])-1)]for i in range(len(grille))])

    # Test si gagnant sur diagonale 2
    if resultat == None:
        print([[[5-j,(j - i) % 7] for j in range(len(grille[0])-2,-1,-1)] for i in range(len(grille))])
        print([[grille[5-j][(j-i)%7]  for j in range(len(grille[0])-2,-1,-1)]for i in range(len(grille))])
        resultat = test_ligne([[grille[5-j][(j-i)%7]  for j in range(len(grille[0])-2,-1,-1)]for i in range(len(grille))])

    return resultat

print(gagnant([['V', 'R', 'V', 'J', 'R', 'R', 'J'],
               ['R', 'V', 'J', 'R', 'J', 'R', 'R'],
               ['V', 'V', 'V', 'V', 'R', 'V', 'R'],
               ['J', 'V', 'V', 'V', 'V', 'R', 'J'],
               ['V', 'V', 'V', 'V', 'V', 'V', 'V'],
               ['V', 'V', 'V', 'V', 'V', 'V', 'V']]))