# -*- coding: utf8 -*-
import random
quotes = [
    "Ecoutez-moi, Monsieur Shakespeare, nous avons beau être ou ne pas être, nous sommes !", 
    "On doit pouvoir choisir entre s'écouter parler et se faire entendre."
]

characters = [
    "alvin et les Chipmunks", 
    "Babar", 
    "betty boop", 
    "calimero", 
    "casper", 
    "le chat potté", 
    "Kirikou"
]

#créer une fonction
def random_quote(liste):
     nbr=random.randint(0,len(liste) - 1)
     citation = liste[nbr]
     return(citation)

reponse = input('Tapez entrée pour connaître une autre citation ou B pour quitter le programme.')

while reponse != "B":
    print(random_quote(characters))
    reponse = input('Tapez entrée pour connaître une autre citation ou B pour quitter le programme.')


print("Fin de programme")

