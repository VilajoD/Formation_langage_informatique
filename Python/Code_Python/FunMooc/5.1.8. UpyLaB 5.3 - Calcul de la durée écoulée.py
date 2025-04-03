"""
fonction duree qui reçoit deux paramètres debut et fin. Ces derniers sont des couples (tuples de deux composantes)
dont la première composante représente une heure et la seconde les minutes.

Cette fonction doit calculer la durée qui s’est écoulée entre ces deux instants. Le résultat sera donné sous la
forme d’un tuple (heure, minutes).
"""

def duree(debut,fin) :
    minute=fin[1]-debut[1]
    heure=fin[0]-debut[0]
    print(minute)
    print(heure)

    if minute < 0:
        minute = 60 + minute
        heure -= 1

    if heure < 0:
        heure = 24 + heure

    return(heure,minute)

res = duree((3, 45), (13, 22))

print(res)