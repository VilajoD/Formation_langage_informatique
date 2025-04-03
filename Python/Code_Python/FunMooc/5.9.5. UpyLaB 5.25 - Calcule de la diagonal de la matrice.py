"""
fonction trace(M) qui reçoit en paramètre une matrice M de taille {n}\times{n} contenant des valeurs
 numériques (de type int ou float), et qui renvoie sa trace, c’est-à-dire la somme de tous
 les éléments de la première diagonale.
"""

def trace(matrice):
    return(sum([matrice[i][i] for i in range(len(matrice))]))

print(trace([]))