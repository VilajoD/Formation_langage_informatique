"""
fonction construction_dict_amis qui reçoit une liste de couples (prenom1, prenom2) signifiant que
prenom1 déclare prenom2 comme étant son ami.

La fonction construit et renvoie un dictionnaire dont les clés sont les prénoms des personnes nommées,
et la valeur de chaque entrée est l’ensemble des amis de la personne.
"""

def construction_dict_amis(liste_duple):
    dico_ami = {}
    for mon_ami,son_ami in liste_duple:
        if mon_ami in dico_ami:
            dico_ami[mon_ami].add(son_ami)
        else :
            dico_ami[mon_ami] = {son_ami}

        if son_ami not in dico_ami:
            dico_ami[son_ami] = set()

    return dico_ami


liste = [('Quidam', 'Pierre'),
                        ('Thierry', 'Michelle'),
                        ('Thierry', 'Pierre')]

print(construction_dict_amis(liste))