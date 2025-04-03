"""
fonction rendre_monnaie qui reçoit en paramètre un entier prix et cinq valeurs
entières x20, x10, x5, x2 et x1, qui représentent le nombre de billets ou de pièces
de chaque valeur que donne un client pour l’achat d’un objet dont le prix est mentionné.

La fonction doit renvoyer cinq valeurs, représentant le nombre de billets et pièces de
chaque sorte qu’il faut rendre au client, dans le même ordre que précédemment. Cette
décomposition doit être faite en rendant le plus possible de billets et pièces de grosses valeurs.

Si la somme d’argent avancée par le client n’est pas suffisante pour effectuer l’achat,
la fonction retournera cinq valeurs None.
"""

def rendre_monnaie(prix, nbr_20, nbr_10, nbr_5, nbr_2, nbr_1):
    reste = prix - ((nbr_20*20)+(nbr_10*10)+(nbr_5*5)+(nbr_2*2)+nbr_1)
    monaie_20 = 0
    monaie_10 = 0
    monaie_5 = 0
    monaie_2 = 0
    monaie_1 = 0

    def cpt_billet(reste, valeur) :
        compteur = 0
        compteur = int(reste/valeur)
        return (compteur)

    if reste > 0 :
        monaie_20 = None
        monaie_10 = None
        monaie_5 = None
        monaie_2 = None
        monaie_1 = None
    else :
        reste = abs(reste)
        monaie_20 = cpt_billet(reste,20)
        reste -= (monaie_20*20)

        monaie_10 = cpt_billet(reste,10)
        reste -= (monaie_10*10)

        monaie_5 = cpt_billet(reste,5)
        reste -= (monaie_5*5)

        monaie_2 = cpt_billet(reste, 2)
        reste -= (monaie_2*2)

        monaie_1 = cpt_billet(reste, 1)
        reste -= monaie_1


    return(monaie_20, monaie_10, monaie_5, monaie_2, monaie_1)

