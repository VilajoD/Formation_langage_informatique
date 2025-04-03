"""
fonction init_mat(m, n) qui construit et renvoie une matrice d’entiers initialisée à la
matrice nulle et de dimension m x n.
"""

def init_mat(ligne, colonne):
    return([[0 for i in range(colonne)] for j in range(ligne)])

print(init_mat(2, 3))