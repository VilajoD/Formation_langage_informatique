"""
fonction top_3_candidats(moyennes) qui reçoit un dictionnaire contenant comme clés les noms des candidats
et comme valeurs la moyenne que chacun a obtenue.

Cette fonction doit retourner un tuple contenant les noms des trois meilleurs candidats, par ordre décroissant
de leurs moyennes.
"""

def top_3_candidats(moyennes):
    classement = []
    for candidat in moyennes:
        classement.append((moyennes[candidat], candidat))
        classement.sort(reverse=True)

    return(classement[0][1],classement[1][1],classement[2][1])


moyenne ={'Candidat 7': 2, 'Candidat 2': 38, 'Candidat 6': 85,
                 'Candidat 1': 8, 'Candidat 3': 17, 'Candidat 5': 83,
                 'Candidat 4': 33}

print(top_3_candidats(moyenne))