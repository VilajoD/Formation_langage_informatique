"""
Programme qui verifie si le petit prince se trouve en journéer ou bien en pleine nuit.

La planète U357 a deux soleils : les étoiles E1515 et E666.  C'est pour cela que les tempêtes magnétiques
sont permanentes, ce qui est excellent pour avoir des aurores boréales.

Par contre, il y fait souvent jour, sauf bien évidemment quand les deux soleils sont couchés en même temps.

Pour cela, vous allez dans un premier temps écrire une fonction soleil_leve qui, pour un soleil particulier,
reçoit trois valeurs entières en argument pour la planète :
     - l'heure de lever du soleil (entre 0 et 23)
     - l'heure du coucher du soleil (entre 0 et 23)
     - l'heure actuelle (entre 0 et 23)

et qui renvoie une valeur booléenne vraie si le soleil est levé sur la planète à l'heure donnée en troisième
argument et fausse, s'il est couché.

"""



def soleil_leve (leve, couche, actuel) :
    if leve == couche == 0:
        jour = True
    elif leve == couche == 12:
        jour = False
    else :
        if ((actuel >= leve or  actuel < couche) and leve > couche) or ((actuel >= leve and  actuel < couche) and leve < couche):
            jour = True
        else :
            jour = False

    return jour



leve_E1515 = int(input())
couche_E1515 = int(input())
leve_E666 = int(input())
couche_E666 = int(input())

for i in range(24):
    if soleil_leve(leve_E1515,couche_E1515,i) == True or  soleil_leve(leve_E666,couche_E666,i) == True :
        print(i)
    else:
        print(i,'*')