"""
fonction valeurs(dico) qui doit fournir, à partir du dictionnaire donné
en paramètre, une liste des valeurs du dictionnaire triées selon leur clé.
"""

def valeurs(dico):
    trie_valeur = []
    trie_cle = list(dico)
    trie_cle.sort()
    for cle in trie_cle:
        trie_valeur.append(dico[cle])

    return(trie_valeur)




dico = {'three': 'trois', 'two': 'deux', 'one': 'un'}
print(valeurs(dico))