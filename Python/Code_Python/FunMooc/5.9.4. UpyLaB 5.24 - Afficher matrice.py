"""
 fonction print_mat(M) qui reçoit une matrice M en paramètre et affiche son contenu.

Les éléments d’une même ligne de la matrice seront affichés sur une même ligne, et séparés par une espace,
les éléments de la ligne suivante étant affichés sur une nouvelle ligne.
"""

def print_mat(matrice):
    imprim = ''
    for ligne in matrice:
        for elem in ligne:
            imprim += str(elem) + ' '
        imprim += '\n'
    print(imprim)


ma_matrice = eval(input())
print_mat(ma_matrice)
