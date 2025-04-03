# -*- coding: utf8 -*-

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
     citation = liste[0]
     return(citation)

reponse = input('Tapez entrée pour connaître une autre citation ou B pour quitter le programme.')

if reponse == "B":
    #quitter le programme
    pass

elif reponse == "C":
    print("Ce n'est pas la bonne lettre !!")
    
else:
    #autre quote
    pass


print(random_quote(quotes))
