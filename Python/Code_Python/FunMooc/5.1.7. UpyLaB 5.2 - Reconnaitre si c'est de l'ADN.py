"""
fonction est_adn qui reçoit une chaîne de caractères en paramètre et qui retourne True si cette chaîne de
caractères n'est pas vide et peut représenter un brin d’ADN, False sinon.
"""

def est_adn(adn):
    res = True

    if len(adn) == 0 :
        res=False

    for base in adn:
        if base != 'A' and base != 'C' and base != 'G' and base != 'T':
            res = False
    return(res)

res = est_adn("")

print(res)