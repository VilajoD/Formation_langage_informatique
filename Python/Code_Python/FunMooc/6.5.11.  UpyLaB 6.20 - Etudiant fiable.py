"""
programme qui lit depuis l’entrée deux chaînes de caractères, représentant les noms des
deux fichiers décrits ci-dessus (dans l’ordre, le fichier de type “result-pass-fail.csv”
suivi du fichier du type “result-count.csv”), et qui imprime la liste des intitulés, un par
ligne, dans l’ordre décroissant des « valeurs » calculées comme suit.

La « valeur » d’un intitulé est le nombre des « apprenants fiables » ayant réussi l’exercice correspondant.

Un « apprenant fiable » est un apprenant qui n’a jamais testé plus de dix fois chacun des codes
qu’il a essayé de valider.

Par exemple, si un apprenant n’a testé que trois exercices en soumettant respectivement
1, 2 et 10 essais, il est réputé « fiable ». Si un autre apprenant a testé tous les exercices,
mais en soumettant 11 essais pour l’un d’entre eux, il n’est pas considéré comme « fiable ».
"""

import os
os.chdir("C:/Users/vilaj/Desktop/Formation Python/Mooc Python/Fichier de travail")

#fichier_exo = open('result-pass-fail-0.csv', 'r')
# fichier_scr = open('result-count-0.csv', 'r')

fichier_exo = input()
fichier_scr = input()

liste_non_fiable = []
cpt_ligne = 0

# dabord faire la liste des eleve fiable avec leur position dans le fichier. En lisant le fichier SCR
    # faire une liste avec les eleve non fiable (leur numéro de lecture)
        # a chaque ligne de lu, faire un split(;) et regarder s'il y a un exo avec plus de 10 tentative
        # si oui le noté dans la liste
for ligne in open(fichier_scr,encoding="UTF_8"):
    if cpt_ligne == 0:
        pass
    else:
        ligne = ligne[:-1] # enleve le retour a la ligne en fin de ligne
        essais = ligne.split(";")
        for i in essais:
            if i.isnumeric():
                if int(i) > 10:
                    liste_non_fiable.append(cpt_ligne)
    cpt_ligne += 1


#print("Liste des non fiable : ", liste_non_fiable)


# Puis lire le fichier exo.
    # Comtabiliser dans un dictionnaire les VRAI des FAUX mais en passant outre les eleve non fiable.
        # faire un split() sur la 1ere ligne et crer pour chaque element une entréer dans le dictionnaire avec en valeur
         # une liste vide. En parralelle garder la liste créer par le split() pour garder les nom des cle du dico.
        # commencer la lecture du fichier et faire un spli(;). On pourra bouclé sur le liste de nom d'exo pour alimenter
         # le dico des compte (ne compter que les VRAI et si le chiffre de la ligne n'est pas dans la liste des non fiable


cpt_ligne = 0
dico_exo_nbr = {}
for ligne in open(fichier_exo,encoding="UTF_8"):
    if cpt_ligne == 0:
        ligne = ligne[:-1]  # enleve le retour a la ligne en fin de ligne
        nom_exo = ligne.split(";")
        for i in nom_exo:
            dico_exo_nbr[i] = 0
    else:
        if cpt_ligne not in liste_non_fiable:
            ligne = ligne[:-1]  # enleve le retour a la ligne en fin de ligne
            reussi = ligne.split(";")
            for i in range(len(reussi)):
                if reussi[i] == "VRAI":
                    dico_exo_nbr[nom_exo[i]] = dico_exo_nbr[nom_exo[i]] + 1
    cpt_ligne += 1

#print("Score pour les exos : ", dico_exo_nbr)

 # Faire un second dictionnaire avec les compte en clé et les exo en liste de valeur
    # récuperer les cle(compte) dans une liste

dico_nbr_exo = {}
for element in dico_exo_nbr:
    if dico_exo_nbr[element] in dico_nbr_exo:
        dico_nbr_exo[dico_exo_nbr[element]].append(element)
        dico_nbr_exo[dico_exo_nbr[element]].sort(reverse=True)
    else:
        dico_nbr_exo[dico_exo_nbr[element]] = [element]

#print("Exo en valeur : ", dico_nbr_exo)

# créer une autre liste en récuperant les valeur du dico pour les clé les plus grande et en faisait un sort.


liste_classement = []
for cpt in dico_nbr_exo:
    liste_classement.append(cpt)
    liste_classement.sort(reverse=True)

#print("liste classement : ", liste_classement)


# lire la liste un element pas un element et l'ecrire dans le fichier

for i in liste_classement:
    for element in dico_nbr_exo[i]:
        print(element)



#fichier_exo.close()
#fichier_scr.close()