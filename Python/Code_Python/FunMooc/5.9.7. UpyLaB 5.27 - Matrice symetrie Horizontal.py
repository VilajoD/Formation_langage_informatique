"""
 Fonction symetrie_horizontale(A) qui reçoit une matrice carrée A (de taille {n}\times{n}) et
 qui renvoie l’image de A par symétrie horizontale par rapport à la ligne du milieu : la première
 ligne devenant la dernière, la seconde, l’avant-dernière, etc.

"""

def symetrie_horizontale(matrice):
    return([[matrice[i][j]  for j in range(len(matrice[0]))]for i in range(len(matrice)-1,-1,-1)])

print(symetrie_horizontale([[1, 2, 3], [4, 5, 6], [7, 8, 9]]))