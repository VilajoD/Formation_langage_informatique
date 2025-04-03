"""
Écrire une fonction file_histogram(fileName) qui prend en paramètre le nom, sous forme d’une chaîne de
 caractères, d’un fichier texte, et qui renvoie un dictionnaire associant à chaque caractère du texte
 contenu dans ce fichier son nombre d’occurrences.

Écrire une fonction words_by_length(fileName) qui prend en paramètre le nom, sous forme d’une chaîne de
caractères, d’un fichier texte, et qui renvoie un dictionnaire associant à une longueur l la liste triée
(dans l’ordre utf-8 croissant) des mots de longueur l présents dans le texte contenu dans le fichier.
Ces mots seront écrits en minuscules.
"""

import os
os.chdir("C:/Users/vilaj/Desktop/Formation Python/Mooc Python/Fichier de travail")

def file_histogram(fileName):
    dictionaire_lettre = {}
    for ligne in open(fileName):
        for lettre in enumerate(ligne):
            print(lettre)
            dictionaire_lettre[lettre[1]] = dictionaire_lettre.setdefault(lettre[1],0) + 1
    return dictionaire_lettre


def words_by_length(fileName):
    dictionnaire_mot = {}
    separateur = "',?!-_."
    for ligne in open(fileName,encoding="UTF_8"):

        for sep in separateur:
            ligne = ligne.replace(sep," ")

        for mot in ligne.split():
            if len(mot) in dictionnaire_mot:
                if mot.lower() not in dictionnaire_mot[len(mot)]:
                    dictionnaire_mot[len(mot)].append(mot.lower())
                    dictionnaire_mot[len(mot)].sort()
            else:
                dictionnaire_mot[len(mot)] = [mot.lower()]

    return dictionnaire_mot
print(words_by_length("texte_Zola.txt"))