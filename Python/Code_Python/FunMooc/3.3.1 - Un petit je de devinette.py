import random
secret = random.randint(0, 5)

reponse_utilisateur = (int(input("Devinez le chiffre auquel je pense. Il se trouve entre 0 et 5")))

if secret == reponse_utilisateur:
    print("Mais vous etes trop fort. C'est gagné !")
else:
    print("Perdu ! La valeur était " + str(secret) + ".")
