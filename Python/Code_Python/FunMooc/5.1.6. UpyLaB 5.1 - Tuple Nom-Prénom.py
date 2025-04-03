"""
Fonction qui recoit en entrée un tuple de 2 avec le nom puis le prenom. Et en sorti renvoi le nom et le prenom dans
une seule variable.
"""

def signature (identite):
    return(identite[1] +' '+ identite[0])

res = signature(('Seurel', 'François'))

print(res)