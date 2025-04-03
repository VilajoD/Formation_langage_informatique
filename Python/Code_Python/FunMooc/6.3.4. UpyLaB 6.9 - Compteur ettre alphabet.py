"""
fonction compteur_lettres(texte) qui renvoie un dictionnaire contenant toutes les lettres de
l’alphabet associées à leur nombre d’apparition dans texte.
"""

def compteur_lettres(texte):
    alphabet = {}.fromkeys('abcdefghijklmnopqrstuvwxyz', 0)
    for lettre in enumerate(texte.lower()):
        print(lettre)
        if lettre[1] in alphabet:
            alphabet[lettre[1]] = alphabet.get(lettre[1]) + 1

    return alphabet


texte ='Dessine-moi un mouton !'
print(compteur_lettres(texte))