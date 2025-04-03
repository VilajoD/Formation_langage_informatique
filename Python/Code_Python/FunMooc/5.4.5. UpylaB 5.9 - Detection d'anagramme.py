"""
fonction anagrammes(v, w) qui renvoie la valeur booléenne True si les mots v et w sont des anagrammes.

La fonction retourne la valeur booléenne False dans le cas contraire.
"""

def anagrammes(v, w):

    def trans_list(x):
        liste = []
        for elem in x:
            liste.append(elem)
        return(liste)


    anagra = True
    nom_1=trans_list(v)
    nom_2=trans_list(w)

    for elem in nom_1:
        if elem in nom_2:
            nom_2.remove(elem)
        else:
            anagra = False

    if len(nom_2) != 0:
        anagra = False

    return(anagra)


print(anagrammes('parisien', 'aspirine'))