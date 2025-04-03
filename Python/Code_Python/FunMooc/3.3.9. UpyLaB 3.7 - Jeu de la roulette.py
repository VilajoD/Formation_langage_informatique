"""
Dans mon casino, ma roulette comporte 13 numéros de 0 à 12 comme montrés ci-dessous :

Le joueur a plusieurs types de paris possibles :
il peut choisir de parier sur le numéro sortant, et dans ce cas, s’il gagne, il remporte douze fois sa mise ;
il peut choisir de parier sur la parité du numéro sortant (pair ou impair), et dans ce cas, s’il gagne,
il remporte deux fois sa mise ;
enfin, il peut choisir de parier sur la couleur du numéro sortant (rouge ou noir), et dans ce cas aussi, s’il gagne,
il remporte deux fois sa mise.

Si le joueur perd son pari, il ne récupère pas sa mise.
Pour simplifier, on suppose que le numéro 0 n’est ni rouge ni noir, mais est pair. Pour simplifier encore, on suppose
que le joueur mise systématiquement 10 euros.

Écrire un programme qui aide le croupier à déterminer la somme que le casino doit donner au joueur.
Le programme lira, dans l’ordre, deux nombres entiers en entrée : le pari du joueur (représenté par un nombre entre 0
et 16, voir description plus bas), et le numéro issu du tirage (nombre entre 0 et 12). Le programme affichera alors le
 montant gagné par le joueur.

Entrées pour le pari du joueur :

nombre entre 0 et 12 : le joueur parie sur le numéro correspondant
13 : le joueur parie sur pair
14 : le joueur parie sur impair
15 : le joueur parie sur la couleur rouge
16 : le joueur parie sur la couleur noire.

Auteur David VILAJSOANA
Date : 05/09/2021

"""

pari_joueur = int(input())
tirage = int(input())
gain = 0

# Si le joueur à jouer un nombre
if pari_joueur <= 12:
    if pari_joueur == tirage:
        gain = 10 * 12  #Remporte 12 fois sa mise

# Si le joueur à jouer la parité
elif pari_joueur == 13 or pari_joueur == 14:
    joueur_pair = pari_joueur == 13
    tirage_pair = (tirage % 2) == 0
    if joueur_pair == tirage_pair :
        gain = 10 * 2 # Remporte 2 fois sa mise

# Si le joueur à jouer la couleur
elif pari_joueur == 15 or pari_joueur == 16:
    joueur_rouge = pari_joueur == 15
    tirage_rouge = tirage == 1 or tirage == 3 or tirage == 5 or tirage == 7 or tirage == 9 or tirage == 12
    if tirage == 0:
        gain = 0
    elif joueur_rouge == tirage_rouge:
        gain = 10 * 2 # Remporte 2 fois sa mise

# Résultat
print(gain)