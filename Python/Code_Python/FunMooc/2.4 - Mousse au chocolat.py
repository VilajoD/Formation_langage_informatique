"""
Auteur : DVILAJO
Programme n°1 du MOOC Python
Gerer la quantitée d'ingredient en fonction du nombre de personne

"""


# Demande à l'utilisateur du nombre de convives
invit = int(input('Recette pour combien de personnes ?'))
nbr_oeufs = int((3/4)*invit)
nbr_sachet_levure = int((1/4)*invit)
quantite_chocolat = int((100/4)*invit)

print("Pour une mousse au chocolat pour " + str(invit) + " personnes, il vous faut :")
print("Oeufs : " + str(nbr_oeufs))
print("Chococolat : " + str(quantite_chocolat) + " grammes")
print("Sachet de sucre vanillé : " + str(nbr_sachet_levure))