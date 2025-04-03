"""
fonction symetrise_amis qui reçoit un dictionnaire d d’amis où les clés sont des prénoms et les
valeurs, des ensembles de prénoms représentant les amis de chacun.

Cette fonction modifie le dictionnaire d de sorte que si une clé prenom1 contient prenom2 dans
l’ensemble de ses amis, l’inverse soit vrai aussi.

La fonction accepte un second paramètre englobe.

Si englobe est vrai, la fonction ajoutera les éléments nécessaires pour symétriser le dictionnaire d.

Sinon, la fonction enlèvera les éléments nécessaires pour symétriser d.
"""

def symetrise_amis(liste_amis,englobe):
    for nom in liste_amis:
        list = set(liste_amis[nom])
        for elem in list:
            if nom in liste_amis[elem]:
                pass
            else :
                print('test')
                if englobe == True:
                    liste_amis[elem].add(nom)

                elif englobe == False:
                    liste_amis[nom].discard(elem)
                    print(list)
                    print(liste_amis)


d = {'Thierry': {'Michelle', 'Bernadette'},
     'Michelle': {'Thierry'},
     'Bernadette': set()}
symetrise_amis(d, False)
print(d)