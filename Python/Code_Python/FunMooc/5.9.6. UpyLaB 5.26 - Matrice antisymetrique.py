"""
Fonction booléenne antisymetrique(M) qui teste si la matrice M reçue est antisymétrique.

Une matrice M = \{m_{ij}\} de taille {n}\times{n} est dite antisymétrique lorsque,
pour toute paire d’indices i, j, on a m_{ij} = - m_{ji}.
"""

def antisymetrique(matrice):
    resultat = True
    for i in range(len(matrice)):
        for j in range(len(matrice[0])):
            if matrice[i][j] != -matrice[j][i]:
                resultat = False

    return(resultat)

print(antisymetrique([[0, 1], [1, 0]]))