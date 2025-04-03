"""
Programme qui lit une valeur flottante x en entrée et imprime une approximation de sin(x)
Cette approximation sera obtenue en additionnant successivement les différents termes de la série jusqu’à ce que
 la valeur du terme devienne inférieure (en valeur absolue) à une constante \epsilon que l’on fixera à 10^{-6}.

Auteur : David VILAJOSANA
Date : 07/09/2021

"""

x = float(input())
expo = 3
facto = 1
somme = x
signe = '-'
fin = False

while fin == False :
    # Calcul du facto
    for i in range(expo):
        facto = float(facto * (expo-i))

    # Ajout a la somme
    if signe == '-':
        somme = somme - ((x**expo)/facto)
        signe = '+'
    else:
        somme = somme + ((x**expo)/facto)
        signe = '-'

    if abs(((x**expo)/facto)) < 10**-6:
        fin = True

    # Reinitialisation
    facto = 1
    expo += 2

print(somme)