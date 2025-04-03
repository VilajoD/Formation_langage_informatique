"""
fonction dupliques qui reçoit une séquence en paramètre.

La fonction doit renvoyer la valeur booléenne True si la séquence passée en paramètre contient des
éléments dupliqués, et la valeur booléenne False sinon.
"""

def dupliques(chaine):

    def trans_list(x):
        liste = []
        for elem in x:
            liste.append(elem)
        return(liste)

    if type(chaine) == str:
        liste = trans_list(chaine)
    else:
        liste = chaine
    duplic = False

    for elem in liste:
        liste.remove(elem)
        if elem in liste:
            duplic = True

    return(duplic)


print(dupliques('crc'))